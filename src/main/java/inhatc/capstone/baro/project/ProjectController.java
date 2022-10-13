package inhatc.capstone.baro.project;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import inhatc.capstone.baro.exception.ErrorResponse;
import inhatc.capstone.baro.project.dto.ProjectDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "프로젝트 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {
	private final ProjectService projectService;

	@Operation(summary = "프로젝트 생성", description = "생성 전 이미지 업로드 API로 이미지 링크 획득 후 생성")
	@ApiResponse(responseCode = "201", description = "생성 완료")
	@PostMapping()
	public ResponseEntity<?> createProject(@Validated @RequestBody ProjectDto.Create request) {
		projectService.createProject(request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(summary = "프로젝트 목록 조회", description = "프로젝트 목록")
	@GetMapping
	public ResponseEntity<Page<ProjectDto.Summary>> getProjectList(ProjectDto.Request request,
		@PageableDefault(size = 10) Pageable pageable) {
		Page<ProjectDto.Summary> projectList = projectService.getProjectList(
			PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize()), request);
		return ResponseEntity.ok(projectList);
	}

	@Operation(summary = "최근 프로젝트 조회")
	@Parameter(description = "가져올 개수", required = true)
	@GetMapping("/recent")
	public ResponseEntity<List<ProjectDto.Summary>> getRecentProjects(@RequestParam int size) {
		List<ProjectDto.Summary> recentProject = projectService.getRecentProject(size);
		return ResponseEntity.ok(recentProject);
	}

	//주목할만한 프로젝트
	@Operation(summary = "주목할만한 프로젝트 조회")
	@GetMapping("/popular")
	public ResponseEntity<List<ProjectDto.Summary>> getRecentProjects() {
		List<ProjectDto.Summary> popularProject = projectService.getPopularProject();
		return ResponseEntity.ok(popularProject);
	}

	//프로젝트 상세 조회
	@Operation(summary = "프로젝트 상세 조회")
	@GetMapping("/{id}")
	public ResponseEntity<ProjectDto.Detail> getProjectDetail(@PathVariable Long id) {
		return ResponseEntity.ok(projectService.getProjectDetail(id));
	}

	//프로젝트 지원하기
	@Operation(summary = "프로젝트 지원하기")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "지원 완료"),
		@ApiResponse(responseCode = "400", description = "지원 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping("/apply")
	public ResponseEntity<?> applyProject(@RequestBody ProjectDto.Apply apply) {
		projectService.applyToProject(apply);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	//프로젝트 지원 취소
	@Operation(summary = "프로젝트 지원 취소")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "취소 완료"),
		@ApiResponse(responseCode = "400", description = "취소 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@DeleteMapping("/apply/{projectId}")
	public ResponseEntity<?> cancelApply(@PathVariable Long projectId) {
		projectService.cancelApplicant(projectId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}


