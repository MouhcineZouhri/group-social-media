package com.mohsin.group.services.impl;

import com.mohsin.group.repositories.GroupRepository;
import com.mohsin.group.repositories.UserRepository;
import com.mohsin.group.repositories.UserRoleInGroupRepository;
import com.mohsin.group.dtos.group.*;
import com.mohsin.group.dtos.users.UserResponse;
import com.mohsin.group.entities.Group;
import com.mohsin.group.entities.User;
import com.mohsin.group.entities.UserRoleInGroup;
import com.mohsin.group.entities.keys.UserGroupKey;
import com.mohsin.group.mapper.GroupMapper;
import com.mohsin.group.mapper.UserMapper;
import com.mohsin.group.services.GroupService;
import com.mohsin.group.utils.GlobalRole;
import com.mohsin.group.utils.GroupUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final UserRepository userRepository;

    private final GroupRepository groupRepository;

    private final UserRoleInGroupRepository userRoleInGroupRepository;

    private final GroupMapper groupMapper;

    private final UserMapper userMapper;

    private final GroupUtils groupUtils;

    @Override // EveryOne
    public GroupResponse getGroup(Long id) {
        Group group = groupRepository.findOrThrow(id);

        return groupMapper.toGroupResponse(group);
    }

    @Override // EveryOne
    public GroupResponse createGroup(CreateGroupRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username =  authentication.getName();

        User user = userRepository.findOrThrow(username);

        Group g = Group.builder()
                        .name(request.getName())
                        .description(request.getDescription())
                        .user(user)
                        .posts(new HashSet<>())
                        .build();

        Group group  = groupRepository.save(g);

        groupUtils.addUserToGroup(user , group , GlobalRole.ADMIN);

        return groupMapper.toGroupResponse(group);
    }

    @Override // AUTHORITY MANAGE GROUP
    public GroupResponse updateGroup(UpdateGroupRequest request) {
        Long groupId = request.getId();

        Group group = groupRepository.findOrThrow(groupId);

        if(request.getName() != null) group.setName(request.getName());

        if(request.getDescription() != null) group.setDescription(request.getDescription());

        Group savedGroup  = groupRepository.save(group);

        return groupMapper.toGroupResponse(savedGroup);
    }

    @Override // AUTHORITY MANAGE GROUP
    public void deleteGroup(Long id) {
        // GROUP EXIST ALREADY CHECK IN PERMISSION
        groupRepository.deleteById(id);
    }

    @Override // ALL MEMBERS
    public void cancelMembership(Long groupId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username =  authentication.getName();

        User user = userRepository.findOrThrow(username);

        Group group = groupRepository.findOrThrow(groupId);

        UserGroupKey key = UserGroupKey.builder()
                .userId(user.getId())
                .groupId(group.getId())
                .build();

        UserRoleInGroup userRoleInGroup = userRoleInGroupRepository
                .findOrThrow(key);

        userRoleInGroupRepository.delete(userRoleInGroup);

    }

    @Override // MANAGE_MEMBERS // ADD MEMBER OR SWITCH THE ROLE
    public void addMember(AttachRoleRequest request) {

        User user = userRepository.findOrThrow(request.getUsername());

        Group group  = groupRepository.findOrThrow(request.getGroupId());

        groupUtils.addUserToGroup(user , group , request.getRoleName());
    }

    @Override // MANAGE_MEMBERS
    public void deleteMember(DetachRoleRequest request) {

        User user = userRepository.findOrThrow(request.getUsername());

        Group group  = groupRepository.findOrThrow(request.getGroupId());

        groupUtils.removeUserFromGroup(user , group);
    }

    @Override // ALL MEMBERS
    public Page<UserResponse> getAllUserOfGroup(Long groupId , Pageable pageable) {
        Group group = groupRepository.findOrThrow(groupId);

        Page<User> response = groupRepository.getUsersOfGroup(group, pageable);

        return response.map(user -> userMapper.toUserResponse(user , group));
    }

}
