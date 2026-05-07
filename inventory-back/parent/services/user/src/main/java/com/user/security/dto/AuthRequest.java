package com.user.security.dto;

//after creating the micro service that will manage all jwt requests, 2 dto's must be created, first for request and second for response
public class AuthRequest {

    private String email;
    private String password;


    public AuthRequest () {}

    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}