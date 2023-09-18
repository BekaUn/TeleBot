package com.project.wecare.configuration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerResource {
 @ExceptionHandler
    public ResponseEntity<ResponseDto<Void>> nullPointerException(NullPointerException e) {
        List<ErrorDto> errors = new ArrayList<>();
        errors.add(new ErrorDto("rejection", e.getMessage()));
        return ResponseEntity.badRequest().body(
                ResponseDto.<Void>builder()
                        .message("Text not found!")
                        .code(-2)
                        .errors(errors)
                        .build()
        );
    }
    @ExceptionHandler
    public ResponseEntity<ResponseDto<Void>> runtimeException(RuntimeException e) {
        List<ErrorDto> errors = new ArrayList<>();
        errors.add(new ErrorDto("rejection", e.getMessage()));
        return ResponseEntity.badRequest().body(
                ResponseDto.<Void>builder()
                        .message("Text not found!")
                        .code(-2)
                        .errors(errors)
                        .build()
        );
    }
}