package com.mohsin.group.controllers;

import com.mohsin.group.dtos.post.*;
import com.mohsin.group.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/groups/{groupId}/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}")
    @PreAuthorize("@groupPermission.isHasPermission(#groupId , 'MEMBER')")
    public PostResponse get(@PathVariable Long groupId , @PathVariable Long postId){
        GetPostRequest request = GetPostRequest.builder()
                .groupId(groupId)
                .postId(postId)
                .build();

        return postService.getPost(request);
    }

    @PostMapping // MEMBER WILL BE NON APPROVE , WHO HAS MANAGE_POST  POST APPROVE !
    @PreAuthorize("@groupPermission.isHasPermission(#groupId , 'MEMBER')")
    public PostResponse save(@PathVariable Long groupId , @RequestBody CreatePostRequest request ){
        request.setGroupId(groupId);

        return postService.createPost(request);
    }

    @PutMapping("/{postId}")
    @PreAuthorize("@groupPermission.isAuthor(#postId)")
    public PostResponse update(@PathVariable Long groupId
            , @PathVariable Long postId
            , @RequestBody UpdatePostRequest request){

        request.setPostId(postId);

        request.setGroupId(groupId);

        return postService.updatePost(request);
    }

    @PostMapping("/{postId}/approve")
    @PreAuthorize("@groupPermission.isHasPermission(#groupId , 'MANAGE_POST')")
    public PostResponse approvePost(
            @PathVariable  Long groupId,
            @PathVariable Long postId
    ){
        return this.postService.approvePost(postId);
    }

    @PostMapping("/{postId}/ignore")
    @PreAuthorize("@groupPermission.isHasPermission(#groupId , 'MANAGE_POST')")
    public PostResponse ignorePost(
            @PathVariable  Long groupId,
            @PathVariable Long postId
    ){
        return this.postService.ignorePost(postId);
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("@groupPermission.isAuthorOrManagePost(#groupId , #postId)")
    public String delete(@PathVariable Long postId, @PathVariable Long groupId){
        DeletePostRequest request = DeletePostRequest.builder()
                .groupId(groupId)
                .postId(postId)
                .build();

        postService.deletePost(request);

        return "success !";
    }

}
