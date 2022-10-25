package inhatc.capstone.baro.lounge;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class LoungeDto {
	@Getter
	@Setter
	public static class Request {
		@NotEmpty(message = "내용을 입력해주세요.")
		private String content;
		@NotNull(message = "멤버 ID를 입력해주세요.")
		private Long memberId;
	}

	@Getter
	@Setter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response {
		private Long id;
		private Long memberId;
		private String memberNickname;
		private String memberProfileUrl;
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		private LocalDateTime createDate;
		private String content;

		public static Response from(Lounge lounge) {
			Response response = Response.builder()
				.memberId(lounge.getMember().getId())
				.memberNickname(lounge.getMember().getNickname())
				.content(lounge.getContent())
				.createDate(lounge.getCreateDate())
				.id(lounge.getId())
				.build();

			if (lounge.getMember().getUserProfileImage() != null) {
				response.memberProfileUrl = lounge.getMember().getUserProfileImage().getImagePath();
			}
			return response;
		}
	}
}
