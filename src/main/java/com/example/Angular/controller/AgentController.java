package com.example.Angular.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Angular.Entity.Demande;
import com.example.Angular.Entity.Rejet;
import com.example.Angular.Entity.Utilisateur;
import com.example.Angular.dto.DemandeRequest;
import com.example.Angular.repository.RejetRepository;
import com.example.Angular.repository.UtilisateurRepository;
import com.example.Angular.service.DemandeService;
import com.example.Angular.service.RessourceService;

@RestController
@RequestMapping("/api/v1/AGENT")
public class AgentController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired

    private DemandeService demandeService;

    @Autowired
    private RejetRepository rejetRepository;

    @Autowired
    private RessourceService ressourceService;

    @GetMapping("/userinfo")
    public ResponseEntity<Utilisateur> userInfo() {
        String utilisateur = SecurityContextHolder.getContext().getAuthentication().getName();
        Utilisateur userinfo = utilisateurRepository.findByEmail(utilisateur).orElseThrow();
        return ResponseEntity.ok(userinfo);
    }

    @PostMapping("/demande")
    public ResponseEntity<?> demande(@RequestBody DemandeRequest request) {

        return ResponseEntity.ok(demandeService.saveDemande(request));
    }

    @GetMapping("/demande/user")
    public ResponseEntity<?> findDemandeByUtilisateur() {
        String utilisateur = SecurityContextHolder.getContext().getAuthentication().getName();
        Utilisateur userinfo = utilisateurRepository.findByEmail(utilisateur).orElseThrow();

        return ResponseEntity.ok(demandeService.findByUtilisateur(userinfo.getId()));
    }

    @GetMapping("/demande/detail/{id}")
    public ResponseEntity<?> rechercheDemande(@PathVariable int id) {
        Demande demande = demandeService.findById(id);
        return ResponseEntity.ok(demande);
    }

    @GetMapping("/rejet/{id}")
    public ResponseEntity<?> getRejet(@PathVariable int id) {
        Rejet rejet = rejetRepository.findByDemande_Id(id);
        return ResponseEntity.ok(rejet);
    }

    @GetMapping("/ressource/travaux/{id}")
    public ResponseEntity<?> getRessourcesByTravaux(@PathVariable("id") int id_Travaux) {
        return ResponseEntity.ok(ressourceService.findAllByTravauxId(id_Travaux));
    }

}