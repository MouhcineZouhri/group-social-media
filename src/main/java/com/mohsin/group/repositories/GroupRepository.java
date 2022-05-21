package com.mohsin.group.repositories;

import com.mohsin.group.entities.Group;
import com.mohsin.group.entities.User;
import com.mohsin.group.exceptions.GroupNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GroupRepository extends JpaRepository<Group , Long> {

    @Query("select  u from User  u inner join u.roleOnGroups rg where rg.group = :group")
    Page<User> getUsersOfGroup(Group group , Pageable pageable);


    default Group findOrThrow(Long id){
        return findById(id)
                .orElseThrow(() -> new GroupNotFoundException(id));
    }


}
