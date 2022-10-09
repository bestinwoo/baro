package inhatc.capstone.baro.project.repository;

import static inhatc.capstone.baro.project.domain.QProject.*;
import static inhatc.capstone.baro.project.domain.QProjectDetail.*;
import static inhatc.capstone.baro.project.domain.QProjectTeam.*;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import inhatc.capstone.baro.member.MemberRepository;
import inhatc.capstone.baro.member.domain.Member;
import inhatc.capstone.baro.member.domain.QMember;
import inhatc.capstone.baro.project.domain.Project;
import inhatc.capstone.baro.project.domain.ProjectDetail;
import inhatc.capstone.baro.project.domain.QProject;

@SpringBootTest
@Transactional
class ProjectRepositoryTest {
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	ProjectDetailRepository projectDetailRepository;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	JPAQueryFactory factory;

	public void createProject() {
		for (int i = 0; i < 2; i++) {
			Member member = Member.builder()
				.email("Test@gmail.com")
				.isFirst(false)
				.oauth2Id("0124495" + i)
				.nickname("Test")
				.university("인하공업전문대학")
				.build();

			memberRepository.save(member);

			Project project = Project.builder()
				.likeCount(0L)
				.title("Test" + i)
				.viewCount(0L)
				.leader(member)
				.build();

			ProjectDetail detail = ProjectDetail.builder()
				.content("Test Content" + i)
				.build();
			detail.setProject(project);
			projectDetailRepository.save(detail);
			//project.setDetail(detail);
			projectRepository.save(project);
		}

	}

	@Test
	@DisplayName("프로젝트 MapsId 확인")
	public void checkMapsId() {
		createProject();
		List<Project> all = projectRepository.findAll();
		Project project = all.get(0);
		System.out.println("project title = " + project.getTitle());
		//	System.out.println("project content = " + project.getDetail().getContent());
		System.out.println("project member email = " + project.getLeader().getEmail());
	}

	@Test
	@DisplayName("Detail Repository 테스트")
	public void detailRepoTest() {
		List<ProjectDetail> all = projectDetailRepository.findAll();
		ProjectDetail detail = all.get(0);
		System.out.println(detail.getContent());
		System.out.println(detail.getProject().getTitle());
	}

	@Test
	@DisplayName("Detail Querydsl 테스트")
	public void detailQuerydslTest() {
		List<ProjectDetail> fetch = factory.select(projectDetail)
			.from(projectDetail)
			.innerJoin(projectDetail.project, QProject.project)
			.fetchJoin()
			.fetch();

		ProjectDetail detail = fetch.get(0);
		System.out.println(detail.getContent());
		System.out.println(detail.getProject().getTitle());

	}

	@Test
	@DisplayName("페이징 쿼리 테스트")
	public void pageQueryTest() {
		List<Project> fetch = factory.select(project)
			.from(project)
			.distinct()
			.innerJoin(project.leader, QMember.member)
			.leftJoin(project.team, projectTeam)
			//.fetchJoin()
			.where(
				projectTeam.project.id.eq(project.id),
				likeSchool("인하공업전문대"),
				eqPurpose("사이드 프로젝트"),
				eqJob(12L)
			)
			.offset(5)
			.limit(1)
			.fetch();

		System.out.println(fetch.get(0).getTeam().get(0).getJob().getName());

		// JPAQuery<Project> countQuery = factory
		// 	.select(project)
		// 	.from(project)
		// 	.distinct()
		// 	.innerJoin(project.leader, QMember.member)
		// 	.leftJoin(project.team, projectTeam)
		// 	.where(
		// 		projectTeam.project.id.eq(project.id),
		// 		likeSchool("인하공업전문대"),
		// 		eqPurpose("사이드 프로젝트"),
		// 		eqJob(12L)
		// 	);

		//	System.out.println(countQuery.fetch());
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

}
