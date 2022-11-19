package inhatc.capstone.baro.project.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import inhatc.capstone.baro.project.domain.Project;
import inhatc.capstone.baro.project.domain.ProjectApplicant;
import inhatc.capstone.baro.project.domain.ProjectDetail;
import inhatc.capstone.baro.project.domain.ProjectSkill;
import inhatc.capstone.baro.project.domain.ProjectTeam;
import inhatc.capstone.baro.project.dto.ProjectDto;

@Mapper
public interface ProjectMapper {
	ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

	default ProjectDto.Summary toSummary(Project project) {
		ProjectDto.Summary summary = ProjectDto.Summary.builder()
				.id(project.getId())
				.title(project.getTitle())
				.state(project.getState())
				.leaderId(project.getLeader().getId())
				.leaderNickname(project.getLeader().getNickname())
				.likeCount(project.getLikeCount())
				.viewCount(project.getViewCount())
				.purpose(project.getPurpose())
				.imagePath(project.getImage().getImagePath())
				.build();

		List<ProjectDto.RecruitJob> jobList = new ArrayList<>();
		//모집 분야별로 grouping
		Map<Long, List<ProjectTeam>> collect = project.getTeam()
				.stream()
				.collect(Collectors.groupingBy(t -> t.getJob().getId()));

		for (Map.Entry<Long, List<ProjectTeam>> entry : collect.entrySet()) {
			List<ProjectTeam> team = entry.getValue();

			//리더는 모집 인원에서 제외하도록 filter 처리
			team = team.stream().filter(t -> {
				if (t.getMember() != null) {
					return !t.getMember().getId().equals(project.getLeader().getId());
				} else {
					return true;
				}
			}).collect(Collectors.toList());

			if (team.size() < 1) {
				continue;
			}

			ProjectDto.RecruitJob job = ProjectDto.RecruitJob.builder()
					.jobId(entry.getKey())
					.recruitCount(team.size())
					.completeCount(team.stream().filter(t -> t.getMember() != null).count())
					.jobName(team.get(0).getJob().getName())
					.build();

			jobList.add(job);
		}
		summary.setJobs(jobList);

		return summary;
	}

	default Set<String> toSkillSet(List<ProjectSkill> skills) {
		return skills.stream().map(ProjectSkill::getName).collect(Collectors.toSet());
	}

	//TODO: from -> Mapper로 변경하기
	default List<ProjectDto.TeamMember> toDetailTeamMember(List<ProjectTeam> team) {
		return team.stream()
				.filter(t -> t.getMember() != null)
				.map(ProjectDto.TeamMember::from)
				.collect(Collectors.toList());
	}

	default List<ProjectDto.TeamMember> toDetailApplicants(List<ProjectApplicant> applicants) {
		return applicants.stream().map(ProjectDto.TeamMember::from).collect(Collectors.toList());
	}

	@Mappings({
			@Mapping(source = "project", target = "summary"),
			@Mapping(source = "content", target = "description"),
			@Mapping(source = "lounge", target = "ideaDetail"),
			@Mapping(source = "lounge.member.id", target = "ideaDetail.memberId"),
			@Mapping(source = "lounge.member.nickname", target = "ideaDetail.memberNickname"),
			@Mapping(source = "lounge.member.userProfileImage.imagePath", target = "ideaDetail.memberProfileUrl"),
			@Mapping(source = "project.skill", target = "skill"),
			@Mapping(source = "project.team", target = "team"),
			@Mapping(source = "project.applicants", target = "applicants")
	})
	ProjectDto.Detail toDetail(ProjectDetail projectDetail);
}
