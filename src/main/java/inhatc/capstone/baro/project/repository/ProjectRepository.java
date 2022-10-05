package inhatc.capstone.baro.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inhatc.capstone.baro.project.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryCustom {

}

