package com.workloom.workloom.dto;

public class AuthResponse {
    private String token;
    private String role;  // User role name for frontend convenience

    public AuthResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }
}
