package inhatc.capstone.baro.project.repository;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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

}
