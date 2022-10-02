package inhatc.capstone.baro.image;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import inhatc.capstone.baro.exception.ErrorCode;
import inhatc.capstone.baro.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "이미지 API", description = "이미지 등록 및 이미지 조회 API")
@RestController
@RequiredArgsConstructor
public class ImageController {
	private final ImageService imageService;

	@Operation(summary = "이미지 등록")
	@Parameters({
		@Parameter(name = "file", description = "이미지 파일(png 또는 jpg 파일로 확장자 제한)", required = true),
		@Parameter(name = "type", description = "프로젝트 이미지일 경우 project, 회원 이미지일 경우 member", required = true)
	})
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "등록 완료", content = @Content(schema = @Schema(defaultValue = "imageName.png"))),
		@ApiResponse(responseCode = "400", description = "등록 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping("/image")
	public ResponseEntity<?> uploadImage(@RequestParam MultipartFile file, @RequestParam String type) {
		if (file != null) {
			try {
				String imagePath = imageService.saveImage(file, type);
				return ResponseEntity.ok(imagePath);
			} catch (IOException e) {
				return ErrorResponse.toResponseEntity(ErrorCode.INVALID_IMAGE_EXTENSION);
			}
		}
		return ErrorResponse.toResponseEntity(ErrorCode.INVALID_INPUT_VALUE);
	}
}
