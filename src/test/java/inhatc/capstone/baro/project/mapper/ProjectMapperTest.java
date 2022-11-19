package inhatc.capstone.baro.project.mapper;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import inhatc.capstone.baro.image.Image;
import inhatc.capstone.baro.job.Job;
import inhatc.capstone.baro.member.MemberRepository;
import inhatc.capstone.baro.member.domain.Member;
import inhatc.capstone.baro.project.domain.Project;
import inhatc.capstone.baro.project.domain.ProjectDetail;
import inhatc.capstone.baro.project.domain.ProjectTeam;
import inhatc.capstone.baro.project.dto.ProjectDto;
import inhatc.capstone.baro.project.repository.ProjectDetailRepository;
import inhatc.capstone.baro.project.repository.ProjectRepository;

@SpringBootTest
@Transactional
class ProjectMapperTest {
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	ProjectDetailRepository projectDetailRepository;

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
	@DisplayName("프로젝트 목록 개요 매핑 테스트")
	public void projectSummaryMappingTest() {
		final List<ProjectTeam> team = List.of(ProjectTeam.builder().id(1L).job(Job.builder().id(1L).build()).build(),
				ProjectTeam.builder().id(2L).job(Job.builder().id(2L).build()).build());
		final Project project = Project.builder()
				.id(1L)
				.viewCount(1L)
				.title("Test")
				.likeCount(1L)
				.leader(Member.builder().id(3L).nickname("TestMember").build())
				.purpose("테스트")
				.image(Image.builder().imagePath("TestImage.png").build())
				.team(team)
				.state("R")
				.build();
		final ProjectDto.Summary summary = ProjectMapper.INSTANCE.toSummary(project);
		assertThat(summary).isNotNull();
		assertThat(summary.getId()).isEqualTo(1L);
		assertThat(summary.getLeaderNickname()).isEqualTo("TestMember");
	}

	@Test
	@DisplayName("프로젝트 상세 매핑 테스트")
	public void projectDetailMappingTest() {
		ProjectDetail detail = projectDetailRepository.findAll().get(1);
		ProjectDto.Detail detail1 = ProjectMapper.INSTANCE.toDetail(detail);

		System.out.println(detail1.getEndDate());
	}
}
