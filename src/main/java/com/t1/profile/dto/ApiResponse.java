package com.t1.profile.dto;

public class ApiResponse {
    private Boolean success;
    private String message;

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // Геттеры и сеттеры

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
