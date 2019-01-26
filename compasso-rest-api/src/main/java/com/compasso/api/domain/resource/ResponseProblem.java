package com.compasso.api.domain.resource;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * This class wraps the problem json according to https://tools.ietf.org/html/rfc7807
 */
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ResponseProblem {

    private String type;
    private String title;
    private int status;
    private String detail;
    private List<String> errors;

    public static ResponseProblem notFound(){
        ResponseProblem problem = new ResponseProblem();
        problem.detail =  "Could not find the requested resource(s)";
        problem.type = "about:blank";
        problem.status = HttpStatus.NOT_FOUND.value();
        problem.title = "not found";
        return problem;
    }

    public static ResponseProblem notFound(String message){
        ResponseProblem problem = new ResponseProblem();
        problem.detail =  message;
        problem.type = "about:blank";
        problem.status = HttpStatus.NOT_FOUND.value();
        problem.title = "not found";
        return problem;
    }

    public static ResponseProblem badRequest(Errors errors){
        return ResponseProblem.badRequest("you have applied a wrong request, please check the documentation",
                "bad request",
                errors.getAllErrors().parallelStream()
                        .map(error -> error.getDefaultMessage())
                        .collect(toList())
        );
    }

    public static ResponseProblem badRequest(String message){
        ResponseProblem problem = new ResponseProblem();
        problem.detail =  message;
        problem.type = "about:blank";
        problem.status = HttpStatus.BAD_REQUEST.value();
        problem.title = "bad request";
        return problem;
    }

    public static ResponseProblem badRequest(String detail, String title){
        ResponseProblem problem = new ResponseProblem();
        problem.detail =  detail;
        problem.type = "about:blank";
        problem.status = HttpStatus.BAD_REQUEST.value();
        problem.title = title;
        return problem;
    }

    public static ResponseProblem badRequest(String detail, String title, List<String> errors){
        ResponseProblem problem = new ResponseProblem();
        problem.detail =  detail;
        problem.type = "about:blank";
        problem.status = HttpStatus.BAD_REQUEST.value();
        problem.title = title;
        problem.errors = errors;
        return problem;
    }


    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public int getStatus() {
        return status;
    }

    public String getDetail() {
        return detail;
    }

    public List<String> getErrors() {
        return errors;
    }
}
