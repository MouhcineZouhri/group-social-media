package com.mohsin.group.dtos.invitation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class InvitationRequest {
    private Long receiverId;
    private Long groupId;
}
