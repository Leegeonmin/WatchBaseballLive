package com.bongbong.watchbaseball.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    OPENAPI_SERVER_ERROR("서버 에러입니다 문의주세요", HttpStatus.INTERNAL_SERVER_ERROR),
    INTERNAL_SERVER_ERROR("서버 에러입니다 문의주세요", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST("유효하지않은 요청입니다", HttpStatus.BAD_REQUEST),
    TEAM_NOT_FOUND("팀을 찾지 못했습니다",HttpStatus.NOT_FOUND),
    WEATHER_DATE_ERROR("유효한 날짜를 찾을 수 없습니다", HttpStatus.INTERNAL_SERVER_ERROR)

    ;
    private final String description;
    private final HttpStatus httpStatus;

    ErrorCode(String description, HttpStatus httpStatus) {
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
