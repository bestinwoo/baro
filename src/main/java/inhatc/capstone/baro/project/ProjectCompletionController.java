package inhatc.capstone.baro.project;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import inhatc.capstone.baro.project.dto.ProjectCompletionDto;
import inhatc.capstone.baro.project.service.ProjectCompletionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project/completion")
@Tag(name = "프로젝트 완성작 API")
public class ProjectCompletionController {
	private final ProjectCompletionService completionService;

	@Operation(summary = "완성작 등록")
	@PostMapping
	public ResponseEntity<?> writeProjectCompletion(@RequestBody ProjectCompletionDto.Write write) {
		completionService.projectComplete(write);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
