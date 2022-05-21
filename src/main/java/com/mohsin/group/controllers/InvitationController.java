package com.mohsin.group.controllers;

import com.mohsin.group.dtos.invitation.AnswerInvitationRequest;
import com.mohsin.group.dtos.invitation.InvitationRequest;
import com.mohsin.group.dtos.invitation.InvitationResponse;
import com.mohsin.group.services.InvitationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/groups/{groupId}/invitations")
@AllArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;

    @GetMapping
    @PreAuthorize("@groupPermission.isHasPermission(#groupId , 'MANAGE_MEMBERS')")
    public Page<InvitationResponse> getGroupNonAnswerInvitations(
            @PathVariable Long groupId,
            @RequestParam(value = "page"  , defaultValue = "0") int page,
            @RequestParam(value = "size" , defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page , size);
        return this.invitationService.getGroupNonAnswerInvitations(groupId ,pageable);
    }

    @GetMapping("/users")
    public Page<InvitationResponse> getUserNonAnswerInvitations(
            @RequestParam(value = "page"  , defaultValue = "0") int page,
            @RequestParam(value = "size" , defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page , size);
        return this.invitationService.getUserNonAnswerInvitations(pageable);
    }

    @PostMapping
    @PreAuthorize("@groupPermission.isHasPermission(#groupId , 'MANAGE_MEMBERS')")
    public InvitationResponse createInvitations(
            @PathVariable Long groupId,
            @RequestBody InvitationRequest request
    ){
        InvitationRequest invitationRequest = InvitationRequest.builder()
                .groupId(groupId)
                .receiverId(request.getReceiverId())
                .build();

        return this.invitationService.createInvitation(invitationRequest);
    }

    @PutMapping // You have to be receiver
    public InvitationResponse answerInvitation(
            @PathVariable Long groupId,
            @RequestBody AnswerInvitationRequest request
    ){

        AnswerInvitationRequest answer = AnswerInvitationRequest.builder()
                .groupId(groupId)
                .accept(request.getAccept())
                .build();

        return this.invitationService.answerInvitation(answer);
    }

}
