package com.mohsin.group.dtos.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class AuthorityRole {
    private Long groupId;

    private String roleName;

    private String authorityName;
}
