package inhatc.capstone.baro.project.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import inhatc.capstone.baro.project.domain.Project;
import inhatc.capstone.baro.project.domain.ProjectTeam;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ProjectDto {
	@Getter
	@Setter
	public static class Create {
		@NotBlank(message = "프로젝트명을 입력해주세요.")
		@Schema(description = "프로젝트명", defaultValue = "인하옥션", required = true)
		private String title;

		@NotBlank(message = "프로젝트 목적을 입력해주세요.")
		@Schema(description = "프로젝트 목적", defaultValue = "사이드 프로젝트")
		private String purpose;

		@NotNull(message = "프로젝트 이미지를 등록해주세요.")
		@Schema(description = "프로젝트 이미지 URL")
		private String thumbnailLink;

		@NotNull(message = "모집분야를 설정해주세요.")
		@ArraySchema(schema = @Schema(description = "모집분야 List", implementation = RecruitJob.class))
		List<RecruitJob> recruitJobs;

		@NotNull(message = "프로젝트 기술 스택을 입력해주세요.")
		@Schema(description = "프로젝트 기술 스택 ID List")
		List<String> skillIds;

		@NotBlank(message = "프로젝트 설명을 입력해주세요.")
		@Schema(description = "프로젝트 설명")
		private String description;

		@Schema(description = "오픈 카톡방 링크")
		private String openTalkLink;

		@Schema(description = "참고한 아이디어 ID", required = false)
		private Long loungeId;

		@NotNull(message = "프로젝트 시작일을 입력해주세요.")
		@Schema(description = "프로젝트 시작일")
		private LocalDate startDate;

		@NotNull(message = "프로젝트 종료일을 입력해주세요.")
		@Schema(description = "프로젝트 종료일")
		private LocalDate endDate;

		@Schema(description = "리더 ID")
		@NotNull(message = "프로젝트 리더 ID를 입력해주세요.")
		private Long leaderId;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class RecruitJob {
		@Schema(description = "직무 ID", required = true)
		private Long jobId;
		@Schema(description = "모집 인원", required = true)
		@Size(min = 1, max = 9, message = "모집 인원은 최소 1명, 최대 9명입니다.")
		private int recruitCount;
		@Schema(description = "모집 완료 인원, 프로젝트 생성 시에는 보낼 필요 없음")
		private Long completeCount;
	}

	@Getter
	@Setter
	@Builder
	public static class Summary {
		private Long id;
		private String title;
		private String leaderNickname;
		private List<RecruitJob> jobs;

		public static Summary from(Project project) {
			Summary summary = Summary.builder()
				.id(project.getId())
				.title(project.getTitle())
				.leaderNickname(project.getLeader().getNickname())
				.build();

			List<RecruitJob> jobList = new ArrayList<>();
			Map<Long, List<ProjectTeam>> collect = project.getTeam()
				.stream()
				.collect(Collectors.groupingBy(t -> t.getJob().getId()));

			for (Map.Entry<Long, List<ProjectTeam>> entry : collect.entrySet()) {
				List<ProjectTeam> team = entry.getValue();
				RecruitJob job = RecruitJob.builder()
					.jobId(entry.getKey())
					.recruitCount(team.size())
					.completeCount(team.stream().filter(t -> t.getMember() != null).count())
					.build();
				jobList.add(job);
			}
			summary.setJobs(jobList);

			return summary;
		}
	}

	@Getter
	@Setter
	@Builder
	public static class Request {
		private String school;
		private String purpose;
		private Long jobId;
		private String state;
	}

}
