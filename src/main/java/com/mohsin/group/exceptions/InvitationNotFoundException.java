package com.mohsin.group.exceptions;

public class InvitationNotFoundException extends RuntimeException{

    public InvitationNotFoundException(){
        super("Invitation Not Found");
    }

}
