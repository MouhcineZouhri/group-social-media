package com.mohsin.group.dtos.invitation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class AnswerInvitationRequest {
    private Long groupId;
    private Boolean accept;
}
