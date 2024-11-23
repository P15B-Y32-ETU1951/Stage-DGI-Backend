package com.example.Angular.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.token.Token;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Angular.Entity.ConfirmationToken;
import com.example.Angular.Entity.Services;
import com.example.Angular.Entity.Utilisateur;
import com.example.Angular.dto.JwtAuthenticationResponse;
import com.example.Angular.dto.RefreshTokenRequest;
import com.example.Angular.dto.SignInRequest;
import com.example.Angular.dto.SignUpRequest;
import com.example.Angular.repository.ConfirmationTokenRepository;
import com.example.Angular.repository.ServiceRepository;
import com.example.Angular.repository.UtilisateurRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UtilisateurRepository utilisateurRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtservice;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final EmailService emailService;

    private final ServiceRepository serviceRepository;

    public Utilisateur signup(SignUpRequest signUpRequest) {
        try {
            Optional<Utilisateur> login = utilisateurRepository.findByEmail(signUpRequest.getEmail());
            if (login.isPresent()) {
                throw new IllegalArgumentException("Email already in use");
            }
            Utilisateur utilisateur = new Utilisateur();

            utilisateur.setEmail(signUpRequest.getEmail());
            utilisateur.setNom(signUpRequest.getNom());
            utilisateur.setPrenom(signUpRequest.getPrenom());
            utilisateur.setRole(signUpRequest.getRole());
            utilisateur.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            Services services = serviceRepository.findById(signUpRequest.getService()).get();
            utilisateur.setService(services);
            utilisateurRepository.save(utilisateur);
            // sendValidationEmail(utilisateur);
            String jwt = jwtservice.generateToken(utilisateur);
            ConfirmationToken confirmationToken = new ConfirmationToken();
            confirmationToken.setToken(jwt);
            confirmationToken.setUtilisateur(utilisateur);
            confirmationToken.setCreatedDate(LocalDateTime.now());
            confirmationTokenRepository.save(confirmationToken);

            emailService.sendSimpleEmail(utilisateur.getEmail(), "Verification de compte",
                    "cliquez sur ce lien pour confirmer votre compte :http://localhost:8080/api/v1/auth/confirm?token="
                            + jwt);
            return utilisateurRepository.save(utilisateur);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public JwtAuthenticationResponse signin(SignInRequest signInRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
        var user = utilisateurRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        var jwt = jwtservice.generateToken(user);
        var refreshToken = jwtservice.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        jwtAuthenticationResponse.setRole(user.getRole().name());
        jwtAuthenticationResponse.setEnabled(user.isEnabled());
        return jwtAuthenticationResponse;

    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = jwtservice.extractUsername(refreshTokenRequest.getToken());
        Utilisateur user = utilisateurRepository.findByEmail(userEmail).orElseThrow();
        if (jwtservice.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtservice.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;

        }
        return null;

    }

    /*
     * public void sendValidationEmail(Utilisateur utilisateur) {
     * var newtoken = generateAndSaveActivationToken(utilisateur);
     * }
     * 
     * private String generateAndSaveActivationToken(Utilisateur utilisateur) {
     * String generatedToken = generateActivationCode(6);
     * var token= Jwts.builder()
     * 
     * .setSubject(utilisateur.getEmail())
     * .setIssuedAt(new Date(System.currentTimeMillis()))
     * .setExpiration(new Date(System.currentTimeMillis() + 604800000))
     * .signWith(jwtservice. getSigningKey(), SignatureAlgorithm.HS256)
     * .compact();
     * return null; // replace with actual implementation
     * }
     * 
     * private String generateActivationCode(int length) {
     * String characters = "0123456789";
     * StringBuilder codeBuilder = new StringBuilder();
     * SecureRandom secureRandom = new SecureRandom();
     * for (int i = 0; i < length; i++) {
     * int randomIndex = secureRandom.nextInt(characters.length());
     * codeBuilder.append(characters.charAt(randomIndex));
     * }
     * return codeBuilder.toString();
     * }
     */

}