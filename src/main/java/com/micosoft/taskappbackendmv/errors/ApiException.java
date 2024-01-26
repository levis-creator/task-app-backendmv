package com.micosoft.taskappbackendmv.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ApiException {
    private  String message;
    private Throwable throwable;
    private HttpStatus httpStatus;
    private ZonedDateTime timestamp;


}
