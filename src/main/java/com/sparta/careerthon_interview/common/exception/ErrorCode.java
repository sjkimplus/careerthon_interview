package com.sparta.careerthon_interview.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 서버에러
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부서버 에러"),
    TOKEN_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "토큰을 찾지 못함"),
    // format error
    WRONG_FORMAT(HttpStatus.BAD_REQUEST, "비밀번호가 올바른 양식이 아닙니다"),
    MISSING_FORMAT(HttpStatus.BAD_REQUEST, "모든 양식 값을 채워주세요"),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저 입니다."),
    USER_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저 타입 입니다."),
    USER_ID_DUPLICATION(HttpStatus.BAD_REQUEST, "중복되는 아이디 입니다."),
    USER_PW_ERROR(HttpStatus.BAD_REQUEST, "비밀 번호가 아이디와 일치하지 않습니다."),

    // JWT
    JWT_UNSAVABLE(HttpStatus.BAD_REQUEST, "JWT 토큰을 쿠키에 저장하는데 실패 했습니다."),
    JWT_INVALID(HttpStatus.BAD_REQUEST, "유효하지 않는 JWT 토큰 입니다"),
    JWT_EXPIRED(HttpStatus.BAD_REQUEST, "만료된 토큰 입니다."),
    JWT_TYPE_ERROR(HttpStatus.BAD_REQUEST, "지원되지 않는 JWT 토큰 입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}