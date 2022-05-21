package com.mohsin.group.dtos.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class GetRoleRequest {
    private String  roleName;
    private Long groupId;
}
