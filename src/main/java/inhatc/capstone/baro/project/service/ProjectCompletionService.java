package inhatc.capstone.baro.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inhatc.capstone.baro.exception.CustomException;
import inhatc.capstone.baro.exception.ErrorCode;
import inhatc.capstone.baro.image.Image;
import inhatc.capstone.baro.image.ImageRepository;
import inhatc.capstone.baro.jwt.SecurityUtil;
import inhatc.capstone.baro.project.domain.Project;
import inhatc.capstone.baro.project.domain.ProjectCompletion;
import inhatc.capstone.baro.project.dto.ProjectCompletionDto;
import inhatc.capstone.baro.project.repository.ProjectCompletionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ProjectCompletionService {
	private final ProjectCompletionRepository projectCompletionRepository;
	private final ImageRepository imageRepository;
	private final ProjectService projectService;

	//완성작 등록
	public void projectComplete(ProjectCompletionDto write) {
		List<Image> images = imageRepository.findByImagePathIn(write.getImageList());
		Project project = projectService.changeStateCompletion(write.getProjectId());
		Long currentMemberId = SecurityUtil.getCurrentMemberId();

		if (!project.getLeader().getId().equals(currentMemberId)) {
			throw new CustomException(ErrorCode.NO_PERMISSION);
		}

		ProjectCompletion completion = ProjectCompletion.createCompletion(write, project, images);
		projectCompletionRepository.save(completion);
	}

	//완성작 조회
	public ProjectCompletionDto findProjectCompletionById(Long projectId) {
		ProjectCompletion projectCompletion = projectCompletionRepository.findById(projectId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PROJECT));

		return ProjectCompletionDto.from(projectCompletion);
	}
}
