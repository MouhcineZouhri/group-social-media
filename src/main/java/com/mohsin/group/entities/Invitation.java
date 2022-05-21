package com.mohsin.group.entities;

import com.mohsin.group.entities.keys.InvitationKey;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class Invitation {

    @EmbeddedId
    private InvitationKey invitationKey;

    @ManyToOne
    private User sender;

    @MapsId("receiverId")
    @ManyToOne
    private User receiver;

    @MapsId("groupId")
    @ManyToOne
    private Group group;

    private Boolean accept;

    @Column(name = "is_answer")
    private Boolean isAnswer;

}
