package inhatc.capstone.baro.project.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import inhatc.capstone.baro.project.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryCustom {
	Page<Project> findAllByOrderByCreateDateDesc(Pageable pageable);

	List<Project> findTop3ByOrderByViewCountDesc();
}

