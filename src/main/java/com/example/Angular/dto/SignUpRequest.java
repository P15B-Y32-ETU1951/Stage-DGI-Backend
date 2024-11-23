package com.example.Angular.dto;

import com.example.Angular.Entity.Role;

import lombok.Data;

@Data
public class SignUpRequest {
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private int service;
    private Role role;
}