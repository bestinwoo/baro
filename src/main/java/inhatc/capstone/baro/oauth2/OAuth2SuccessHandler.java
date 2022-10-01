package inhatc.capstone.baro.oauth2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import inhatc.capstone.baro.jwt.TokenDto;
import inhatc.capstone.baro.jwt.TokenProvider;
import inhatc.capstone.baro.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
	private final TokenProvider tokenProvider;
	@Value("${app.oauth2.authorized-redirect-uri}")
	private String redirectUrl;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication)
		throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(authentication);

		CustomOAuth2User oAuth2User = (CustomOAuth2User)authentication.getPrincipal();
		Member loggedMember = oAuth2User.getMember();
		boolean firstMember = loggedMember.isFirst();

		log.info("Principal에서 꺼낸 OAuth2User = {}", oAuth2User);
		log.info("토큰 발행 시작");

		TokenDto token = tokenProvider.generateTokenDto(createUserPayload(loggedMember));
		log.info("{}", token);
		String redirectUri = UriComponentsBuilder.fromUriString(redirectUrl)
			.queryParam("accessToken", token.getAccessToken())
			.queryParam("refreshToken", token.getRefreshToken())
			.queryParam("isFirst", firstMember)
			.toUriString();

		response.sendRedirect(redirectUri);
	}

	public Map<String, Object> createUserPayload(Member member) {
		Map<String, Object> payload = new HashMap<>();
		payload.put("sub", member.getId());
		payload.put("email", member.getEmail());
		if (member.getNickname() != null) {
			payload.put("nickname", member.getNickname());
		}
		payload.put("role", "ROLE_USER");
		return payload;
	}
}
