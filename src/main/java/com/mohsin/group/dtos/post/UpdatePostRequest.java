package com.mohsin.group.dtos.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class UpdatePostRequest {
    private Long postId;
    private Long groupId;
    private String content;

}
