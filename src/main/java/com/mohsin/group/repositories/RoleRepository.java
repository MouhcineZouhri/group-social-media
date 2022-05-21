package com.mohsin.group.repositories;

import com.mohsin.group.entities.Group;
import com.mohsin.group.entities.Post;
import com.mohsin.group.entities.Role;
import com.mohsin.group.entities.User;
import com.mohsin.group.exceptions.PostNotFoundException;
import com.mohsin.group.exceptions.RoleNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role , Long> {
    Optional<Role> findRoleByName(String name);

    Optional<Role> findByNameAndGroup(String name , Group group);

    @Query("select  r from Role  r inner join r.roleOnGroups rg where rg.group = :group")
    Page<Role> getRolesOfGroup(Group group , Pageable pageable);

    @Query("select r from Role r inner join r.roleOnGroups rg where  rg.user = :user and  rg.group = :group")
    Optional<Role> getUserRoleOnGroup(@Param("user") User user , Group group);


    default Role findOrThrow(String name){
        return findRoleByName(name)
                .orElseThrow(() -> new RoleNotFoundException(name));
    }

    default Role findOrThrow(String name , Group group){
        return findByNameAndGroup(name , group)
                .orElseThrow(() -> new RoleNotFoundException(name));
    }

}
