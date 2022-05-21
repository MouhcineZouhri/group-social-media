package com.mohsin.group.repositories;

import com.mohsin.group.entities.Group;
import com.mohsin.group.entities.Invitation;
import com.mohsin.group.entities.User;
import com.mohsin.group.entities.keys.InvitationKey;
import com.mohsin.group.exceptions.InvitationNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InvitationRepository extends JpaRepository<Invitation  , InvitationKey> {

    @Query("select i from Invitation i where i.group = :group and i.isAnswer=false")
    Page<Invitation> getGroupNonAnswerInvitation(Group group , Pageable pageable);

    @Query("select i from Invitation i where i.receiver = :user and i.isAnswer=false")
    Page<Invitation> getGroupNonAnswerInvitation(User user, Pageable pageable);

    default Invitation findOrThrow(InvitationKey key){
        return findById(key)
                .orElseThrow(InvitationNotFoundException::new)  ;
    }

}
