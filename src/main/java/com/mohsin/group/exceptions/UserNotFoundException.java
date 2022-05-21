package com.mohsin.group.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String username){
        super("user with username " + username + " not Found !");
    }

    public UserNotFoundException(Long id){
        super("user with id " + id + " not Found !");
    }
}
