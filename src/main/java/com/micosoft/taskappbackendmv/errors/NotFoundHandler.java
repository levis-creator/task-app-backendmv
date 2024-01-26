package com.micosoft.taskappbackendmv.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
@ControllerAdvice
public class NotFoundHandler {
    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleApiRequestException(NotFoundException exception){
//        1.Payload containing exception details
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                exception.getMessage(),
                status,
                ZonedDateTime.now(ZoneId.of("Z"))

        );
//        2.Return response entity
        return new ResponseEntity<>(apiException, status);
    }
}
