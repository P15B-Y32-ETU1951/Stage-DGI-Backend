package com.example.Angular.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
    private String captchaToken;

    // Getters et setters
}
