package inhatc.capstone.baro.job;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {
	private Long id;
	private String name;
	private Long depth;
	private List<JobDto> children;

	public static JobDto of(Job job) {
		return JobDto.builder()
			.id(job.getId())
			.name(job.getName())
			.depth(job.getDepth())
			.children(job.getChildren().stream().map(JobDto::of).collect(Collectors.toList()))
			.build();
	}
}
