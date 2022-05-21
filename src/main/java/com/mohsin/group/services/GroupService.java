package com.mohsin.group.services;


import com.mohsin.group.dtos.group.*;
import com.mohsin.group.dtos.users.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GroupService {

    GroupResponse getGroup(Long id);

    GroupResponse createGroup(CreateGroupRequest request) ;

    GroupResponse updateGroup(UpdateGroupRequest request);

    void deleteGroup(Long id);

    void cancelMembership(Long groupId);

    void addMember(AttachRoleRequest request);

    void deleteMember(DetachRoleRequest request);

    Page<UserResponse> getAllUserOfGroup(Long groupId , Pageable pageable);

}
