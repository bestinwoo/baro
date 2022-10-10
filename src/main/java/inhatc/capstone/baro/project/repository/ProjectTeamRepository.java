package inhatc.capstone.baro.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inhatc.capstone.baro.project.domain.ProjectTeam;

public interface ProjectTeamRepository extends JpaRepository<ProjectTeam, Long> {
	boolean existsByMemberIdAndProjectId(Long memberId, Long projectId);

	boolean existsByProjectIdAndJobIdAndMemberIdIsNull(Long projectId, Long jobId);
}
