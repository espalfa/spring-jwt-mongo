package com.logistic.app.app.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.logistic.app.app.dtos.UserDto;
import com.logistic.app.app.models.User;
import com.logistic.app.app.repositories.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;
    private TokenService tokenService;

    @Autowired
    UserService(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> getUserByUsername(String userId) {
        Optional<User> response = userRepository.findUserByUsername(userId);
        return response;
    }

    public UserDto saveUser(UserDto user) {
        Optional<User> existingItemOptional = userRepository.findUserByUsername(user.getUsername());
        if (!existingItemOptional.isPresent()) {
            User newUser = new User(user.getUsername(), user.getPassword());
            userRepository.insert(newUser);
            Optional<User> userCreated = userRepository.findUserByUsername(newUser.getUsername());
            User created = userCreated.get();
            return new UserDto(created.getId(), created.getUsername(), created.getPassword());
        } else {
            return null;
        }
    }

    public String createToken(UserDto user) {
        return tokenService.createToken(user.getId());
    }
}