package com.logistic.app.app.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpInc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logistic.app.app.dtos.UserDto;
import com.logistic.app.app.models.User;
import com.logistic.app.app.repositories.UserRepository;
import com.logistic.app.app.services.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> regiterUser(@RequestBody UserDto user) {
        
        UserDto registeredUser = userService.saveUser(user);
        if (registeredUser == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        registeredUser.setPassword("no password for you");
        registeredUser.setToken(userService.createToken(user));
        return new ResponseEntity<>(registeredUser, HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<UserDto> getById(@RequestBody UserDto user) {
        Optional<User> userFound = userService.getUserByUsername(user.getUsername());
        if (userFound.isPresent()) {
            User found = userFound.get();
            if(found.getPassword().equals(user.getPassword())){
                UserDto userDto = new UserDto(found.getId(), found.getUsername(), found.getPassword());
                userDto.setToken(userService.createToken(user));
                return new ResponseEntity<>(userDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
