package com.mohsin.group.dtos.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class CreateRoleRequest {
    private Long groupId;
    private String name;
    private String description;
}
