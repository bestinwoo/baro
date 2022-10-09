package inhatc.capstone.baro.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import inhatc.capstone.baro.project.domain.ProjectDetail;

public interface ProjectDetailRepository extends JpaRepository<ProjectDetail, Long> {
	Optional<ProjectDetail> findById(Long id);
}
