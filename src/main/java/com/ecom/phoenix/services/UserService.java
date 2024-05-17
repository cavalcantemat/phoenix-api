package com.ecom.phoenix.services;

import com.ecom.phoenix.models.User;
import com.ecom.phoenix.repositories.UserRepository;
import com.ecom.phoenix.utils.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User findById(String id) {
        return this.userRepository.findById(id);
    }

    public User findByLogin(String login) {
        return (User) this.userRepository.findByLogin(login);
    }

    public User register(User user) {
        return this.userRepository.save(user);
    }

    @Transactional
    public User edit(String id, User newUser) {
        User user = userRepository.findById(id);

        if (user != null) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(newUser.getPassword());

            user.setLogin(newUser.getLogin());
            user.setPassword(encryptedPassword);

            return userRepository.save(user);
        }
        
        throw new ResourceNotFoundException("User not found: " + id);
    }
}
