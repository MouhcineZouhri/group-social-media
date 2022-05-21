package com.mohsin.group.unit.entities;

import com.mohsin.group.entities.Group;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class GroupTest {

    @Test
    public void testEqualGroup(){

        Group group1 = Group.builder()
                .id(1L)
                .name("mohsin")
                .build();

        Group group2 = Group.builder()
                .name("mohsin")
                .build();

        Assertions.assertThat(group1).isEqualTo(group2);
    }
}
