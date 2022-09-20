package inhatc.capstone.baro.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import inhatc.capstone.baro.jwt.JwtAuthFilter;
import inhatc.capstone.baro.jwt.TokenProvider;
import inhatc.capstone.baro.oauth2.CustomOAuth2UserService;
import inhatc.capstone.baro.oauth2.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {
	private final CustomOAuth2UserService oAuth2UserService;
	private final OAuth2SuccessHandler successHandler;
	private final TokenProvider tokenProvider;
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors().configurationSource(request -> {
				CorsConfiguration cors = new CorsConfiguration();
				cors.setAllowedOrigins(List.of("*"));
				cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
				cors.setAllowedHeaders(List.of("*"));
				return cors;
			});
		http.httpBasic().disable()
			.csrf().disable()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

			.and()
			.authorizeRequests()
			.antMatchers(HttpMethod.OPTIONS, "/**/*").permitAll()
			.antMatchers("/token/**").permitAll()
			.anyRequest().authenticated()

			.and()
			.addFilterBefore(new JwtAuthFilter(tokenProvider),
				OAuth2LoginAuthenticationFilter.class)
			.oauth2Login()
			.successHandler(successHandler)
			.userInfoEndpoint().userService(oAuth2UserService);


		return http.build();
	}
}
