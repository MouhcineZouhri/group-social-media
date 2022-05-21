package com.mohsin.group.services;

import com.mohsin.group.dtos.group.AuthorityRole;
import com.mohsin.group.dtos.role.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {

    Page<RoleResponse> getRoles(Long groupId , Pageable pageable);

    RoleResponse getRole(GetRoleRequest request);

    RoleResponse createRole(CreateRoleRequest  request);

    RoleResponse updateRole(UpdateRoleRequest request);

    void deleteRole(DeleteRoleRequest request);

    RoleResponse attachAuthority(AuthorityRole request);

    RoleResponse detachAuthority(AuthorityRole request);
}
