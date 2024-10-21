package com.t1.profile.dto;

public class LoginRequest {
    private String email;
    private String password;

    // Геттеры и сеттеры

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
}
