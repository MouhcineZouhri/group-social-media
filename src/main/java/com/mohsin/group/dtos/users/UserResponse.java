package com.mohsin.group.dtos.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mohsin.group.dtos.role.RoleResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class UserResponse {
    private Long id;

    private String username;

    @JsonProperty(value = "roles")
    private RoleResponse roleResponse;
}
