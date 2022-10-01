package inhatc.capstone.baro.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	// 400 BAD_REQUEST
	INVALID_ID(BAD_REQUEST, "유효하지 않은 ID"),
	// 401 UNAUTHORIZED
	INVALID_TOKEN(UNAUTHORIZED, "유효하지 않은 토큰입니다.");

	private final HttpStatus httpStatus;
	private final String detail;
}
