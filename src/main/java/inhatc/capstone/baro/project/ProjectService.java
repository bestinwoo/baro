package inhatc.capstone.baro.project;

import static inhatc.capstone.baro.exception.ErrorCode.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inhatc.capstone.baro.exception.CustomException;
import inhatc.capstone.baro.image.ImageRepository;
import inhatc.capstone.baro.project.domain.Project;
import inhatc.capstone.baro.project.dto.ProjectDto;
import inhatc.capstone.baro.project.dto.ProjectDto.Request;
import inhatc.capstone.baro.project.dto.ProjectDto.Summary;
import inhatc.capstone.baro.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ProjectService {
	private final ProjectRepository projectRepository;
	private final ImageRepository imageRepository;

	//프로젝트 생성
	public void createProject(ProjectDto.Create request) {
		//이미지를 먼저 서버에 업로드해야 이미지 첨부 가능
		imageRepository.findById(request.getThumbnailLink())
			.orElseThrow(() -> new CustomException(NOT_FOUND_IMAGE));

		Project project = Project.createProject(request);
		if (project.getLeader().isFirst()) { // 가입하지 않은 회원 프로젝트 생성 불가
			throw new CustomException(NOT_JOINED);
		}

		projectRepository.save(project);
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

	//프로젝트 수정

	//프로젝트 삭제

	//프로젝트 좋아요
}
