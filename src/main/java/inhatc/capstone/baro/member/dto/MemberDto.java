package inhatc.capstone.baro.member.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

public class MemberDto {
	@Getter
	@Setter
	public static class Register {
		private Long id;
		@NotBlank(message = "닉네임을 입력해주세요")
		@Size(message = "닉네임은 4글자 이상, 10글자 이하입니다.", min = 4, max = 10)
		private String nickname;
		@NotNull(message = "직무를 선택해주세요")
		private Long jobId;
		@NotBlank(message = "직무 레벨을 입력해주세요.")
		private String jobLevel;
		@NotBlank(message = "대학교 이름을 입력해주세요.")
		private String university;
	}
}
