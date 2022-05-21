package com.mohsin.group.controllers;

import com.mohsin.group.dtos.group.AuthorityRole;
import com.mohsin.group.dtos.role.*;
import com.mohsin.group.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/groups/{groupId}/roles")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    @PreAuthorize("@groupPermission.isHasPermission(#groupId , 'MANAGE_ROLE')")
    public Page<RoleResponse> getRoles(
            @PathVariable Long groupId,
            @RequestParam(value = "page"  , defaultValue = "0") int page,
            @RequestParam(value = "size" , defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page , size);
        return roleService.getRoles(groupId , pageable);
    }

    @GetMapping("/{roleName}")
    @PreAuthorize("@groupPermission.isHasPermission(#groupId , 'MANAGE_ROLE')")
    public RoleResponse getRole(
            @PathVariable Long groupId,
            @PathVariable String roleName
    ){
        GetRoleRequest getRoleRequest = GetRoleRequest.builder()
                .groupId(groupId)
                .roleName(roleName)
                .build();

        return roleService.getRole(getRoleRequest);
    }

    @PostMapping
    @PreAuthorize("@groupPermission.isHasPermission(#groupId , 'MANAGE_ROLE')")
    public RoleResponse createRole(
            @PathVariable Long groupId,
            @RequestBody CreateRoleRequest request
    ){

        CreateRoleRequest createRoleRequest = CreateRoleRequest.builder()
                .groupId(groupId)
                .name(request.getName())
                .description(request.getDescription())
                .build();

        return roleService.createRole(createRoleRequest);
    }



    @PutMapping("/{roleName}")
    @PreAuthorize("@groupPermission.isHasPermission(#groupId , 'MANAGE_ROLE')")
    public RoleResponse updateRole(
            @PathVariable Long groupId,
            @PathVariable String roleName,
            @RequestBody UpdateRoleRequest request
    ){

        UpdateRoleRequest updateRoleRequest = UpdateRoleRequest.builder()
                .groupId(groupId)
                .roleName(roleName)
                .name(request.getName())
                .build();

        return roleService.updateRole(updateRoleRequest);
    }

    @DeleteMapping("/{roleName}")
    @PreAuthorize("@groupPermission.isHasPermission(#groupId , 'MANAGE_ROLE')")
    public void deleteRole(
            @PathVariable Long groupId,
            @PathVariable String roleName
    ){

        DeleteRoleRequest deleteRoleRequest = DeleteRoleRequest.builder()
                .groupId(groupId)
                .roleName(roleName)
                .build();

        roleService.deleteRole(deleteRoleRequest);
    }


    @PostMapping("/{roleName}/attach")
    @PreAuthorize("@groupPermission.isHasPermission(#groupId , 'MANAGE_ROLE')")
    public RoleResponse attachAuthority(
            @PathVariable Long groupId,
            @PathVariable String roleName,
            @RequestBody AuthorityRole request
    ){

        AuthorityRole authorityRole = AuthorityRole.builder()
                .groupId(groupId)
                .roleName(roleName)
                .authorityName(request.getAuthorityName())
                .build();

        return roleService.attachAuthority(authorityRole);
    }

    @PostMapping("/{roleName}/detach")
    @PreAuthorize("@groupPermission.isHasPermission(#groupId , 'MANAGE_ROLE')")
    public RoleResponse detachAuthority(
            @PathVariable Long groupId,
            @PathVariable String roleName,
            @RequestBody AuthorityRole request
    ){

        AuthorityRole authorityRole = AuthorityRole.builder()
                .groupId(groupId)
                .roleName(roleName)
                .authorityName(request.getAuthorityName())
                .build();

        return roleService.detachAuthority(authorityRole);
    }
}
