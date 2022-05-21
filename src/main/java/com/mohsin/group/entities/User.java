package com.mohsin.group.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @EqualsAndHashCode.Include
    private String username;

    private String password;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    private Set<Group> groups ;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Post> posts ;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    private Set<UserRoleInGroup> roleOnGroups;

}
