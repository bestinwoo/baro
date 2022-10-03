package inhatc.capstone.baro.project;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import inhatc.capstone.baro.project.dto.ProjectDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "프로젝트 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {
	private final ProjectService projectService;

	@Operation(summary = "프로젝트 생성", description = "생성 전 이미지 업로드 API로 이미지 링크 획득 후 생성")
	@PostMapping()
	public ResponseEntity<?> createProject(@Validated @RequestBody ProjectDto.Create request) {
		projectService.createProject(request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
