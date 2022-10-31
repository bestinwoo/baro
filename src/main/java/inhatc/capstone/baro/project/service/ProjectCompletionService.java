package inhatc.capstone.baro.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inhatc.capstone.baro.image.Image;
import inhatc.capstone.baro.image.ImageRepository;
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
	public void projectComplete(ProjectCompletionDto.Write write) {
		List<Image> images = imageRepository.findByImagePathIn(write.getImageList());

		Project project = projectService.changeStateCompletion(write.getProjectId());
		ProjectCompletion completion = ProjectCompletion.createCompletion(write, project, images);
		projectCompletionRepository.save(completion);
	}
	//완성작 조회
}
