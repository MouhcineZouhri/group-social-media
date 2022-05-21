package com.mohsin.group.exceptions;

import com.mohsin.group.entities.Authority;
import com.mohsin.group.entities.Role;

public class RoleAlreadyHaveAuthorityException extends RuntimeException {

    public RoleAlreadyHaveAuthorityException(Role r , Authority authority){
        super("Role " + r.getName() + " Already have authority " + authority.getName());
    }
}
