package com.mohsin.group.dtos.joinrequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class AnswerJoinRequest {
    private Long groupId;

    private Long requesterId;

    private Boolean accept;
}
