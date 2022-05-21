package com.mohsin.group.controllers;

import com.mohsin.group.dtos.group.*;
import com.mohsin.group.dtos.users.UserResponse;
import com.mohsin.group.services.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/groups")
@AllArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @GetMapping("/{id}")
    public GroupResponse get(@PathVariable Long id){
        return groupService.getGroup(id);
    }

    @PostMapping
    public GroupResponse save(@RequestBody CreateGroupRequest request){
        return groupService.createGroup(request);
    }

    @PutMapping("/{groupId}")
    @PreAuthorize("@groupPermission.isHasPermission(#groupId , 'MANAGE_GROUP')")
    public GroupResponse update(@PathVariable Long groupId ,@RequestBody UpdateGroupRequest request){
        request.setId(groupId);
        return groupService.updateGroup(request);
    }

    @DeleteMapping("/{groupId}")
    @PreAuthorize("@groupPermission.isHasPermission(#groupId , 'MANAGE_GROUP')")
    public String delete(@PathVariable Long groupId){
        groupService.deleteGroup(groupId);
        return "success !";
    }

    @PostMapping("/{groupId}/attach")
    @PreAuthorize("@groupPermission.isHasPermission(#groupId , 'MANAGE_GROUP')")
    public void attachRoleToMember(
            @PathVariable Long groupId,
            @RequestBody AttachRoleRequest request
    ){

        AttachRoleRequest affectRoleRequest = AttachRoleRequest.builder()
                .groupId(groupId)
                .roleName(request.getRoleName())
                .username(request.getUsername())
                .build();

        groupService.addMember(affectRoleRequest);
    }

    @DeleteMapping("/{groupId}/detach")
    @PreAuthorize("@groupPermission.isHasPermission(#groupId , 'MANAGE_GROUP')")
    public void detachRoleToMember(
            @PathVariable Long groupId,
            @RequestBody DetachRoleRequest request
    ){

        DetachRoleRequest detachRoleRequest = DetachRoleRequest.builder()
                .groupId(groupId)
                .username(request.getUsername())
                .build();

        groupService.deleteMember(detachRoleRequest);
    }

    @DeleteMapping("/{groupId}/cancel")
    @PreAuthorize("@groupPermission.isHasPermission(#groupId , 'MEMBER')")
    public void detachRoleToMember(
            @PathVariable Long groupId
    ){
        // ADMIN CAN CANCEL ONLY IF THERE IS ANOTHER ADMIN

        groupService.cancelMembership(groupId);
    }


    @GetMapping("/{groupId}/users")
    @PreAuthorize("@groupPermission.isHasPermission(#groupId , 'MANAGE_MEMBERS')")
    public Page<UserResponse> getUsersOfGroup(
            @PathVariable Long groupId,
            @RequestParam(value = "page"  , defaultValue = "0") int page,
            @RequestParam(value = "size" , defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page , size);
        return groupService.getAllUserOfGroup(groupId ,pageable);
    }

}
