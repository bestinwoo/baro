package inhatc.capstone.baro.project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import inhatc.capstone.baro.project.dto.ProjectDto;
import inhatc.capstone.baro.project.service.ProjectService;

@SpringBootTest
class ProjectServiceTest {
	@Autowired
	ProjectService projectService;

	@Test
	public void getProjectList() {
		ProjectDto.Request request = ProjectDto.Request.builder()
			// .school("인하공업전문")
			// .purpose("사이드 프로젝트")
			// .state("R")
			.jobId(15L)
			.build();

		PageRequest pageRequest = PageRequest.of(0, 10);
		Page<ProjectDto.Summary> projectList = projectService.getProjectList(pageRequest, request);

		for (ProjectDto.Summary summary : projectList) {
			System.out.println(summary.getId() + " " + summary.getTitle());
		}

	}
}
