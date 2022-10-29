package inhatc.capstone.baro.member;

import static inhatc.capstone.baro.exception.ErrorCode.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inhatc.capstone.baro.exception.CustomException;
import inhatc.capstone.baro.image.Image;
import inhatc.capstone.baro.image.ImageRepository;
import inhatc.capstone.baro.job.Job;
import inhatc.capstone.baro.job.JobRepository;
import inhatc.capstone.baro.jwt.SecurityUtil;
import inhatc.capstone.baro.member.domain.Member;
import inhatc.capstone.baro.member.dto.MemberDto;
import inhatc.capstone.baro.project.dto.ProjectDto;
import inhatc.capstone.baro.project.repository.ProjectApplicantRepository;
import inhatc.capstone.baro.project.repository.ProjectRepository;
import inhatc.capstone.baro.ranking.School;
import inhatc.capstone.baro.ranking.SchoolRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Service
public class MemberService {
	private final MemberRepository memberRepository;
	private final JobRepository jobRepository;
	private final ImageRepository imageRepository;
	private final ProjectApplicantRepository applicantRepository;
	private final ProjectRepository projectRepository;
	private final SchoolRepository schoolRepository;

	/**
	 * 회원 가입
	 * @param register 회원 가입에 필요한 정보 (닉네임, 직무, 대학교)
	 */
	public void join(MemberDto.Register register) {
		Member member = memberRepository.findById(register.getId()).orElseThrow(() -> new CustomException(INVALID_ID));
		if (!member.isFirst()) { // 이미 가입된 유저일경우 exception throw
			throw new CustomException(EXIST_MEMBER);
		}
		
		if (!schoolRepository.existsByName(register.getUniversity())) {
			schoolRepository.save(School.builder().name(register.getUniversity()).point(0L).build());
		}
		member.registerMember(register);
	}

	/**
	 * 회원정보 조회
	 * @param memberId 조회할 대상 회원 ID
	 * @return 회원정보
	 */
	@Transactional(readOnly = true)
	public MemberDto.Info getMemberInfo(Long memberId) {
		Member member = memberRepository.findByIdAndIsFirstIsFalse(memberId)
			.orElseThrow(() -> new CustomException(NOT_FOUND_MEMBER));

		return MemberDto.Info.from(member);
	}

	/**
	 *
	 * @param memberId 수정할 멤버 ID
	 * @param modify 수정할 정보
	 * @return 수정 후 멤버 정보
	 */
	public MemberDto.Info modifyMemberInfo(Long memberId, MemberDto.Modify modify) {
		if (!SecurityUtil.getCurrentMemberId().equals(memberId)) {
			throw new CustomException(NO_PERMISSION);
		}
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(NOT_FOUND_MEMBER));
		Optional<Job> job = Optional.empty();
		Optional<Image> image = Optional.empty();

		if (modify.getJobId() != null) {
			job = jobRepository.findById(modify.getJobId());
		}
		if (modify.getImageUrl() != null) {
			image = imageRepository.findById(modify.getImageUrl());
		}
		Member updateMemberInfo = member.updateMemberInfo(image, job, modify);

		return MemberDto.Info.from(updateMemberInfo);
	}

	/**
	 * 프로젝트 현황 조회
	 * @param memberId 조회할 멤버 ID
	 * @return apply : 지원 현황, progress : 진행중(팀에 소속되었으나 완료되지 않음), complete : 완료된 프로젝트
	 */
	public ProjectDto.MyPage getMyProject(Long memberId) {
		ProjectDto.MyPage myProject = new ProjectDto.MyPage();
		List<ProjectDto.Summary> apply = applicantRepository.findByApplicantId(memberId)
			.stream()
			.map(ap -> ProjectDto.Summary.from(ap.getProject()))
			.collect(
				Collectors.toList());
		myProject.setApply(apply);

		List<ProjectDto.Summary> memberProject = projectRepository.findByMemberId(memberId)
			.stream()
			.map(ProjectDto.Summary::from)
			.collect(Collectors.toList());

		myProject.setProgress(
			memberProject.stream()
				.filter(p -> !p.getState().equals("E"))
				.collect(Collectors.toList())
		);

		myProject.setComplete(
			memberProject.stream()
				.filter(p -> p.getState().equals("E"))
				.collect(Collectors.toList())
		);

		return myProject;
	}
}
