package ru.home.vehicle.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.home.vehicle.dto.ApiError;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = UnsupportedOperationException.class)
    protected ResponseEntity<Object> handleConflict(UnsupportedOperationException ex, WebRequest request) {
        var responseBody = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), "");
        return handleExceptionInternal(
                ex, responseBody,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request
        );
    }
}
