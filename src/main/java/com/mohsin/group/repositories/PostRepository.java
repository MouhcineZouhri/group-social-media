package com.mohsin.group.repositories;

import com.mohsin.group.entities.Post;
import com.mohsin.group.exceptions.PostNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post , Long> {

    default Post findOrThrow(Long id){
        return findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

}
