package com.mohsin.group.repositories;

import com.mohsin.group.entities.Group;
import com.mohsin.group.entities.JoinRequest;
import com.mohsin.group.entities.User;
import com.mohsin.group.entities.keys.JoinRequestKey;
import com.mohsin.group.exceptions.JoinRequestNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JoinRequestRepository extends JpaRepository<JoinRequest, JoinRequestKey> {

    @Query("select j from JoinRequest j where j.group = :group and j.isAnswer = false")
    Page<JoinRequest> getNonAnsweredJoinRequest(Group group , Pageable pageable);

    @Query("select j from JoinRequest j where j.requester = :user and j.isAnswer = false")
    Page<JoinRequest> getNonAnsweredJoinRequest(User user , Pageable pageable);

    default JoinRequest findOrThrow(JoinRequestKey key){
        return findById(key)
                .orElseThrow(JoinRequestNotFoundException::new);
    }

}
