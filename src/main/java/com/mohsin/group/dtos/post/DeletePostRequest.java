package com.mohsin.group.dtos.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class DeletePostRequest {
    private Long groupId;
    private Long postId;
}
