package com.mohsin.group.entities;

import com.mohsin.group.entities.keys.JoinRequestKey;
import lombok.*;

import javax.persistence.*;

@Table(name = "join_request")
@Entity
@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class JoinRequest {

    @EmbeddedId
    private JoinRequestKey joinRequestKey;

    @MapsId("requestorId")
    @ManyToOne
    private User requester;

    @ManyToOne
    private User responder;

    @MapsId("groupId")
    @ManyToOne
    private Group group;

    private Boolean accept ;

    @Column(name = "is_answer")
    private Boolean isAnswer;
}
