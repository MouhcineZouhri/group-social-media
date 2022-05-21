package com.mohsin.group.repositories;

import com.mohsin.group.entities.Group;
import com.mohsin.group.entities.Role;
import com.mohsin.group.entities.User;
import com.mohsin.group.exceptions.RoleNotFoundException;
import com.mohsin.group.exceptions.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User ,Long> {
    Optional<User> findUserByUsername(String username);

    default User findOrThrow(String username){
        return findUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    default User findOrThrow(Long id){
        return findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }


}
