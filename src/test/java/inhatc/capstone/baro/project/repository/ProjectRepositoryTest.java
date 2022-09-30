package inhatc.capstone.baro.project.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import inhatc.capstone.baro.member.MemberRepository;
import inhatc.capstone.baro.member.domain.Member;
import inhatc.capstone.baro.project.domain.Project;
import inhatc.capstone.baro.project.domain.ProjectDetail;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProjectRepositoryTest {
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	ProjectDetailRepository projectDetailRepository;
	@Autowired
	MemberRepository memberRepository;

	public void createProject() {
		Member member = Member.builder()
			.email("Test@gmail.com")
			.isFirst(false)
			.oauth2Id("0124495")
			.nickname("Test")
			.university("인하공업전문대학")
			.build();

		memberRepository.save(member);

		Project project = Project.builder()
			.likeCount(0L)
			.title("Test")
			.viewCount(0L)
			.leader(member)
			.build();

		ProjectDetail detail = ProjectDetail.builder()
				.content("Test Content")
					.purpose("사이드 프로젝트")
						.build();

		projectDetailRepository.save(detail);
		project.setDetail(detail);
		projectRepository.save(project);

	}

	@Test
	@DisplayName("Lazy 확인")
	public void checkFetchLazy() {
		createProject();
		List<Project> all = projectRepository.findAll();
		Project project = all.get(0);
		System.out.println("project title = " + project.getTitle());
		System.out.println("project content = " + project.getDetail().getContent());
		System.out.println("project member email = " + project.getLeader().getEmail());
	}

}
