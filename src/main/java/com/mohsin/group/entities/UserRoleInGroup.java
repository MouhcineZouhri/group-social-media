package com.mohsin.group.entities;

import com.mohsin.group.entities.keys.UserGroupKey;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Table(name = "user_group_role")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class UserRoleInGroup {

    @EmbeddedId
    private UserGroupKey userGroupKey;

    @MapsId("userId")
    @ManyToOne
    private User user;

    @MapsId("groupId")
    @ManyToOne
    private Group group;

    @ManyToOne
    private Role role;


}
