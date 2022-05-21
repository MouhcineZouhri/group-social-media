package com.mohsin.group.dtos.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class UpdateGroupRequest {
    private Long id;
    private String name;
    private String description;
}
