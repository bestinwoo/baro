package inhatc.capstone.baro.oauth2;

import java.util.Map;

public class OAuth2UserInfoFactory {
	/**
	 * 적절한 OAuth2UserInfo 를 생성해서 반환한다.
	 * @param provider OAuth2 서비스 정보, registrationId 값으로 추출한 enum
	 * @param attributes OAuth2 계정 정보
	 * @return OAuth2UserInfo 구현체, provider 에 맞는 구현체가 제공된다.
	 */
	public static OAuth2UserInfo createUserInfo(OAuth2Provider provider, Map<String, Object> attributes) {
		OAuth2UserInfo userInfo = null;
		switch (provider) {
			case KAKAO:
				userInfo = new KakaoUser(attributes);
				break;
			case NAVER:
				userInfo = new NaverUser(attributes);
				break;
			case GOOGLE:
				userInfo = new GoogleUser(attributes);
				break;
		}

		return userInfo;
	}
}
