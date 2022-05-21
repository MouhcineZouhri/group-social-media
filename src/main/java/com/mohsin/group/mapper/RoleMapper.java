package com.mohsin.group.mapper;

import com.mohsin.group.dtos.role.RoleResponse;
import com.mohsin.group.entities.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponse toRoleResponse(Role role);
}
