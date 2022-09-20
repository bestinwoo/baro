package inhatc.capstone.baro.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TokenDto {
	private String grantType;
	private String accessToken;
	private Long accessTokenExpiresIn;
	private Long refreshTokenExpiresIn;
	private String refreshToken;
}
