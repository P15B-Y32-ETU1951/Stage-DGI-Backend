package com.example.Angular.dto;

import lombok.Data;

@Data
public class SignInRequest {
    private String email;
    private String password;
}