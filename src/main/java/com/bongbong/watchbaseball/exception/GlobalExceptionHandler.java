package com.bongbong.watchbaseball.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomErrorResponse> handleCustomException(final CustomException e) {
        log.error("CustomException occurred : {}", e.getErrorCode().getDescription());

        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(
                CustomErrorResponse.builder()
                        .errorCode(e.getErrorCode())
                        .message(e.getErrorCode().getDescription())
                        .build()
        );
    }

    //요청 데이터 오류
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException is occurred", e);

        BindingResult bindingResult = e.getBindingResult();
        FieldError error = bindingResult.getFieldError();
        assert error != null;
        String fieldError = String.format("Field: %s, Error: %s", error.getField(), error.getDefaultMessage());

        return ResponseEntity.status(ErrorCode.BAD_REQUEST.getHttpStatus()).body(
                CustomErrorResponse.builder()
                        .errorCode(ErrorCode.BAD_REQUEST)
                        .message(ErrorCode.BAD_REQUEST.getDescription())
                        .fieldError(fieldError)
                        .build()
        );
    }

    //자주 발생되는 오류
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("DataIntegrityViolationException occurred", e);
        return ResponseEntity.status(ErrorCode.BAD_REQUEST.getHttpStatus()).body(
                CustomErrorResponse.builder()
                        .errorCode(ErrorCode.BAD_REQUEST)
                        .message(ErrorCode.BAD_REQUEST.getDescription())
                        .build()
        );
    }

    @ResponseStatus
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleException(Exception e) {
        log.error("Exception is occurred", e);
        return ResponseEntity.status(ErrorCode.BAD_REQUEST.getHttpStatus()).body(
                CustomErrorResponse.builder()
                        .errorCode(ErrorCode.BAD_REQUEST)
                        .message(ErrorCode.BAD_REQUEST.getDescription())
                        .build()
        );
    }
}
