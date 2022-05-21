package com.mohsin.group.exceptions;

public class PostNotFoundException extends RuntimeException{

    public PostNotFoundException(Long id){
        super("Post with id " + id + " Not Found !");
    }
}
