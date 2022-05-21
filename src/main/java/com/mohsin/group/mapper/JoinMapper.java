package com.mohsin.group.mapper;

import com.mohsin.group.dtos.joinrequest.JoinResponse;
import com.mohsin.group.entities.JoinRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JoinMapper {

    JoinResponse toDemandMembershipResponse(JoinRequest membership);
}
