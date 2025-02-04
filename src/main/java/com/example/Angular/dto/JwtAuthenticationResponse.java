package com.example.Angular.dto;

import java.util.Date;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String token;
    private String refreshToken;
    private String Role;
    private boolean isEnabled;
    private boolean passwordstate;
    private Date token_expiration;
    private int id;
}