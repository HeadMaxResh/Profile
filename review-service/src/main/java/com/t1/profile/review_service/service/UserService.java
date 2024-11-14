package com.t1.profile.review_service.service;

import com.t1.profile.review_service.dto.UserDetailsDto;
import com.t1.profile.review_service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class UserService {

    private final RestTemplate restTemplate;

    @Value("${user-profile-service.url}")
    private String userProfileServiceUrl;

    @Autowired
    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserDto getUserById(Integer userId) {
        String url = userProfileServiceUrl + userId;
        return restTemplate.getForObject(url, UserDto.class);
    }

    public UserDetailsDto getByEmail(String email) {
        String url = UriComponentsBuilder.fromHttpUrl(userProfileServiceUrl)
                .path("/users/by-email")
                .queryParam("email", email)
                .toUriString();
        return restTemplate.getForObject(url, UserDetailsDto.class);
    }

}
