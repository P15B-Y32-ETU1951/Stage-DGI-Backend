package com.example.Angular.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Angular.Entity.ConfirmationToken;
import com.example.Angular.Entity.PasswordValidator;
import com.example.Angular.Entity.Role;
import com.example.Angular.Entity.Services;
import com.example.Angular.Entity.Utilisateur;
import com.example.Angular.dto.ForgotPasswordRequest;
import com.example.Angular.dto.JwtAuthenticationResponse;
import com.example.Angular.dto.PasswordWordRequest;
import com.example.Angular.dto.RefreshTokenRequest;
import com.example.Angular.dto.SignInRequest;
import com.example.Angular.dto.SignUpRequest;
import com.example.Angular.repository.ConfirmationTokenRepository;
import com.example.Angular.repository.ServiceRepository;
import com.example.Angular.repository.UtilisateurRepository;

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
    private final CaptchaService captchaService;

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
            if (signUpRequest.getService() == 3) {
                utilisateur.setRole(Role.DPR_SAF);
            }
            System.out.println(PasswordValidator.isPasswordComplex(signUpRequest.getPassword()) + ",,,. "
                    + PasswordValidator.isPredictable(signUpRequest.getPassword()));
            if (PasswordValidator.isPasswordComplex(signUpRequest.getPassword()) == true
                    && PasswordValidator.isPredictable(signUpRequest.getPassword()) == false) {
                utilisateur.updatePassword(passwordEncoder.encode(signUpRequest.getPassword()));
            } else {
                System.err.println("Invalid password");
                return null;
            }

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

    public Utilisateur changepassword(PasswordWordRequest signUpRequest) {
        // Vérifie si un utilisateur avec cet email existe déjà
        Utilisateur user = utilisateurRepository.findById(signUpRequest.getId()).get();
        user.updatePassword(passwordEncoder.encode(signUpRequest.getPassword()));
        return utilisateurRepository.save(user);
    }

    public Utilisateur forgotpassword(ForgotPasswordRequest signUpRequest) {

        Utilisateur user = utilisateurRepository.findByEmail(signUpRequest.getEmail()).get();
        if (user != null) {
            try {
                emailService.sendSimpleEmail(user.getEmail(), "Mot de passe oublie",
                        "cliquez sur ce lien pour changer votre mot de passe http://localhost:3000/auth/change-password/"
                                + user.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return user;

    }

    public JwtAuthenticationResponse signin(SignInRequest signInRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
        var user = utilisateurRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        boolean isCaptchaValid = captchaService.validateCaptcha(signInRequest.getCaptcha());
        System.out.println("isCaptchaValid????????????????" + isCaptchaValid);
        if (!isCaptchaValid) {
            System.out.println(signInRequest.getCaptcha());
            System.out.println("isCaptchaValid????????????????" + isCaptchaValid);
            System.err.println("Invalid captcha");
        }

        // Exemple de logique d'authentification (à adapter selon vos besoins)
        // Vérification si le mot de passe a expiré
        if (user.getPasswordExpiryDate() != null && user.getPasswordExpiryDate().isBefore(LocalDate.now())) {
            // Lancer une exception ou retourner une réponse spécifique
            var jwt = jwtservice.generateToken(user);
            var refreshToken = jwtservice.generateRefreshToken(new HashMap<>(), user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshToken);
            jwtAuthenticationResponse.setRole(user.getRole().name());
            jwtAuthenticationResponse.setEnabled(user.isEnabled());
            jwtAuthenticationResponse.setPasswordstate(true);
            jwtAuthenticationResponse.setId(user.getId());
            System.out.println("mdp expired +++++++++++++++++");
            return jwtAuthenticationResponse;
        }

        var jwt = jwtservice.generateToken(user);
        var refreshToken = jwtservice.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setToken_expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7));
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        jwtAuthenticationResponse.setRole(user.getRole().name());
        jwtAuthenticationResponse.setEnabled(user.isEnabled());
        jwtAuthenticationResponse.setPasswordstate(false);
        System.out.println("mdp not expired +++++++++++++++++");
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