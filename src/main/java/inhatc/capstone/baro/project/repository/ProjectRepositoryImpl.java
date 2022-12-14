package inhatc.capstone.baro.project.repository;

import static inhatc.capstone.baro.project.domain.QProject.*;
import static inhatc.capstone.baro.project.domain.QProjectTeam.*;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import inhatc.capstone.baro.member.domain.QMember;
import inhatc.capstone.baro.project.domain.Project;
import inhatc.capstone.baro.project.domain.QProject;
import inhatc.capstone.baro.project.domain.QProjectSkill;
import inhatc.capstone.baro.project.dto.ProjectDto;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Project> findByCondition(ProjectDto.Request request, Pageable pageable) {
		List<Project> projectList = queryFactory
				.selectFrom(project)
				.distinct()
				.innerJoin(project.leader, QMember.member)
				.leftJoin(project.team, projectTeam)

				.leftJoin(project.skill, QProjectSkill.projectSkill)
				.where(
						likeSchool(request.getSchool()),
						eqPurpose(request.getPurpose()),
						eqJob(request.getJobId()),
						eqState(request.getState())
				)
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();

		JPAQuery<Long> countQuery = queryFactory
				.select(project.countDistinct())
				.from(project)
				.leftJoin(project.team, projectTeam)
				.where(
						likeSchool(request.getSchool()),
						eqPurpose(request.getPurpose()),
						eqJob(request.getJobId()),
						eqState(request.getState())
				);

		return PageableExecutionUtils.getPage(projectList, pageable, countQuery::fetchOne);
	}

	@Override
	public List<Project> findByMemberId(Long memberId) {
		return queryFactory.selectFrom(QProject.project)
				.innerJoin(QProject.project.team, projectTeam)
				.where(eqTeamMemberId(memberId))
				.fetch();
	}

	private BooleanExpression likeSchool(String school) {
		if (!StringUtils.hasText(school)) {
			return null;
		}
		return project.leader.university.like("%" + school + "%");
	}

	private BooleanExpression eqPurpose(String purpose) {
		if (!StringUtils.hasText(purpose)) {
			return null;
		}
		return project.purpose.eq(purpose);
	}

	private BooleanExpression eqJob(Long jobId) {
		if (Objects.isNull(jobId)) {
			return null;
		}
		return projectTeam.job.id.eq(jobId);
	}

	private BooleanExpression eqState(String state) {
		if (!StringUtils.hasText(state)) {
			return null;
		}
		return project.state.eq(state);
	}

	private BooleanExpression eqTeamMemberId(Long memberId) {
		return projectTeam.member.id.eq(memberId);
	}
}
