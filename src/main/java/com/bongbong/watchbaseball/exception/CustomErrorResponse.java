package com.bongbong.watchbaseball.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CustomErrorResponse {
    private final ErrorCode errorCode;
    private final String message;
    private String fieldError;
}
