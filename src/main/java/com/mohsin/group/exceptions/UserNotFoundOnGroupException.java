package com.mohsin.group.exceptions;

import com.mohsin.group.entities.keys.UserGroupKey;

public class UserNotFoundOnGroupException extends RuntimeException{

    public UserNotFoundOnGroupException(UserGroupKey key){
        super("User with id " + key.getUserId() + " not Found In group " + key.getGroupId());
    }

}
