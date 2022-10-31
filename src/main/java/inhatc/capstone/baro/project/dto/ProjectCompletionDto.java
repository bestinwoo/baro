package inhatc.capstone.baro.project.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

public class ProjectCompletionDto {
	@Getter
	@Setter
	public static class Write {
		@NotBlank(message = "프로젝트 개요는 필수 항목입니다.")
		private String summary;
		@NotBlank(message = "프로젝트 기능 명세는 필수 항목입니다.")
		private String functionSpecification;
		@NotBlank(message = "프로젝트 후기는 필수 항목입니다.")
		private String completionReview;
		private String projectResult;
		private String githubLink;
		@NotEmpty(message = "결과물 이미지는 필수 항목입니다.")
		private List<String> imageList;
		@NotNull(message = "프로젝트 ID는 필수 항목입니다.")
		private Long projectId;
	}
}
