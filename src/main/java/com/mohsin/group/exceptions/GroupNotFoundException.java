package com.mohsin.group.exceptions;

public class GroupNotFoundException extends RuntimeException{

    public GroupNotFoundException(String name){
        super("Group with name " + name + " not Found !");
    }

    public GroupNotFoundException(Long id){
        super("Group with id " + id + " not Found !");
    }
}
