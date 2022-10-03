package inhatc.capstone.baro.member.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class MemberDto {
	@Getter
	@Setter
	public static class Register {
		@Schema(description = "회원 ID")
		private Long id;
		@NotBlank(message = "닉네임을 입력해주세요")
		@Size(message = "닉네임은 4글자 이상, 10글자 이하입니다.", min = 4, max = 10)
		@Schema(description = "닉네임", defaultValue = "인우최고")
		private String nickname;

		@NotNull(message = "직무를 선택해주세요")
		@Schema(description = "직무 ID")
		private Long jobId;

		@NotBlank(message = "직무 레벨을 입력해주세요.")
		@Schema(description = "직무 레벨", defaultValue = "고수")
		private String jobLevel;

		@NotBlank(message = "대학교 이름을 입력해주세요.")
		@Schema(description = "대학교 이름", defaultValue = "인하공업전문대학")
		private String university;
	}
}
