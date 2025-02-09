package com.bongbong.watchbaseball.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR("서버 에러입니다 문의주세요", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST("유효하지않은 요청입니다", HttpStatus.BAD_REQUEST),
    TEAM_NOT_FOUND("팀을 찾지 못했습니다",HttpStatus.NOT_FOUND);


    private final String description;
    private final HttpStatus httpStatus;

    ErrorCode(String description, HttpStatus httpStatus) {
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
