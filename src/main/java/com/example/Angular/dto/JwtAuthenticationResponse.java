package com.example.Angular.dto;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String token;
    private String refreshToken;
    private String Role;
    private boolean isEnabled;
    private boolean passwordstate;
    private int id;
}