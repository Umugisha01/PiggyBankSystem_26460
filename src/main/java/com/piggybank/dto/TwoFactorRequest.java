package com.piggybank.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class TwoFactorRequest {
    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    private String otp;

    public TwoFactorRequest() {}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }
}