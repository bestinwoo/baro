package inhatc.capstone.baro.oauth2;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import inhatc.capstone.baro.member.Member;
import inhatc.capstone.baro.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	// Spring Security OAuth2 에서 기본으로 제공하는 OAuth2UserService 를 사용하기 위함
	private final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
	private final MemberRepository memberRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		// OAuth2 서비스 제공자 구분
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		OAuth2Provider provider = OAuth2Provider.valueOf(registrationId.toUpperCase(Locale.ROOT));

		// OAuth2 계정 정보 가져오기
		OAuth2User oauth2User = delegate.loadUser(userRequest);
		Map<String, Object> attributes = oauth2User.getAttributes();
		OAuth2UserInfo userInfo = OAuth2UserInfoFactory.createUserInfo(provider, attributes);

		Optional<Member> byEmailAndProvider = memberRepository.findByEmailAndProvider(userInfo.getEmail(),
			userInfo.getOAuth2Provider());

		boolean first; // 최초 로그인 여부
		Member member;
		if (byEmailAndProvider.isEmpty()) {
			// 신규 회원인 경우
			member = Member.createMember(userInfo.getOAuth2Id(), userInfo.getEmail(), userInfo.getOAuth2Provider());
			memberRepository.save(member);
			first = true;
		} else {
			// 기존 회원인 경우
			member = byEmailAndProvider.get();
			first = false;
		}
		return new CustomOAuth2User(userInfo, member, first);
	}
}
