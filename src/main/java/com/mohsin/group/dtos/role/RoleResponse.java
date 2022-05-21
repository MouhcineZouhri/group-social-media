package com.mohsin.group.dtos.role;

import com.mohsin.group.entities.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class RoleResponse {
    private String name;

    private Set<Authority> authorities;

}
