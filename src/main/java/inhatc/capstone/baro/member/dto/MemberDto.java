package inhatc.capstone.baro.member.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import inhatc.capstone.baro.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Info {
		private Long id;
		private String nickname;
		private String imageUrl;
		private String email;
		private Long jobId;
		private Long point;
		private String jobParentName;
		private String jobChildName;
		private String jobLevel;
		private String school;
		private String introduce;

		public static Info from(Member member) {
			Info info = Info.builder()
				.id(member.getId())
				.nickname(member.getNickname())
				.email(member.getEmail())
				.jobId(member.getJob().getId())
				.jobParentName(member.getJob().getParent().getName())
				.jobChildName(member.getJob().getName())
				.jobLevel(member.getJobLevel())
				.school(member.getUniversity())
				.point(member.getPoint())
				.introduce(member.getIntroduce())
				.build();
			if (member.getUserProfileImage() != null) {
				info.imageUrl = member.getUserProfileImage().getImagePath();
			}
			return info;
		}
	}

	@Getter
	@Setter
	public static class Modify {
		private String imageUrl;
		private Long jobId;
		private String jobLevel;
		private String introduce;
		private String nickname;
	}
}
