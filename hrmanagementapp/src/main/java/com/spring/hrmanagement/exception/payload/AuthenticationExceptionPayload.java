package com.spring.hrmanagement.exception.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
@Getter
@AllArgsConstructor
public class AuthenticationExceptionPayload {

    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;
}
