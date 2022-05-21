package com.mohsin.group.exceptions;

public class JoinRequestNotFoundException extends RuntimeException{

    public JoinRequestNotFoundException(){
        super("Join Request Not Found");
    }

}
