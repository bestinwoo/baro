package inhatc.capstone.baro.job;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class JobController {
	private final JobRepository jobRepository;

	@GetMapping("/job")
	public ResponseEntity<?> getJobList() {
		List<Job> list = jobRepository.findAll();
		return ResponseEntity.ok(list.stream().map(JobDto::of).collect(Collectors.toList()));
	}
}
