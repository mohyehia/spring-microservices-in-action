package com.moh.yehia.organization.service.advice;

import com.moh.yehia.organization.service.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e, ServerHttpRequest serverHttpRequest) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", e.getMessage());
        errorMap.put("path", serverHttpRequest.getPath().toString());
        errorMap.put("timestamp", LocalDateTime.now().toString());
        return new ResponseEntity<>(errorMap, HttpStatus.NOT_FOUND);
    }
}
