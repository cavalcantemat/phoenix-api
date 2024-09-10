package com.ecom.phoenix.controllers;

import com.ecom.phoenix.models.User;
import com.ecom.phoenix.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public User findById(@PathVariable String id) {
        return this.userService.findById(id);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> edit(@ModelAttribute User user) {
        try {
            userService.edit(user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
