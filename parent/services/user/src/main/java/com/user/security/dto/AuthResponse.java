package com.user.security.dto;

//this DTO will contain the token response
public class AuthResponse {

    private String token;

    public AuthResponse (String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}