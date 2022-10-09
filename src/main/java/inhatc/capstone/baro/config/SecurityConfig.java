package inhatc.capstone.baro.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import inhatc.capstone.baro.jwt.JwtAccessDeniedHandler;
import inhatc.capstone.baro.jwt.JwtAuthFilter;
import inhatc.capstone.baro.jwt.JwtAuthenticationEntryPoint;
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
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Bean
	public WebSecurityCustomizer webSecurity() {
		return (web) -> web.ignoring().antMatchers("/resources/**", "/images/**", "/swagger-ui/**", "/v3/api-docs/**");
	}

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
			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.accessDeniedHandler(jwtAccessDeniedHandler)

			.and()
			.headers()
			.frameOptions()
			.sameOrigin()

			.and()
			.authorizeRequests()
			.antMatchers(HttpMethod.OPTIONS).permitAll()
			.antMatchers("/token/**", "/job/**").permitAll()
			.antMatchers(HttpMethod.GET, "/project/**").permitAll()
			//	.antMatchers("/member/**").permitAll()
			.anyRequest().authenticated()

			.and()
			.logout().logoutSuccessUrl("/")

			.and()
			.oauth2Login()
			.successHandler(successHandler)
			.userInfoEndpoint().userService(oAuth2UserService);

		http.addFilterBefore(new JwtAuthFilter(tokenProvider),
			UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
