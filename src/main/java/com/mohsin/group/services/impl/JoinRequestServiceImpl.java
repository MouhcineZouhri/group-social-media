package com.mohsin.group.services.impl;

import com.mohsin.group.exceptions.GroupNotFoundException;
import com.mohsin.group.repositories.*;
import com.mohsin.group.dtos.joinrequest.AnswerJoinRequest;
import com.mohsin.group.dtos.joinrequest.JoinResponse;
import com.mohsin.group.entities.*;
import com.mohsin.group.entities.keys.JoinRequestKey;
import com.mohsin.group.exceptions.AlreadyAskJoinException;
import com.mohsin.group.mapper.JoinRequestMapper;
import com.mohsin.group.services.JoinRequestService;
import com.mohsin.group.utils.GlobalRole;
import com.mohsin.group.utils.GroupUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JoinRequestServiceImpl implements JoinRequestService {

    private final UserRepository userRepository;

    private final JoinRequestRepository joinRequestRepository;

    private final GroupRepository groupRepository;

    private final GroupUtils groupUtils;

    private final JoinRequestMapper joinRequestMapper;


    @Override // MANAGE_MEMBERS
    public Page<JoinResponse> getGroupNonAnsweredJoinRequest(Long groupId , Pageable pageable) {
        Group group = groupRepository.findOrThrow(groupId);

        Page<JoinRequest> response = joinRequestRepository
                .getNonAnsweredJoinRequest(group, pageable);

        return response.map(joinRequestMapper::toJoinResponse);
    }

    @Override // authentications
    public Page<JoinResponse> getUserNonAnsweredJoinRequest(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username =  authentication.getName();

        User requester = userRepository.findOrThrow(username);

        Page<JoinRequest> response = joinRequestRepository.getNonAnsweredJoinRequest(requester, pageable);

        return response.map(joinRequestMapper::toJoinResponse);
    }

    @Override // any user
    public JoinResponse createJoinRequest(Long groupId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username =  authentication.getName();

        User requester = userRepository.findOrThrow(username);

        Group group = groupRepository.findOrThrow(groupId);

        JoinRequestKey key = JoinRequestKey.builder()
                .groupId(group.getId())
                .requestorId(requester.getId())
                .build();

        JoinRequest joinRequest = joinRequestRepository.findById(key)
                .orElse(null);

        if (joinRequest != null) throw new AlreadyAskJoinException();

        JoinRequest savedJoinRequest = joinRequestRepository.save(
                JoinRequest.builder()
                        .joinRequestKey(key)
                        .requester(requester)
                        .group(group)
                        .accept(false)
                        .isAnswer(false)
                        .build()
        );

        return joinRequestMapper.toJoinResponse(savedJoinRequest);
    }

    @Override // MANAGE_MEMBER
    public JoinResponse answerJoinRequest(AnswerJoinRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username =  authentication.getName();

        User responder = userRepository.findOrThrow(username);

        User requester = userRepository.findOrThrow(request.getRequesterId());

        Group group = groupRepository.findOrThrow(request.getGroupId());

        JoinRequestKey key = JoinRequestKey.builder()
                .groupId(group.getId())
                .requestorId(requester.getId())
                .build();

        JoinRequest joinRequest = joinRequestRepository.findOrThrow(key);

        if(request.getAccept())
            groupUtils.addUserToGroup(requester , group , GlobalRole.MEMBER);

        joinRequest.setResponder(responder);
        joinRequest.setAccept(request.getAccept());
        joinRequest.setIsAnswer(true);

        JoinRequest savedJoinRequestor = joinRequestRepository.save(joinRequest);

        return joinRequestMapper.toJoinResponse(savedJoinRequestor);
    }
}
