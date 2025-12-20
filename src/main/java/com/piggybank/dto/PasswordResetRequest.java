package com.piggybank.dto;

import javax.validation.constraints.NotBlank;

public class PasswordResetRequest {
    @NotBlank
    private String token;
    
    @NotBlank
    private String newPassword;

    public PasswordResetRequest() {}

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}