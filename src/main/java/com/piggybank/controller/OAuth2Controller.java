package com.piggybank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth2")
public class OAuth2Controller {

    @GetMapping("/success")
    public String oauth2Success() {
        return "OAuth2 login successful! You can close this window.";
    }

    @GetMapping("/failure")
    public String oauth2Failure() {
        return "OAuth2 login failed. Please try again.";
    }
}