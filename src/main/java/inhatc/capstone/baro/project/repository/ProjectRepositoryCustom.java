package inhatc.capstone.baro.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import inhatc.capstone.baro.project.domain.Project;
import inhatc.capstone.baro.project.dto.ProjectDto;

public interface ProjectRepositoryCustom {
	Page<Project> findByCondition(ProjectDto.Request request, Pageable pageable);
}
