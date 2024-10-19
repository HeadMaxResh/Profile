package com.t1.profile.dto;

public class UserDto {
    private String name;
    private String email;
    private String password; // Добавляем поле для пароля


    public String getName() {
        return name;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}