package com.mohsin.group.entities.keys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class JoinRequestKey implements Serializable {
    private Long requestorId;

    private Long groupId;

}
