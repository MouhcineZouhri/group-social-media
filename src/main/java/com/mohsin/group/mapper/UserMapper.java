package com.mohsin.group.mapper;

import com.mohsin.group.repositories.RoleRepository;
import com.mohsin.group.dtos.users.UserResponse;
import com.mohsin.group.entities.Group;
import com.mohsin.group.entities.Role;
import com.mohsin.group.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    public UserResponse toUserResponse(User user , Group group){

        Role role = roleRepository.getUserRoleOnGroup(user , group)
                .orElseThrow(() -> new RuntimeException(user.getUsername()));

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roleResponse(roleMapper.toRoleResponse(role))
                .build();
    }

}
