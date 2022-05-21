package com.mohsin.group.exceptions;

public class InvitationAlreadySendException extends RuntimeException{

    public InvitationAlreadySendException(){
        super("Invitation Already send !");
    }
}
