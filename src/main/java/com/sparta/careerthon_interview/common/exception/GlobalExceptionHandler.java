package com.sparta.careerthon_interview.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.sparta.careerthon_interview.common.dto.ApiResponse;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CareerthonException.class)
    public ResponseEntity<ApiResponse<?>> handlerGlobalException(CareerthonException e) {
        return new ResponseEntity<>(ApiResponse.error(e.getMessage()), e.getStatus());
    }
}
