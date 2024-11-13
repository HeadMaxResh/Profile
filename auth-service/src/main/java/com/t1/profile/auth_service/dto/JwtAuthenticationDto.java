package com.t1.profile.auth_service.dto;

import lombok.Data;

@Data
public class JwtAuthenticationDto {

    private String accessToken;
    private String tokenType = "Bearer";
    private String roleType;
    private Integer userId;

    public JwtAuthenticationDto(String accessToken, String roleType, Integer userId) {
        this.accessToken = accessToken;
        this.roleType = roleType;
        this.userId = userId;
    }

    public JwtAuthenticationDto(String accessToken) {
        this.accessToken = accessToken;
    }

}
