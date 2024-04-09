package com.example.scim_cam.model;

public class YourResponse {
    private String token;
    private long expiresIn;

    // Constructors, getters, and setters
    public YourResponse() {}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
