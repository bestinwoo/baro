package inhatc.capstone.baro.project.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import inhatc.capstone.baro.image.Image;
import inhatc.capstone.baro.member.domain.Member;
import inhatc.capstone.baro.project.dto.ProjectDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private Long viewCount;
	private Long likeCount;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "leader_id")
	private Member leader;
	@OneToOne
	@JoinColumn(name = "image_path")
	private Image image;

	@OneToOne(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private ProjectDetail detail;

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ProjectSkill> skill;
	private String state;
	private LocalDateTime createDate;

	public void setSkill(List<ProjectSkill> skill) {
		this.skill = skill;
	}

	public void setTeam(List<ProjectTeam> team) {
		this.team = team;
	}

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ProjectTeam> team;

	public void setDetail(ProjectDetail detail) {
		this.detail = detail;
	}

	/**
	 * 새 프로젝트 생성
	 * @param create
	 * @return Project
	 */
	public static Project createProject(ProjectDto.Create create) {
		Project project = Project.builder()
			.title(create.getTitle())
			.viewCount(0L)
			.likeCount(0L)
			.createDate(LocalDateTime.now())
			.state("R")
			.leader(Member.builder().id(create.getLeaderId()).build())
			.image(Image.builder().imagePath(create.getThumbnailLink()).build())
			.build();

		ProjectDetail detail = ProjectDetail.from(create);

		detail.setProject(project);
		project.setDetail(detail);

		List<ProjectSkill> skills = create.getSkillIds().stream().map(m ->
			ProjectSkill.builder().name(m).project(project).build()
		).collect(Collectors.toList());

		project.setSkill(skills);

		List<ProjectTeam> team = new ArrayList<>();

		team.add(ProjectTeam.createTeam(create.getLeaderJobId(), project.leader).setProject(project));

		for (int i = 0; i < create.getRecruitJobs().size(); i++) {
			ProjectDto.RecruitJob recruitJob = create.getRecruitJobs().get(i);
			for (int j = 0; j < recruitJob.getRecruitCount(); j++) {
				team.add(ProjectTeam.createTeam(recruitJob.getJobId()).setProject(project));
			}
		}
		project.setTeam(team);

		return project;
	}
}
