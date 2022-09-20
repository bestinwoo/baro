package inhatc.capstone.baro.oauth2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import inhatc.capstone.baro.jwt.TokenDto;
import inhatc.capstone.baro.jwt.TokenProvider;
import inhatc.capstone.baro.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private final TokenProvider tokenProvider;
	private final ObjectMapper objectMapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
		throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(authentication);

		CustomOAuth2User oAuth2User = (CustomOAuth2User)authentication.getPrincipal();
		Member loggedMember = oAuth2User.getMember();
		boolean firstMember = oAuth2User.isFirst();

		log.info("Principal에서 꺼낸 OAuth2User = {}", oAuth2User);
		// 최초 로그인이라면 회원가입 처리를 한다.
		String redirectUrl;
		log.info("토큰 발행 시작");

		TokenDto token = tokenProvider.generateTokenDto(createUserPayload(loggedMember));
		log.info("{}", token);
		redirectUrl = UriComponentsBuilder.fromUriString("/home")
			.queryParam("token", "token")
			.build().toUriString();
		getRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}

	public Map<String, Object> createUserPayload(Member member) {
		Map<String, Object> payload = new HashMap<>();
		payload.put("sub", member.getId());
		payload.put("email", member.getEmail());
		payload.put("role", "ROLE_USER");
		return payload;
	}
}
