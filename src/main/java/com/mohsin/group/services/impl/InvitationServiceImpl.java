package com.mohsin.group.services.impl;

import com.mohsin.group.repositories.*;
import com.mohsin.group.dtos.invitation.AnswerInvitationRequest;
import com.mohsin.group.dtos.invitation.InvitationRequest;
import com.mohsin.group.dtos.invitation.InvitationResponse;
import com.mohsin.group.entities.*;
import com.mohsin.group.entities.keys.InvitationKey;
import com.mohsin.group.exceptions.*;
import com.mohsin.group.mapper.InvitationMapper;
import com.mohsin.group.services.InvitationService;
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
public class InvitationServiceImpl implements InvitationService {
    private final  UserRepository userRepository;

    private final GroupRepository groupRepository;

    private final InvitationRepository invitationRepository;

    private final InvitationMapper invitationMapper;

    private final GroupUtils groupUtils;


    @Override // USER Authentications
    public Page<InvitationResponse> getUserNonAnswerInvitations(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username =  authentication.getName();

        User user = userRepository.findOrThrow(username);

        return  invitationRepository.getGroupNonAnswerInvitation(user, pageable)
                .map(invitationMapper::toInvitationResponse);
    }

    @Override // MANAGE_MEMBERS
    public Page<InvitationResponse> getGroupNonAnswerInvitations(Long groupId, Pageable pageable) {
        Group group = groupRepository.findOrThrow(groupId);

        return invitationRepository.getGroupNonAnswerInvitation(group , pageable)
                .map(invitationMapper::toInvitationResponse);
    }

    @Override // MANAGE_MEMBERS
    public InvitationResponse createInvitation(InvitationRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username= authentication.getName();

        User sender = userRepository.findOrThrow(username);

        User receiver = userRepository.findOrThrow(request.getReceiverId());

        if (sender.equals(receiver))
            throw new NotAllowedException();

        Group group = groupRepository.findOrThrow(request.getGroupId());

        InvitationKey invitationKey = InvitationKey
                .builder()
                .groupId(group.getId())
                .receiverId(receiver.getId())
                .build();

        Invitation checkInvitation = invitationRepository
                                            .findById(invitationKey)
                                            .orElse(null);

        if(checkInvitation != null ) throw new InvitationAlreadySendException();

        Invitation invitation = Invitation.builder()
                .invitationKey(invitationKey)
                .sender(sender)
                .receiver(receiver)
                .group(group)
                .isAnswer(false)
                .accept(false)
                .build();

        Invitation savedInvitation = invitationRepository.save(invitation);

        return invitationMapper.toInvitationResponse(savedInvitation);
    }

    @Override // the receiver only  => authentication
    public InvitationResponse answerInvitation(AnswerInvitationRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username= authentication.getName();

        User receiver = userRepository.findOrThrow(username);

        Group group = groupRepository.findOrThrow(request.getGroupId());

        InvitationKey invitationKey  = InvitationKey.builder()
                .groupId(group.getId())
                .receiverId(receiver.getId())
                .build();

        Invitation invitation = invitationRepository.findOrThrow(invitationKey);

        if(invitation.getReceiver() != receiver) throw new NotAllowedException();

        if(invitation.getIsAnswer()) throw new InvitationAlreadyAnsweredException();

        if(request.getAccept())
            groupUtils.addUserToGroup(receiver , group , GlobalRole.MEMBER);

        invitation.setAccept(request.getAccept());

        invitation.setIsAnswer(true);

        Invitation savedInvitation = invitationRepository.save(invitation);

        return invitationMapper.toInvitationResponse(savedInvitation);
    }

}
