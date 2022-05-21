package com.mohsin.group.services;

import com.mohsin.group.dtos.invitation.AnswerInvitationRequest;
import com.mohsin.group.dtos.invitation.InvitationRequest;
import com.mohsin.group.dtos.invitation.InvitationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InvitationService {

    // GET MY NON-Accepted INVITATION , GET NON-Accepted GROUP INVITATION ,

    Page<InvitationResponse> getUserNonAnswerInvitations(Pageable pageable);

    Page<InvitationResponse> getGroupNonAnswerInvitations(Long orderId , Pageable pageable);

    InvitationResponse createInvitation(InvitationRequest request);

    // ACCEPTED OR IGNORED IT
    InvitationResponse answerInvitation(AnswerInvitationRequest request);

}
