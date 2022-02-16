package com.casestudy.skilltracker.admin.exception;

import com.casestudy.skilltracker.admin.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ControllerAdvice
@EnableWebMvc
@Slf4j
public class ApplicationExceptionHandler  {

    @ExceptionHandler(value = {ValidationException.class})
    public ResponseEntity<ErrorResponse> validationException(Exception e) {
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage());
        log.error(e.getMessage());
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> unhandedException(Exception e) {
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage());
        log.error(e.getMessage());
        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
