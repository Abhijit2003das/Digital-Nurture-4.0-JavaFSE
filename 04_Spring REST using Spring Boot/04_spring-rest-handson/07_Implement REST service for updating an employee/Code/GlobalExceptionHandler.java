// src/main/java/com/example/countryservice/GlobalExceptionHandler.java
package com.example.countryservice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.springframework.web.context.request.ServletWebRequest; // <--- ADD THIS IMPORT!

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        LOGGER.info("Start: handleMethodArgumentNotValid method invoked.");

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                                .map(x -> x.getDefaultMessage())
                                .collect(Collectors.toList());

        body.put("errors", errors);

        LOGGER.info("End: handleMethodArgumentNotValid method completed. Errors: {}", errors);
        return new ResponseEntity<>(body, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        LOGGER.info("Start: handleHttpMessageNotReadable method invoked.");
        LOGGER.warn("HttpMessageNotReadableException caught: {}", ex.getMessage());

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("error", "Bad Request");

        List<String> errors = new ArrayList<>();

        if (ex.getCause() instanceof InvalidFormatException) {
            final InvalidFormatException cause = (InvalidFormatException) ex.getCause();
            if (cause.getPath() != null && !cause.getPath().isEmpty()) {
                String fieldName = cause.getPath().get(0).getFieldName();
                String expectedType = cause.getTargetType() != null ? cause.getTargetType().getSimpleName() : "unknown type";
                String message = "Incorrect format for field '" + fieldName + "'. Expected "
                                 + expectedType + ", but received '" + cause.getValue() + "'.";
                errors.add(message);
                body.put("message", message);
            } else {
                errors.add("Malformed JSON or incorrect data format.");
                body.put("message", "Malformed JSON or incorrect data format.");
            }
        } else {
            errors.add("Malformed JSON request body.");
            body.put("message", "Malformed JSON request body.");
        }

        body.put("errors", errors);

        LOGGER.info("End: handleHttpMessageNotReadable method completed. Response Body: {}", body);
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<Object> handleEmployeeNotFoundException(EmployeeNotFoundException ex, WebRequest request) {
        LOGGER.info("Start: handleEmployeeNotFoundException method invoked.");
        LOGGER.warn("EmployeeNotFoundException caught: {}", ex.getMessage());

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());
        body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

        LOGGER.info("End: handleEmployeeNotFoundException method completed. Response Body: {}", body);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}