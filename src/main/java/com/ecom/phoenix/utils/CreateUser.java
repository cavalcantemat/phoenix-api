package com.ecom.phoenix.utils;

import com.ecom.phoenix.controllers.AuthenticationController;
import com.ecom.phoenix.dtos.RegisterDto;
import com.ecom.phoenix.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CreateUser implements CommandLineRunner {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationController authenticationController;

    public static void main(String[] args) {
        SpringApplication.run(CreateUser.class, args);
    }

    @Override
    public void run(String... args) {
        if (this.userService.findByLogin("admin") == null) {
            //enter a password to create a sysadmin to test locally
            String password = "";
            this.authenticationController.create(new RegisterDto("admin","admin","admin@gmail.com" , password, UserRole.ADMIN));
        }
    }
}
