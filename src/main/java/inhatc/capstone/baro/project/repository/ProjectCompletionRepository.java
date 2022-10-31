package inhatc.capstone.baro.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inhatc.capstone.baro.project.domain.ProjectCompletion;

public interface ProjectCompletionRepository extends JpaRepository<ProjectCompletion, Long> {
}
