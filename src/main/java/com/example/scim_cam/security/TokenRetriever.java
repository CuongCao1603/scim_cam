package com.example.scim_cam.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.scim_cam.model.YourRequest;

public class TokenRetriever {
    public static String fetchToken() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://your-authentication-api-url";
        HttpEntity<YourRequest> request = new HttpEntity<>(new YourRequest());
        return url;
    }
}
