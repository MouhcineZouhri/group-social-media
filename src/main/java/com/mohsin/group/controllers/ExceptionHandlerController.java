package com.mohsin.group.controllers;

import com.mohsin.group.exceptions.NotAllowedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerController {


    @ExceptionHandler(NotAllowedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String , String> notAllowed(Exception e){

        Map<String , String> response = new HashMap<>();

        response.put("message" , e.getMessage());

        return response;
    }



    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String , String> exception(Exception e){

        Map<String , String> response = new HashMap<>();

        response.put("message" , e.getMessage());

        return response;
    }

}
