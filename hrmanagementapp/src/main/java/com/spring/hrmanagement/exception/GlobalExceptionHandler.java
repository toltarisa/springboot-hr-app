package com.spring.hrmanagement.exception;

import com.spring.hrmanagement.exception.payload.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiException(ApiRequestException exception) {

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                exception.getMessage(),
                exception,
                badRequest,
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = {DbOperationException.class})
    public ResponseEntity<Object> handleDbOperationException(DbOperationException exception) {

        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;

        DbException dbException = new DbException(
                exception.getMessage(),
                exception,
                internalServerError,
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(dbException, internalServerError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Object> handleUserMethodFieldErrors(MethodArgumentNotValidException ex) {

        final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        final List<CustomFieldError> customFieldErrors = new ArrayList<>();

        for (FieldError fieldError : fieldErrors) {

            final String field = fieldError.getField();

            final String message = fieldError.getDefaultMessage();

            final CustomFieldError customFieldError = CustomFieldError.builder().field(field).message(message).build();

            customFieldErrors.add(customFieldError);

        }

        if(customFieldErrors.size() == 1)
            return ResponseEntity.badRequest().body(customFieldErrors.iterator().next());

        return ResponseEntity.badRequest().body(customFieldErrors);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex,
                                                                              WebRequest request) {
        NotFoundError errorDetails = new NotFoundError(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    public final ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;

        AuthenticationExceptionPayload exception = new AuthenticationExceptionPayload(
                ex.getMessage(),
                internalServerError,
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(exception, internalServerError);
    }
}
