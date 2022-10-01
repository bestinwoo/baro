package inhatc.capstone.baro.job;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {
	@Schema(description = "직무 번호")
	private Long id;
	@Schema(description = "직무 이름", defaultValue = "프론트엔드개발")
	private String name;
	@Schema(description = "직무 분류 깊이(ex.0->1->2)")
	private Long depth;
	@Schema(description = "해당 직무의 하위 분류", implementation = JobDto.class)
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
