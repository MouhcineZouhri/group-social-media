package com.mohsin.group.exceptions;

public class GlobalRoleNameForbiddenException extends RuntimeException{

    public GlobalRoleNameForbiddenException(String name){
        super("Role name should not be " + name);
    }
}
