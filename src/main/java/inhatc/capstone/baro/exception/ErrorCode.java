package inhatc.capstone.baro.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	// 400 BAD_REQUEST
	REQUIRED_TOKEN(HttpStatus.BAD_REQUEST, "ID Token은 필수값입니다."),
	// 401 UNAUTHORIZED
	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.");

	private final HttpStatus httpStatus;
	private final String detail;
}
