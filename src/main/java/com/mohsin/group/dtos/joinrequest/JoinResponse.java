package com.mohsin.group.dtos.joinrequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class JoinResponse {
    private Long groupId;

    private String groupName;

    private String requestorName;

    private String responderName;

    private Boolean accept;

    private Boolean isAnswer ;
}
