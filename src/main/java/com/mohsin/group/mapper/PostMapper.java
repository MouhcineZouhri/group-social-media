package com.mohsin.group.mapper;

import com.mohsin.group.dtos.post.PostResponse;
import com.mohsin.group.entities.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostResponse toPostResponse(Post post);
}
