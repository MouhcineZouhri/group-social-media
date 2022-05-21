package com.mohsin.group.services;

import com.mohsin.group.dtos.joinrequest.AnswerJoinRequest;
import com.mohsin.group.dtos.joinrequest.JoinResponse;
import com.mohsin.group.entities.JoinRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JoinRequestService {

    Page<JoinResponse> getGroupNonAnsweredJoinRequest(Long groupId, Pageable pageable);

    Page<JoinResponse> getUserNonAnsweredJoinRequest(Pageable pageable);

    JoinResponse createJoinRequest(Long groupId);

    JoinResponse answerJoinRequest(AnswerJoinRequest request);

}
