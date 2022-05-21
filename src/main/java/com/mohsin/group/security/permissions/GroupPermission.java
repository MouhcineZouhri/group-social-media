package com.mohsin.group.security.permissions;

import com.mohsin.group.exceptions.AuthorityNotFoundException;
import com.mohsin.group.exceptions.PostNotFoundException;
import com.mohsin.group.exceptions.UserNotFoundException;
import com.mohsin.group.repositories.*;
import com.mohsin.group.entities.*;
import com.mohsin.group.entities.keys.UserGroupKey;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class GroupPermission {

    private final GroupRepository groupRepository;

    private final UserRepository userRepository;

    private final UserRoleInGroupRepository userRoleInGroupRepository;

    private final AuthorityRepository authorityRepository;

    private final PostRepository postRepository;

    public Boolean isHasPermission(Long groupId , String authority){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findOrThrow(username);

        Group group = groupRepository.findOrThrow(groupId);

        UserGroupKey key = UserGroupKey.builder()
                .groupId(group.getId())
                .userId(user.getId())
                .build();

        UserRoleInGroup userRoleInGroup = userRoleInGroupRepository.findById(key)
                .orElse(null);

        if (userRoleInGroup != null) {
            Role role = userRoleInGroup.getRole();

            Authority savedAuthority = authorityRepository.findOrThrow(authority);

            return role.getAuthorities().contains(savedAuthority);
        }

        return false;

    }

    public boolean isAuthor(Long postId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findOrThrow(username);

        Post post = postRepository.findOrThrow(postId);

        return post.getUser().equals(user);
    }

    public boolean isAuthorOrManagePost(Long groupId, Long postId){
        return isAuthor(postId) || isHasPermission(groupId , "MANAGE_POST");
    }



}
