package com.example.Angular.controller;

import java.security.Provider.Service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Angular.Entity.ConfirmationToken;
import com.example.Angular.Entity.InvalidToken;
import com.example.Angular.Entity.Ressource;
import com.example.Angular.Entity.Services;
import com.example.Angular.Entity.Utilisateur;
import com.example.Angular.dto.ForgotPasswordRequest;
import com.example.Angular.dto.JwtAuthenticationResponse;
import com.example.Angular.dto.PasswordWordRequest;
import com.example.Angular.dto.RefreshTokenRequest;
import com.example.Angular.dto.SignInRequest;
import com.example.Angular.dto.SignUpRequest;
import com.example.Angular.repository.ConfirmationTokenRepository;
import com.example.Angular.repository.InvalidTokenJPA;
import com.example.Angular.repository.ServiceRepository;
import com.example.Angular.repository.UtilisateurRepository;
import com.example.Angular.service.AuthenticationService;
import com.example.Angular.service.RessourceService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("*")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private RessourceService ressourceService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {
        // Vérifie si un utilisateur avec cet email existe déjà
        Optional<Utilisateur> user = utilisateurRepository.findByEmail(signUpRequest.getEmail());

        // Si l'utilisateur existe, renvoyer une erreur 409 (Conflict)
        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
        }

        // Si l'email n'est pas utilisé, procéder à l'inscription
        Utilisateur newUser = authenticationService.signup(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changepassword(@RequestBody PasswordWordRequest signUpRequest) {
        // Vérifie si un utilisateur avec cet email existe déjà
        Utilisateur user = authenticationService.changepassword(signUpRequest);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<?> forgotpassword(@RequestBody ForgotPasswordRequest signUpRequest) {
        // Vérifie si un utilisateur avec cet email existe déjà
        Utilisateur user = authenticationService.forgotpassword(signUpRequest);
        if (user != null) {
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");

    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello !");
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SignInRequest signInRequest) {
        if (!authenticationService.signin(signInRequest).isEnabled()) {
            return ResponseEntity.status(401).body("Utilisateur désactivé");

        }
        return ResponseEntity.ok(authenticationService.signin(signInRequest));

    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirm(@RequestParam("token") String token) {
        ConfirmationToken confirmation = confirmationTokenRepository.findByToken(token).get();
        Utilisateur user = confirmation.getUtilisateur();
        user.setEnabled(true);
        utilisateurRepository.save(user);
        return ResponseEntity.ok("Confirmation réussi!");
    }

    @Autowired
    private InvalidTokenJPA invalidTokenJPA;

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Supprime le préfixe "Bearer "

            // Ajouter le token dans la base de données des tokens invalides
            InvalidToken invalidToken = new InvalidToken(token);
            invalidTokenJPA.save(invalidToken);

            return ResponseEntity.ok("Déconnexion réussie");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token manquant ou invalide");
        }
    }

    @GetMapping("/services")
    public ResponseEntity<List<Services>> getAllservices() {
        return ResponseEntity.ok(serviceRepository.findAll());
    }

    @GetMapping("/userinfo")
    public ResponseEntity<Utilisateur> userInfo() {
        String utilisateur = SecurityContextHolder.getContext().getAuthentication().getName();
        Utilisateur userinfo = utilisateurRepository.findByEmail(utilisateur).orElseThrow();
        return ResponseEntity.ok(userinfo);
    }

    @PostMapping("/ressource")
    public ResponseEntity<?> addRessource(@RequestBody Ressource ressource) {
        return ResponseEntity.ok(ressourceService.saveRessource(ressource));
    }

}
