package inhatc.capstone.baro.project.service;

import static inhatc.capstone.baro.exception.ErrorCode.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inhatc.capstone.baro.exception.CustomException;
import inhatc.capstone.baro.image.ImageRepository;
import inhatc.capstone.baro.job.Job;
import inhatc.capstone.baro.jwt.SecurityUtil;
import inhatc.capstone.baro.likes.LikesRepository;
import inhatc.capstone.baro.lounge.Lounge;
import inhatc.capstone.baro.lounge.LoungeRepository;
import inhatc.capstone.baro.member.MemberRepository;
import inhatc.capstone.baro.member.domain.Member;
import inhatc.capstone.baro.project.domain.Project;
import inhatc.capstone.baro.project.domain.ProjectApplicant;
import inhatc.capstone.baro.project.domain.ProjectDetail;
import inhatc.capstone.baro.project.domain.ProjectTeam;
import inhatc.capstone.baro.project.dto.PointType;
import inhatc.capstone.baro.project.dto.ProjectDto;
import inhatc.capstone.baro.project.dto.ProjectDto.Request;
import inhatc.capstone.baro.project.dto.ProjectDto.Summary;
import inhatc.capstone.baro.project.repository.ProjectApplicantRepository;
import inhatc.capstone.baro.project.repository.ProjectDetailRepository;
import inhatc.capstone.baro.project.repository.ProjectRepository;
import inhatc.capstone.baro.project.repository.ProjectTeamRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ProjectService {
	//TODO: 서비스 분리하는 것이 좋을지도..
	private final ProjectRepository projectRepository;
	private final ProjectDetailRepository projectDetailRepository;
	private final ProjectTeamRepository projectTeamRepository;
	private final ProjectApplicantRepository projectApplicantRepository;
	private final LikesRepository likesRepository;
	private final ImageRepository imageRepository;
	private final MemberRepository memberRepository;
	private final LoungeRepository loungeRepository;

	//프로젝트 생성
	public void createProject(ProjectDto.Create request) {
		//이미지를 먼저 서버에 업로드해야 이미지 첨부 가능
		imageRepository.findById(request.getThumbnailLink())
			.orElseThrow(() -> new CustomException(NOT_FOUND_IMAGE));
		Member leader = memberRepository.findById(request.getLeaderId())
			.orElseThrow(() -> new CustomException(NOT_FOUND_MEMBER));

		Project project = Project.createProject(request);
		if (leader.isFirst()) { // 가입하지 않은 회원 프로젝트 생성 불가
			throw new CustomException(NOT_JOINED);
		}

		if (request.getLoungeId() != null) {
			addPointLoungeWriter(request.getLoungeId());
		}

		leader.addPoint(PointType.CREATE_PROJECT);
		Project save = projectRepository.save(project);

		ProjectDetail detail = ProjectDetail.from(request);
		detail.setProject(save);
		projectDetailRepository.save(detail);
	}

	//아이디어 공유자 포인트 증가
	private void addPointLoungeWriter(Long loungeId) {
		Lounge lounge = loungeRepository.findById(loungeId).orElseThrow(() -> new CustomException(NOT_FOUND_LOUNGE));
		lounge.getMember().addPoint(PointType.SHARED_IDEA);
	}

	//프로젝트 목록 조회
	@Transactional(readOnly = true)
	public Page<Summary> getProjectList(Pageable pageable, Request request) {
		return projectRepository.findByCondition(request, pageable).map(Summary::from);
	}

	//최근 프로젝트 조회
	@Transactional(readOnly = true)
	public List<Summary> getRecentProject(int size) {
		return projectRepository.findAllByOrderByCreateDateDesc(PageRequest.of(0, size)).map(Summary::from)
			.getContent();
	}

	//주목할만한 프로젝트 조회
	@Transactional(readOnly = true)
	public List<Summary> getPopularProject() {
		return projectRepository.findTop3ByOrderByViewCountDesc()
			.stream()
			.map(Summary::from)
			.collect(Collectors.toList());
	}

	//프로젝트 상세 조회
	public ProjectDto.Detail getProjectDetail(Long id, Long memberId) {
		ProjectDetail detail = projectDetailRepository.findById(id)
			.orElseThrow(() -> new CustomException(NOT_FOUND_PROJECT));
		detail.getProject().increaseViewCount();

		ProjectDto.Detail response = ProjectDto.Detail.from(detail);
		response.setLike(likesRepository.existsByMemberIdAndProjectId(memberId, id));
		return response;
	}

	//프로젝트 지원
	public void applyToProject(ProjectDto.Apply apply) {
		Long memberId = SecurityUtil.getCurrentMemberId();
		//이미 팀에 소속되어 있는지 확인
		if (projectTeamRepository.existsByMemberIdAndProjectId(memberId, apply.getProjectId())) {
			throw new CustomException(EXIST_PROJECT_MEMBER);
		}

		//지원 직무에 자리가 있는지 확인
		if (!projectTeamRepository.existsByProjectIdAndJobIdAndMemberIdIsNull(apply.getProjectId(), apply.getJobId())) {
			throw new CustomException(FULL_PROJECT_JOB_MEMBER);
		}

		//이미 지원했는지 확인
		if (projectApplicantRepository.existsByApplicantIdAndProjectId(memberId, apply.getProjectId())) {
			throw new CustomException(EXIST_PROJECT_APPLICANT);
		}
		ProjectApplicant applicant = ProjectApplicant.builder()
			.job(Job.builder().id(apply.getJobId()).build())
			.project(Project.builder().id(apply.getProjectId()).build())
			.applicant(Member.builder().id(memberId).build())
			.build();

		projectApplicantRepository.save(applicant);
	}

	//프로젝트 지원 취소
	public void deleteApplicant(Long projectId, Long memberId) {
		ProjectApplicant applicant = projectApplicantRepository.findByProjectIdAndApplicantId(projectId, memberId)
			.orElseThrow(() -> new CustomException(INVALID_ID));

		projectApplicantRepository.delete(applicant);
	}

	//지원자 수락
	public void acceptApplicant(Long applicantId) {
		ProjectApplicant applicant = projectApplicantRepository.findById(applicantId)
			.orElseThrow(() -> new CustomException(INVALID_APPLICANT_ID));

		//지원 직무에 자리가 있는지 확인
		ProjectTeam projectTeam = projectTeamRepository.findTopByProjectIdAndJobIdAndMemberIdIsNull(
			applicant.getProject().getId(),
			applicant.getJob().getId()).orElseThrow(() -> new CustomException(EXIST_PROJECT_APPLICANT));

		projectTeam.joinTeam(applicant.getApplicant());
		applicant.getApplicant().addPoint(PointType.JOIN_PROJECT);
		projectApplicantRepository.delete(applicant);

		if (!projectTeamRepository.existsByProjectIdAndMemberIdIsNull(projectTeam.getProject().getId())) {
			projectTeam.getProject().changeProjectState("C");
		}
	}

	//지원자 거절
	public void rejectApply(Long applicantId) {
		ProjectApplicant applicant = projectApplicantRepository.findById(applicantId)
			.orElseThrow(() -> new CustomException(INVALID_APPLICANT_ID));

		Long currentMemberId = SecurityUtil.getCurrentMemberId();

		if (!applicant.getProject().getLeader().getId().equals(currentMemberId)) {
			throw new CustomException(NO_PERMISSION);
		}

		projectApplicantRepository.delete(applicant);
	}

	//프로젝트 상태 변경(완료)
	public Project changeStateCompletion(Long projectId) {
		Project project = projectRepository.findById(projectId)
			.orElseThrow(() -> new CustomException(NOT_FOUND_PROJECT));

		if (!project.getState().equals("C")) {
			throw new CustomException(NOT_CONTINUE_PROJECT);
		}
		
		project.changeProjectState("E");
		return project;
	}
}
