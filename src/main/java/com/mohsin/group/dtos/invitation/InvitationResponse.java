package com.mohsin.group.dtos.invitation;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class InvitationResponse {
    private Long groupId;

    private String groupName;

    private String senderName;

    private String receiverName;

    private Boolean accept;
}
