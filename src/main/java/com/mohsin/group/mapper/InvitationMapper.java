package com.mohsin.group.mapper;

import com.mohsin.group.dtos.invitation.InvitationResponse;
import com.mohsin.group.entities.Invitation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InvitationMapper {

    public InvitationResponse toInvitationResponse(Invitation invitation){
        return InvitationResponse.builder()
                .groupId(invitation.getGroup().getId())
                .groupName(invitation.getGroup().getName())
                .receiverName(invitation.getReceiver().getUsername())
                .senderName(invitation.getSender().getUsername())
                .accept(invitation.getAccept())
                .build();
    }

}


