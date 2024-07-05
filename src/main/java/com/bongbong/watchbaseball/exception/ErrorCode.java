package com.bongbong.watchbaseball.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    BAD_REQUEST("유효하지않은 요청입니다", HttpStatus.BAD_REQUEST),
    TEAM_NOT_FOUND("팀을 찾지 못했습니다",HttpStatus.NOT_FOUND);


    private final String description;
    private final HttpStatus httpStatus;

    ErrorCode(String description, HttpStatus httpStatus) {
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
