package com.example.scim_cam;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.scim_cam.security.TokenRetriever;
import com.example.scim_cam.utils.JwtUtil;

@SpringBootApplication
public class ScimCamApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScimCamApplication.class, args);

        String token = TokenRetriever.fetchToken();
        System.out.println("Token: " + token);
    }


}
