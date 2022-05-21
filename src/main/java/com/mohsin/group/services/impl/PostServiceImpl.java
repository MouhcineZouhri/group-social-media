package com.mohsin.group.services.impl;

import com.mohsin.group.repositories.GroupRepository;
import com.mohsin.group.repositories.PostRepository;
import com.mohsin.group.repositories.UserRepository;
import com.mohsin.group.dtos.post.*;
import com.mohsin.group.entities.Group;
import com.mohsin.group.entities.Post;
import com.mohsin.group.entities.User;
import com.mohsin.group.exceptions.GroupNotFoundException;
import com.mohsin.group.exceptions.PostNotFoundException;
import com.mohsin.group.exceptions.UserNotFoundException;
import com.mohsin.group.mapper.PostMapper;
import com.mohsin.group.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService  {

    private PostRepository postRepository;

    private UserRepository userRepository;

    private GroupRepository groupRepository;

    private PostMapper postMapper;


    @Override // MEMBERS
    public PostResponse getPost(GetPostRequest request) {
        Group group = groupRepository.findOrThrow(request.getGroupId());

        Post post = postRepository.findOrThrow(request.getPostId());
        
        return postMapper.toPostResponse(post);
    }

    @Override // MEMBERS
    public PostResponse createPost(CreatePostRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username =  authentication.getName();

        User user = userRepository.findOrThrow(username);

        Group group =groupRepository.findOrThrow(request.getGroupId());

        Post p  = Post.builder()
                .content(request.getContent())
                .approved(false)
                .user(user)
                .group(group)
                .build();

        Post post  = postRepository.save(p);

        return postMapper.toPostResponse(post);
    }

    @Override // AUTHOR
    public PostResponse updatePost(UpdatePostRequest request) {
        Post post = postRepository.findOrThrow(request.getPostId());

        if(request.getContent() != null) post.setContent(request.getContent());

        Post savedPost = postRepository.save(post);

        return postMapper.toPostResponse(savedPost);
    }

    @Override // MANAGE_POST
    public PostResponse approvePost(Long postId) {
        Post post = postRepository.findOrThrow(postId);

        post.setApproved(true);

        Post savedPost = postRepository.save(post);

        return postMapper.toPostResponse(savedPost) ;
    }

    @Override // MANAGE_POST
    public PostResponse ignorePost(Long postId) {
        Post post = postRepository.findOrThrow(postId); // find or throw NotFoundException

        post.setApproved(false);

        Post savedPost = postRepository.save(post);

        return postMapper.toPostResponse(savedPost) ;
    }

    @Override // MANAGE_POST OR AUTHOR
    public void deletePost(DeletePostRequest request) {
        Post post = postRepository.findOrThrow(request.getPostId());
        postRepository.delete(post);
    }

}
