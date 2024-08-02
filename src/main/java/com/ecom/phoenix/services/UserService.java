package com.ecom.phoenix.services;

import com.ecom.phoenix.infra.security.TokenService;
import com.ecom.phoenix.models.User;
import com.ecom.phoenix.repositories.UserRepository;
import com.ecom.phoenix.utils.ResourceNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    public User findById(String id) {
        return this.userRepository.findById(id);
    }

    public User findByLogin(String login) {
        return (User) this.userRepository.findByLogin(login);
    }

    @Transactional
    public void edit(User newUser) {
        User user = userRepository.findById(newUser.getId());

        if (user != null) {
            user.setLogin(newUser.getLogin());
            user.setName(newUser.getName());
            user.setEmail(newUser.getEmail());

            if (newUser.getPassword() != null) {
                String encryptedPassword = new BCryptPasswordEncoder().encode(newUser.getPassword());
                user.setPassword(encryptedPassword);
            }

            userRepository.save(user);
            return;
        }
        
        throw new ResourceNotFoundException("User not found: " + newUser.getId());
    }

    public User userLogged(HttpServletRequest request) {
        String token = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("access_token")) {
                    token = cookie.getValue();
                }
            }
        }

        String login = tokenService.validateToken(token);
        return findByLogin(login);
    }
}
