package inhatc.capstone.baro.project.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import inhatc.capstone.baro.project.domain.ProjectApplicant;
import inhatc.capstone.baro.project.dto.ProjectDto;

@SpringBootTest
@Transactional
class ProjectApplicantRepositoryTest {
	@Autowired
	ProjectApplicantRepository applicantRepository;

	@Test
	void findByApplicantId() {
		List<ProjectApplicant> applicants = applicantRepository.findByApplicantId(1L);

		for (ProjectApplicant applicant : applicants) {
			ProjectDto.Summary from = ProjectDto.Summary.from(applicant.getProject());
			System.out.println(from.getTitle());
		}
	}
}
