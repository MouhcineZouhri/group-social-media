package com.mohsin.group.unit.mapper;


import com.mohsin.group.dtos.invitation.InvitationResponse;
import com.mohsin.group.entities.Group;
import com.mohsin.group.entities.Invitation;
import com.mohsin.group.entities.User;
import com.mohsin.group.mapper.InvitationMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class InvitationMapperTest {

    @Test
    public void  testInvitationMapper(){
        Group group = Group.builder()
                .id(1L)
                .name("Gr 1")
                .build();

        User sender = User.builder()
                .username("mohsin")
                .build();

        User receiver = User.builder()
                .username("omar")
                .build();

        Invitation invitation = Invitation.builder()
                .group(group)
                .receiver(receiver)
                .sender(sender)
                .accept(false)
                .build();

        InvitationMapper mapper = new InvitationMapper();

        InvitationResponse response = mapper.toInvitationResponse(invitation);

        Assertions.assertThat(response.getGroupName()).isEqualTo(group.getName());
        Assertions.assertThat(response.getReceiverName()).isEqualTo(receiver.getUsername());
        Assertions.assertThat(response.getSenderName()).isEqualTo(sender.getUsername());
        Assertions.assertThat(response.getAccept()).isFalse();

    }
}
