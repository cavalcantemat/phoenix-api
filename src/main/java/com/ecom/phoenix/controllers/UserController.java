package com.ecom.phoenix.controllers;

import com.ecom.phoenix.models.User;
import com.ecom.phoenix.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public User findById(@PathVariable String id) {
        return this.userService.findById(id);
    }

    @PostMapping("/create")
    public User register(@RequestBody User user) {
        return this.userService.register(user);
    }

    @PostMapping("/{id}")
    public User edit(@PathVariable String id, @RequestBody User user) {
        return this.userService.edit(id, user);
    }
}
