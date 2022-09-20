package inhatc.capstone.baro.oauth2;

import inhatc.capstone.baro.member.Member;
import lombok.Getter;

/**
 * OAuth2 로그인 직후 JWT 토큰 발급에 사용되는 유저 정보
 */
@Getter
public class CustomOAuth2User {
	private Member member;
	private boolean first;


}

