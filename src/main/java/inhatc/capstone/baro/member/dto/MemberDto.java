package inhatc.capstone.baro.member.dto;

import lombok.Getter;
import lombok.Setter;

public class MemberDto {
	@Getter
	@Setter
	public static class Register {
		private Long id;
		private String nickname;
		private Long jobId;
		private String jobLevel;
		private String university;
	}
}
