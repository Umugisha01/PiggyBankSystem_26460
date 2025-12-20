package com.piggybank.dto;

public class AuthenticationResponse {
    private String token;
    private String refreshToken;
    private String type = "Bearer";
    private String email;
    private String role;
    private boolean requiresTwoFactor = false;
    
    public AuthenticationResponse() {}
    
    public AuthenticationResponse(String token, String email, String role) {
        this.token = token;
        this.email = email;
        this.role = role;
    }
    
    public AuthenticationResponse(String token, String refreshToken, String email, String role) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.email = email;
        this.role = role;
    }
    
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }

    public boolean isRequiresTwoFactor() { return requiresTwoFactor; }
    public void setRequiresTwoFactor(boolean requiresTwoFactor) { this.requiresTwoFactor = requiresTwoFactor; }
}