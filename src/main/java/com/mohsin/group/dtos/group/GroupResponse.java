package com.mohsin.group.dtos.group;

import lombok.*;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor @ToString
public class GroupResponse {
    private Long id;
    private String name;
    private String description;
}
