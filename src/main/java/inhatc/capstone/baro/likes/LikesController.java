package inhatc.capstone.baro.likes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "좋아요")
@RestController
@RequiredArgsConstructor

public class LikesController {
	private final LikesService likesService;

	//좋아요
	@Operation(summary = "좋아요")
	@ApiResponse(responseCode = "201", description = "좋아요 완료")
	@PostMapping("/like")
	public ResponseEntity<?> likeProject(@Validated @RequestBody LikesDto likesDto) {
		likesService.likeProject(likesDto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	//좋아요 취소
	@Operation(summary = "좋아요 취소")
	@ApiResponse(responseCode = "204", description = "좋아요 취소 완료")
	@DeleteMapping("/unlike")
	public ResponseEntity<?> unlikeProject(@Validated @RequestBody LikesDto likesDto) {
		likesService.unlikeProject(likesDto);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
