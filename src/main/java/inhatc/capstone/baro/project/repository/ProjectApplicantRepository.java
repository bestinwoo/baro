package inhatc.capstone.baro.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inhatc.capstone.baro.project.domain.ProjectApplicant;

public interface ProjectApplicantRepository extends JpaRepository<ProjectApplicant, Long> {
	boolean existsByApplicantId(Long applicantId);
}
