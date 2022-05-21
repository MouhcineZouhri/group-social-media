package com.mohsin.group.init;

import com.mohsin.group.repositories.AuthorityRepository;
import com.mohsin.group.repositories.RoleRepository;
import com.mohsin.group.entities.Authority;
import com.mohsin.group.entities.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class AuthorityInitializer {

    private final AuthorityRepository authorityRepository;

    private final RoleRepository roleRepository;

    public  void init(){

//        String[] authorities = {"MANEGE_POST" , "MANAGE_MEMBERS"  , "MANAGE_ROLE" , "MANAGE_GROUP"};

        Map<String , String> maps = new HashMap<>();

        maps.put("MEMBER" , "CREATE NON APPROVE POST AND GET POSTS");

        maps.put("MANEGE_POST" ,"APPROVE POST AND DELETE POST");

        maps.put("MANAGE_MEMBERS" ,"answer request membership , make invitations , remove a member .");

        maps.put("MANAGE_ROLE" , " Create | DELETE | UPDATE  Custom Role , And attach them to  members .");

        maps.put("MANAGE_GROUP" , "Update Group info , Delete Group .");

        maps.forEach((key, value) -> {
            Authority authority = Authority.builder()
                    .name(key)
                    .description(value)
                    .build();

            authorityRepository.save(authority);
        });

        /*
            1 - MANAGE POST
                => APPROVE POST AND DELETE POST .

            2 - MANAGE MEMBERS :
                => answer request membership , make invitations , remove a member .

            3 - MANEGE ROLE :
                => Create | DELETE | UPDATE  Custom Role , And attach them to  members .

            4 - MANGE GROUP:
                => Update Group info , Delete Group .
         */

    }

    public void attachRole(){

        Role admin = roleRepository.findRoleByName("ADMIN")
                .orElseThrow(() -> new RuntimeException(""));

        authorityRepository.findAll()
                .forEach(authority -> {
                    admin.getAuthorities().add(authority);
                });

        roleRepository.save(admin);

        Role member = roleRepository.findRoleByName("MEMBER")
                .orElseThrow(() -> new RuntimeException(""));

        Authority authority = authorityRepository.findByName("MEMBER")
                .orElse(null);

        if(authority != null) member.getAuthorities().add(authority);

        roleRepository.save(member);
    }


}
