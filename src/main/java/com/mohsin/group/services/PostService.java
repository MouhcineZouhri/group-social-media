package com.mohsin.group.services;

import com.mohsin.group.dtos.post.*;
import com.mohsin.group.entities.Post;

public interface PostService {

    // GET ALL POSTS THAT USER CAN SEE (ALL POSTS OF THAT USER MEMBER IN THE GROUP)
    // Page<PostResponse> getAllPosts();

    PostResponse getPost(GetPostRequest request);

    PostResponse createPost(CreatePostRequest request);

    PostResponse updatePost(UpdatePostRequest request);

    PostResponse approvePost(Long postId);

    PostResponse ignorePost(Long postId);

    void deletePost(DeletePostRequest request);

}
