package com.mohsin.group.exceptions;

import com.mohsin.group.entities.Authority;
import com.mohsin.group.entities.Role;

public class RoleNotContainAuthorityException extends RuntimeException{

    public RoleNotContainAuthorityException(Role r , Authority authority){
        super("Role " + r.getName() + " does not contain  authority " + authority.getName());
    }
}
