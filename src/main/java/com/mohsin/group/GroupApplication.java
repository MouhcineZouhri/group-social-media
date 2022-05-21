package com.mohsin.group;

import com.mohsin.group.init.AuthorityInitializer;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GroupApplication {

    public static void main(String[] args) {
        SpringApplication.run(GroupApplication.class, args);
    }


    //@Bean
    public ApplicationRunner runner(AuthorityInitializer initializer){
        return args -> {

            initializer.init();

            initializer.attachRole();
        };
    }
}
