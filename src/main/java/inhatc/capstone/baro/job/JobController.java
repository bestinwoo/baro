package inhatc.capstone.baro.job;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "직무", description = "직무 분류 API")
@RequiredArgsConstructor
public class JobController {
	private final JobRepository jobRepository;

	//TODO: 직무 첫 조회 후 애플리케이션 메모리에 저장해서 (캐시) DB 조회 안하게 하기
	@Operation(summary = "직무 조회")
	@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = JobDto.class)))
	@GetMapping("/job")
	public ResponseEntity<?> getJobList() {
		List<Job> list = jobRepository.findByParentIsNull();
		return ResponseEntity.ok(list.stream().map(JobDto::of).collect(Collectors.toList()));
	}
}
