package com.mohsin.group.utils;

import com.mohsin.group.entities.Group;
import com.mohsin.group.entities.Role;
import com.mohsin.group.entities.User;
import com.mohsin.group.entities.UserRoleInGroup;
import com.mohsin.group.entities.keys.UserGroupKey;
import com.mohsin.group.exceptions.UserAlreadyHaveRoleException;
import com.mohsin.group.repositories.RoleRepository;
import com.mohsin.group.repositories.UserRoleInGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class GroupUtils {

    private final RoleRepository roleRepository;

    private final UserRoleInGroupRepository userRoleInGroupRepository;

    public void addUserToGroup(User user , Group group , String roleName){
        Role role;

        if(GlobalRole.GLOBAL_ROLE.contains(roleName)){
            role = roleRepository.findOrThrow(roleName);
        }else {
            role = roleRepository.findOrThrow(roleName , group);
        }

        UserGroupKey key = UserGroupKey.builder()
                .groupId(group.getId())
                .userId(user.getId())
                .build();

        UserRoleInGroup userRoleInGroup = userRoleInGroupRepository.findById(key)
                .orElse(null);

        if(userRoleInGroup != null){ // if user already has a Role in group Then change it .

            if(userRoleInGroup.getRole().equals(role))
                throw new UserAlreadyHaveRoleException(user ,role);

            userRoleInGroup.setRole(role);

            userRoleInGroupRepository.save(userRoleInGroup);
        }
        else {
            UserRoleInGroup newUserRoleInGroup = UserRoleInGroup.builder()
                    .userGroupKey(key)
                    .user(user)
                    .group(group)
                    .role(role)
                    .build();

            userRoleInGroupRepository.save(newUserRoleInGroup);
        }
    }

    public void removeUserFromGroup(User user , Group group) {

        UserGroupKey key = UserGroupKey.builder()
                .groupId(group.getId())
                .userId(user.getId())
                .build();

        UserRoleInGroup userRoleInGroup = userRoleInGroupRepository
                .findOrThrow(key);

        userRoleInGroupRepository.delete(userRoleInGroup);

    }

}
