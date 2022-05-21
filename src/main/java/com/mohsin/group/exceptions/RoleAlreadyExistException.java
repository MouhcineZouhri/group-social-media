package com.mohsin.group.exceptions;

public class RoleAlreadyExistException extends RuntimeException{

    public RoleAlreadyExistException(String name){
        super("Role with name " + name + " Already Exist ! ");
    }
}
