package inhatc.capstone.baro.jwt;

import static inhatc.capstone.baro.exception.ErrorCode.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import inhatc.capstone.baro.exception.CustomException;
import inhatc.capstone.baro.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "토큰 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {
	private final RedisTemplate<String, Object> redisTemplate;
	private final TokenProvider tokenProvider;

	@Operation(summary = "토큰 재발급", description = "Access Token 만료시 재발급하는 API")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "재발급 완료", content = @Content(schema = @Schema(implementation = TokenDto.Response.class))),
		@ApiResponse(responseCode = "400", description = "유효하지 않은 Token", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping("/reissue")
	public ResponseEntity<TokenDto.Response> reissue(@RequestBody TokenDto.Reissue token) {
		if (!tokenProvider.validateToken(token.getRefreshToken())) {
			throw new CustomException(INVALID_REFRESH_TOKEN);
		}

		Authentication authentication = tokenProvider.getAuthentication(token.getAccessToken());
		//Redis에 저장된 Refresh Token 꺼내오기
		String key = "RefreshToken:" + authentication.getName();
		String refreshToken = (String)redisTemplate.opsForValue().get(key);

		if (refreshToken == null || !refreshToken.equals(token.getRefreshToken())) {
			throw new CustomException(INVALID_REFRESH_TOKEN);
		}

		TokenDto.Response tokenDto = tokenProvider.generateTokenDto(tokenProvider.parseClaims(token.getAccessToken()));

		redisTemplate.opsForValue()
			.set(key, tokenDto.getRefreshToken(), tokenDto.getRefreshTokenExpiresIn() - new Date().getTime(),
				TimeUnit.MILLISECONDS);
		return ResponseEntity.ok(tokenDto);
	}
}
