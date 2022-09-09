package com.logistic.app.app.dtos;

import lombok.Data;

@Data
public class UserDto {
    public String id;
    private String username;
    private String password;
    private String token;

    public UserDto(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public UserDto(String id, String username, String password, String token) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.token = token;
    }

    public UserDto() {
    }

    

}
