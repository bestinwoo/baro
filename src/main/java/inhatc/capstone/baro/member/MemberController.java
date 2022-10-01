package inhatc.capstone.baro.member;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import inhatc.capstone.baro.exception.ErrorResponse;
import inhatc.capstone.baro.member.dto.MemberDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "member", description = "회원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
	private final MemberService memberService;

	@Operation(summary = "회원가입")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "회원가입 성공"),
		@ApiResponse(responseCode = "400", description = "해당 ID에 해당하는 멤버 정보 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody MemberDto.Register register) {
		memberService.join(register);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
