package inhatc.capstone.baro.project.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import inhatc.capstone.baro.project.domain.Project;
import inhatc.capstone.baro.project.dto.ProjectDto;

public interface ProjectRepositoryCustom {
	/**
	 * 프로젝트 목록 조회에서 여러 조건으로 복합 조회
	 * @param request
	 * @param pageable
	 * @return 조건에 맞는 Project PageList
	 */
	Page<Project> findByCondition(ProjectDto.Request request, Pageable pageable);

	/**
	 * 대상 멤버 ID가 프로젝트 팀에 소속되어 있는 프로젝트 리스트 조회
	 * @param memberId 프로젝트에 소속되어 있는지 조회할 멤버 ID
	 * @return 해당 멤버가 소속된 프로젝트 List
	 */
	List<Project> findByMemberId(Long memberId);
}
