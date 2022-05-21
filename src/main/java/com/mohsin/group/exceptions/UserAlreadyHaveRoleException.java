package com.mohsin.group.exceptions;

import com.mohsin.group.entities.Role;
import com.mohsin.group.entities.User;

public class UserAlreadyHaveRoleException extends RuntimeException{

    public UserAlreadyHaveRoleException(User user , Role role){
        super("User with username " +user.getUsername()+ " Already have the role "+ role.getName());
    }
}
