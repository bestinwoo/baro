package inhatc.capstone.baro.project.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import inhatc.capstone.baro.image.Image;
import inhatc.capstone.baro.project.dto.ProjectCompletionDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProjectCompletion {
	@Id
	private Long id;
	//개요
	@NotNull(message = "프로젝트 개요는 필수 항목입니다.")
	private String summary;
	//프로젝트 기능명세
	@NotNull(message = "프로젝트 기능 명세는 필수 항목입니다.")
	private String functionSpecification;
	//완성후기
	@NotNull(message = "프로젝트 후기는 필수 항목입니다.")
	private String completionReview;
	//성과
	private String projectResult;
	//깃허브
	private String githubLink;
	//이미지 목록
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Image> images;

	@OneToOne
	@MapsId
	@JoinColumn(name = "id")
	private Project project;

	public static ProjectCompletion createCompletion(ProjectCompletionDto write, Project project,
		List<Image> images) {
		ProjectCompletion completion = ProjectCompletion.builder()
			.project(project)
			.summary(write.getSummary())
			.functionSpecification(write.getFunctionSpecification())
			.completionReview(write.getCompletionReview())
			.projectResult(write.getProjectResult())
			.githubLink(write.getGithubLink())
			.build();

		completion.images = new ArrayList<>();
		for (Image image : images) {
			image.setProject(project);
			completion.images.add(image);
		}

		return completion;
	}
}
