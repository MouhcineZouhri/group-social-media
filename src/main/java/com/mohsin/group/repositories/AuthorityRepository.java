package com.mohsin.group.repositories;

import com.mohsin.group.entities.Authority;
import com.mohsin.group.exceptions.AuthorityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority , Long> {

    Optional<Authority> findByName(String name);

    default Authority findOrThrow(String name){
        return findByName(name)
                    .orElseThrow(() -> new AuthorityNotFoundException(name));
    }

}
