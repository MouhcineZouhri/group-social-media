package com.mohsin.group.mapper;

import com.mohsin.group.dtos.joinrequest.JoinResponse;
import com.mohsin.group.entities.JoinRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JoinRequestMapper {


    public JoinResponse toJoinResponse(JoinRequest request){

        String responderName= request.getResponder() != null
                        ? request.getResponder().getUsername()
                        : null;

        return JoinResponse.builder()
                .groupId(request.getGroup().getId())
                .groupName(request.getGroup().getName())
                .requestorName(request.getRequester().getUsername())
                .responderName(responderName)
                .isAnswer(request.getIsAnswer())
                .accept(request.getAccept())
                .build();

    }
}
