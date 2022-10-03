package inhatc.capstone.baro.project.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import inhatc.capstone.baro.job.Job;
import inhatc.capstone.baro.member.domain.Member;
import inhatc.capstone.baro.project.dto.ProjectDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
public class ProjectTeam {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	public ProjectTeam setProject(Project project) {
		this.project = project;
		return this;
	}

	@ManyToOne
	@JoinColumn(name = "skill_id")
	private Job job;

	public static ProjectTeam createTeam(ProjectDto.RecruitJob job) {
		return ProjectTeam.builder()
			.job(Job.builder().id(job.getJobId()).build()).build();
	}
}
