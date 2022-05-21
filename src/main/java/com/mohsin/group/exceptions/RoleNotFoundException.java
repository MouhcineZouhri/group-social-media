package com.mohsin.group.exceptions;

public class RoleNotFoundException extends RuntimeException{

    public RoleNotFoundException(String name){
        super("Role with name " + name + " not Found !");
    }

    public RoleNotFoundException(Long id){
        super("Role with id " + id + " not Found !");
    }
}
