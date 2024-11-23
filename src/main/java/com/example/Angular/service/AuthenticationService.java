package com.example.Angular.service;

import com.example.Angular.Entity.Utilisateur;
import com.example.Angular.dto.JwtAuthenticationResponse;
import com.example.Angular.dto.RefreshTokenRequest;
import com.example.Angular.dto.SignInRequest;
import com.example.Angular.dto.SignUpRequest;

public interface AuthenticationService {

    Utilisateur signup(SignUpRequest signupRequest);

    JwtAuthenticationResponse signin(SignInRequest signInRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}