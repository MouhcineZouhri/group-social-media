package com.mohsin.group.exceptions;

public class AuthorityNotFoundException extends RuntimeException{

    public AuthorityNotFoundException(String name){
        super("Authority with name " + name + " not Found !");
    }

    public AuthorityNotFoundException(Long id){
        super("Authority with id " + id + " not Found !");
    }
}
