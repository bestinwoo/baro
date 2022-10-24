package inhatc.capstone.baro.member;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import inhatc.capstone.baro.exception.ErrorResponse;
import inhatc.capstone.baro.member.dto.MemberDto;
import inhatc.capstone.baro.project.dto.ProjectDto;
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
	public ResponseEntity<?> signup(@Validated @RequestBody MemberDto.Register register) {
		memberService.join(register);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@Operation(summary = "회원정보 조회")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "조회 성공"),
		@ApiResponse(responseCode = "404", description = "해당 ID에 해당하는 멤버 정보 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/{memberId}")
	public ResponseEntity<MemberDto.Info> getMemberInfo(@PathVariable Long memberId) {
		return ResponseEntity.ok(memberService.getMemberInfo(memberId));
	}

	@Operation(summary = "회원정보 수정")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "수정 완료"),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 멤버 ID", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "403", description = "자신 외의 다른 멤버 수정 시도", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PatchMapping("/{memberId}")
	public ResponseEntity<MemberDto.Info> modifyMemberInfo(@PathVariable Long memberId,
		@RequestBody MemberDto.Modify modify) {
		MemberDto.Info info = memberService.modifyMemberInfo(memberId, modify);
		return ResponseEntity.ok(info);
	}

	@Operation(summary = "프로젝트 현황")
	@GetMapping("/{memberId}/project")
	public ResponseEntity<ProjectDto.MyPage> getProjectStatus(@PathVariable Long memberId) {
		ProjectDto.MyPage myProject = memberService.getMyProject(memberId);
		return ResponseEntity.ok(myProject);
	}

}
