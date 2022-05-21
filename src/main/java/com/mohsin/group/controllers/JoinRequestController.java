package com.mohsin.group.controllers;

import com.mohsin.group.dtos.joinrequest.AnswerJoinRequest;
import com.mohsin.group.dtos.joinrequest.JoinResponse;
import com.mohsin.group.services.JoinRequestService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/groups/{groupId}/joins")
@AllArgsConstructor
public class JoinRequestController {

    private final JoinRequestService joinRequestService;

    @GetMapping
    @PreAuthorize("@groupPermission.isHasPermission(#groupId , 'MANAGE_MEMBERS')")
    public Page<JoinResponse> getGroupNonAnsweredJoinRequest(
            @PathVariable Long groupId,
            @RequestParam(value = "page"  , defaultValue = "0") int page,
            @RequestParam(value = "size" , defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page , size);
        return this.joinRequestService.getGroupNonAnsweredJoinRequest(groupId , pageable);
    }

    @GetMapping("/users") // SHOULD BE IN ANOTHER CONTROLLER !
    public Page<JoinResponse> getUsersNonAnsweredJoinRequest(
            @RequestParam(value = "page"  , defaultValue = "0") int page,
            @RequestParam(value = "size" , defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page , size);
        return this.joinRequestService.getUserNonAnsweredJoinRequest(pageable);
    }


    @PostMapping
    public JoinResponse createJoinRequest(
            @PathVariable Long groupId
    ){
        return this.joinRequestService.createJoinRequest(groupId);
    }

    @PutMapping
    @PreAuthorize("@groupPermission.isHasPermission(#groupId , 'MANAGE_MEMBERS')")
    public JoinResponse answerJoinRequest(
            @PathVariable Long groupId,
            @RequestBody AnswerJoinRequest request
    ){
        AnswerJoinRequest answerJoinRequest = AnswerJoinRequest.builder()
                .groupId(groupId)
                .requesterId(request.getRequesterId())
                .accept(request.getAccept())
                .build();

        return this.joinRequestService.answerJoinRequest(answerJoinRequest);
    }

}
