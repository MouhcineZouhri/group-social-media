package com.mohsin.group.repositories;

import com.mohsin.group.entities.Group;
import com.mohsin.group.entities.User;
import com.mohsin.group.entities.UserRoleInGroup;
import com.mohsin.group.entities.keys.UserGroupKey;
import com.mohsin.group.exceptions.UserNotFoundException;
import com.mohsin.group.exceptions.UserNotFoundOnGroupException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleInGroupRepository extends JpaRepository<UserRoleInGroup, UserGroupKey> {

    Optional<UserRoleInGroup> findByUserAndGroup(User user , Group group);

    default UserRoleInGroup findOrThrow(UserGroupKey key){
        return findById(key)
                .orElseThrow(() -> new UserNotFoundOnGroupException(key));
    }

}
