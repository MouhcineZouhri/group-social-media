package com.mohsin.group.mapper;

import com.mohsin.group.dtos.group.GroupResponse;
import com.mohsin.group.entities.Group;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    GroupResponse toGroupResponse(Group group);
}
