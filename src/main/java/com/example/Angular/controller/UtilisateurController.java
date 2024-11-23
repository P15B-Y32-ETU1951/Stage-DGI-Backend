package com.example.Angular.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Angular.Entity.Utilisateur;
import com.example.Angular.repository.UtilisateurRepository;

@RestController
@RequestMapping("/api/v1/CHEF_SERVICE")
@CrossOrigin("*")
public class UtilisateurController {

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello steel!");
    }

    @GetMapping("/userinfo")
    public ResponseEntity<Utilisateur> userInfo() {
        String utilisateur = SecurityContextHolder.getContext().getAuthentication().getName();
        Utilisateur userinfo = utilisateurRepository.findByEmail(utilisateur).orElseThrow();
        return ResponseEntity.ok(userinfo);
    }
}