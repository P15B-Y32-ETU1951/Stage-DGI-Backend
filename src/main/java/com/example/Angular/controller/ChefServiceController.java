package com.example.Angular.controller;

import java.time.LocalDateTime;

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
import com.example.Angular.Entity.Notif;
import com.example.Angular.Entity.Reclamation;
import com.example.Angular.Entity.Rejet;
import com.example.Angular.Entity.Statut;
import com.example.Angular.Entity.StatutDemande;
import com.example.Angular.Entity.Utilisateur;
import com.example.Angular.dto.DemandeRequest;
import com.example.Angular.dto.RejectRequest;
import com.example.Angular.dto.StatutDemandeRequest;
import com.example.Angular.repository.DemandeRepository;
import com.example.Angular.repository.NotifRepository;
import com.example.Angular.repository.ReclamationRepository;
import com.example.Angular.repository.StatutDemandeRepository;
import com.example.Angular.repository.UtilisateurRepository;
import com.example.Angular.service.DemandeService;
import com.example.Angular.service.RessourceService;
import com.example.Angular.service.StatutService;

@RestController
@RequestMapping("/api/v1/CHEF_SERVICE")
public class ChefServiceController {
    @Autowired
    private DemandeService demandeService;
    @Autowired
    StatutService statutService;
    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private StatutDemandeRepository statutDemandeRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RessourceService ressourceService;
    @Autowired
    private NotifRepository notifRepository;
    @Autowired
    private ReclamationRepository reclamationRepository;

    @PostMapping("/demande")
    public ResponseEntity<?> demande(@RequestBody DemandeRequest request) {
        Demande demande = demandeService.saveDemande(request);
        StatutDemande stdm = new StatutDemande();
        stdm.setDemande(demande);
        stdm.setStatut(statutService.getStatut(2));
        stdm.setDate_changement(LocalDateTime.now());
        statutDemandeRepository.save(stdm);
        return ResponseEntity.ok(demande);
    }

    @GetMapping("/demande/{id_statut}")
    public ResponseEntity<?> getDemandeByStatut(@PathVariable int id_statut) {

        return ResponseEntity.ok(demandeService.findAllDemandesByStatutAndService(id_statut));
    }

    @GetMapping("/demande/detail/{id}")
    public ResponseEntity<?> rechercheDemande(@PathVariable int id) {

        return ResponseEntity.ok(demandeService.findById(id));
    }

    @PostMapping("/demande/statut")
    public ResponseEntity<?> setStatut(@RequestBody StatutDemandeRequest request) {
        Statut statut = statutService.getStatut(request.getStatut());
        Demande demande = demandeService.findById(request.getId_demande());
        demande.setStatut(statut);
        demandeRepository.save(demande);
        StatutDemande stdm = new StatutDemande();
        stdm.setDemande(demande);
        stdm.setStatut(statut);
        stdm.setDate_changement(LocalDateTime.now());
        statutDemandeRepository.save(stdm);
        if (request.getStatut() == 2) {
            Notif notif = new Notif();
            notif.setDemande(demande);
            notif.setRecent(true);
            notifRepository.save(notif);
        }
        return ResponseEntity.ok(stdm);
    }

    @GetMapping("/demande/service")
    public ResponseEntity<?> findDemandeByUtilisateur() {
        String utilisateur = SecurityContextHolder.getContext().getAuthentication().getName();
        Utilisateur userinfo = utilisateurRepository.findByEmail(utilisateur).orElseThrow();

        return ResponseEntity.ok(demandeService.findByService(userinfo.getService().getId()));
    }

    @GetMapping("/ressource/travaux/{id}")
    public ResponseEntity<?> getRessourcesByTravaux(@PathVariable("id") int id_Travaux) {
        return ResponseEntity.ok(ressourceService.findAllByTravauxId(id_Travaux));
    }

    @PostMapping("/reclamation")
    public ResponseEntity<?> reclamation(@RequestBody RejectRequest request) {
        Reclamation reclamation = new Reclamation();
        Demande demande = demandeService.findById(request.getId_demande());
        reclamation.setDemande(demande);
        reclamation.setDate(LocalDateTime.now());
        reclamation.setMotif(request.getMotif());

        return ResponseEntity.ok(reclamationRepository.save(reclamation));

    }
}