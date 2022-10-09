package inhatc.capstone.baro.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	// 400 BAD_REQUEST
	INVALID_ID(BAD_REQUEST, "유효하지 않은 ID입니다."),
	INVALID_REFRESH_TOKEN(BAD_REQUEST, "유효하지 않은 Refresh Token입니다."),
	INVALID_INPUT_VALUE(BAD_REQUEST, "입력 양식과 맞지않는 입력값입니다."),
	EXIST_MEMBER(BAD_REQUEST, "이미 가입된 유저입니다."),
	INVALID_IMAGE_EXTENSION(BAD_REQUEST, "이미지는 png 또는 jpg 확장자만 업로드 할 수 있습니다."),
	NOT_FOUND_IMAGE(BAD_REQUEST, "이미지를 찾을 수 없습니다."),

	// 401 UNAUTHORIZED
	INVALID_TOKEN(UNAUTHORIZED, "유효하지 않은 토큰입니다."),

	//403 FORBIDDEN
	NOT_JOINED(FORBIDDEN, "가입하지 않은 회원입니다."),

	//404 NOT_FOUND
	NOT_FOUND_PROJECT(NOT_FOUND, "프로젝트를 찾을 수 없습니다.");

	private final HttpStatus httpStatus;
	private final String detail;
}
