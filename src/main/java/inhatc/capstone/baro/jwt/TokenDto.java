package inhatc.capstone.baro.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class TokenDto {
	@Getter
	@Setter
	@Builder
	public static class Response {
		private String grantType;
		private String accessToken;
		private Long accessTokenExpiresIn;
		private Long refreshTokenExpiresIn;
		private String refreshToken;
	}

	@Getter
	@Setter
	public static class Reissue {
		private String accessToken;
		private String refreshToken;
	}

}
