package inhatc.capstone.baro.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import inhatc.capstone.baro.project.domain.ProjectApplicant;

public interface ProjectApplicantRepository extends JpaRepository<ProjectApplicant, Long> {
	boolean existsByApplicantId(Long applicantId);

	Optional<ProjectApplicant> findByProjectIdAndApplicantId(Long projectId, Long applicantId);

	List<ProjectApplicant> findByApplicantId(Long applicantId);
}
