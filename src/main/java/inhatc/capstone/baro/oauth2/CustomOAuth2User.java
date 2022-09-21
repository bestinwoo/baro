package inhatc.capstone.baro.oauth2;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import inhatc.capstone.baro.member.Member;
import lombok.Getter;

/**
 * OAuth2 로그인 직후 JWT 토큰 발급에 사용되는 유저 정보
 */
@Getter
public class CustomOAuth2User extends DefaultOAuth2User {
	private Member member;
	private boolean first;

	public CustomOAuth2User(OAuth2UserInfo oAuth2UserInfo, Member member, boolean first) {
		super(List.of(new SimpleGrantedAuthority("ROLE_USER")), oAuth2UserInfo.getAttributes(), member.getProvider().getAttributeKey());
		this.member = member;
		this.first = first;
	}

	// 시큐리티 컨텍스트 내의 인증 정보를 가져와 하는 작업을 수행할 경우 계정 식별자가 사용되도록 조치
	@Override
	public String getName() {
		return String.valueOf(member.getId());
	}
}

