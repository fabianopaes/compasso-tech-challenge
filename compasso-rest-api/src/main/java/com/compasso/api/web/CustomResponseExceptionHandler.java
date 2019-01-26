package com.compasso.api.web;

import com.compasso.api.domain.resource.ResponseProblem;
import com.compasso.api.exception.ResourceNotFoundException;
import com.compasso.api.exception.RulesNotSatisfiedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RulesNotSatisfiedException.class)
    public final ResponseEntity<ResponseProblem> handleRulesNotSatisfiedException(RulesNotSatisfiedException ex, WebRequest request) {
        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8)
                .body(ResponseProblem.badRequest(ex.getMessage()));
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ResponseProblem> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE);
        return new ResponseEntity<>(ResponseProblem.notFound(), headers,
                HttpStatus.NOT_FOUND);
    }
}
