package com.mohsin.group.intergration.repositories;

import com.mohsin.group.entities.Group;
import com.mohsin.group.entities.Role;
import com.mohsin.group.entities.User;
import com.mohsin.group.entities.UserRoleInGroup;
import com.mohsin.group.entities.keys.UserGroupKey;
import com.mohsin.group.repositories.GroupRepository;
import com.mohsin.group.repositories.RoleRepository;
import com.mohsin.group.repositories.UserRepository;
import com.mohsin.group.repositories.UserRoleInGroupRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class GroupRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleInGroupRepository userRoleInGroupRepository;

    @Test
    public void getUsersOfGroupTest(){

        User user = userRepository.save(
                User.builder()
                    .username("mohsin")
                    .groups(new HashSet<>())
                    .build()
        );

        Group group = groupRepository.save(
                Group.builder()
                    .user(user)
                    .name("Gr 1")
                    .build()
        );

        // User should have a role to be in a group .

        Role role = roleRepository.save(
                Role.builder()
                        .name("Role1")
                        .build()
        );

        UserGroupKey key  = UserGroupKey.builder()
                .userId(user.getId())
                .groupId(group.getId())
                .build();

        UserRoleInGroup userRoleInGroup = UserRoleInGroup.builder()
                .userGroupKey(key)
                .role(role)
                .group(group)
                .user(user)
                .build();

        userRoleInGroupRepository.save(userRoleInGroup);

        Page<User> users  = groupRepository.getUsersOfGroup(group, Pageable.unpaged());

        assertThat(users.getContent().size()).isEqualTo(1);
        assertThat(users.getContent()).contains(user);

    }
}
