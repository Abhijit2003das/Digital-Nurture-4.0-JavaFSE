// src/main/java/com/example/countryservice/GlobalExceptionHandler.java
package com.example.countryservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpStatus; // REMOVE THIS IMPORT
import org.springframework.http.HttpStatusCode; // NEW IMPORT: Use HttpStatusCode
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
                                                                  // FIX: Change HttpStatus to HttpStatusCode
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        LOGGER.info("Start: handleMethodArgumentNotValid method invoked.");

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value()); // HttpStatusCode also has a .value() method

        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                                .map(x -> x.getDefaultMessage())
                                .collect(Collectors.toList());

        body.put("errors", errors);

        LOGGER.info("End: handleMethodArgumentNotValid method completed. Errors: {}", errors);
        return new ResponseEntity<>(body, headers, status);
    }
}