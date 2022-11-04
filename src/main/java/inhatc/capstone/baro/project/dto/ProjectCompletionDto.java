package inhatc.capstone.baro.project.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import inhatc.capstone.baro.image.Image;
import inhatc.capstone.baro.project.domain.ProjectCompletion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCompletionDto {
	@NotBlank(message = "프로젝트 개요는 필수 항목입니다.")
	private String summary;
	@NotBlank(message = "프로젝트 개발 설명은 필수 항목입니다.")
	private String description;
	private String projectResult;
	private String githubLink;
	@NotEmpty(message = "결과물 이미지는 필수 항목입니다.")
	private List<String> imageList;
	@NotNull(message = "프로젝트 ID는 필수 항목입니다.")
	private Long projectId;

	public static ProjectCompletionDto from(ProjectCompletion completion) {
		return ProjectCompletionDto.builder()
			.summary(completion.getSummary())
			.description(completion.getDescription())
			.projectResult(completion.getProjectResult())
			.githubLink(completion.getGithubLink())
			.imageList(completion.getImages().stream().map(Image::getImagePath).collect(Collectors.toList()))
			.projectId(completion.getId())
			.build();
	}

}
