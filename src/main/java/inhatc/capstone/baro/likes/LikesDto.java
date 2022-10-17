package inhatc.capstone.baro.likes;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikesDto {
	@NotNull(message = "프로젝트 ID를 입력해주세요.")
	@Schema(description = "좋아요할 프로젝트 ID", required = true)
	private Long projectId;
}
