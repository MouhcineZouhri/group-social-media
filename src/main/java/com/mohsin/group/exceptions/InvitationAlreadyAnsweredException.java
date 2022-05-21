package com.mohsin.group.exceptions;

public class InvitationAlreadyAnsweredException extends RuntimeException{

    public InvitationAlreadyAnsweredException(){
     super("Invitation Already Send");
    }

}
