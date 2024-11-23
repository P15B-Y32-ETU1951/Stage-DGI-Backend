package com.example.Angular.controller;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Angular.Entity.Demande;
import com.example.Angular.Entity.Notif;
import com.example.Angular.Entity.Planification;
import com.example.Angular.Entity.Rejet;
import com.example.Angular.Entity.Ressource;
import com.example.Angular.Entity.RessourceTravaux;
import com.example.Angular.Entity.Statut;
import com.example.Angular.Entity.StatutDemande;
import com.example.Angular.Entity.Travaux;
import com.example.Angular.dto.ApprovisionDTO;
import com.example.Angular.dto.PlanificationRequest;
import com.example.Angular.dto.RejectRequest;
import com.example.Angular.dto.RessourceDTO;
import com.example.Angular.dto.StatutDemandeRequest;
import com.example.Angular.repository.DemandeRepository;
import com.example.Angular.repository.PlanificationRepository;
import com.example.Angular.repository.RejetRepository;
import com.example.Angular.repository.RessourceTravauxRepository;
import com.example.Angular.repository.StatutDemandeRepository;
import com.example.Angular.repository.TravauxRepository;
import com.example.Angular.service.DemandeService;
import com.example.Angular.service.NotifService;
import com.example.Angular.service.PlanificationService;
import com.example.Angular.service.RessourceService;
import com.example.Angular.service.StatutService;

@RestController
@RequestMapping("api/v1/DPR_SAF")
public class DPRController {

    @Autowired
    StatutService statutService;
    @Autowired
    PlanificationService planificationService;

    @Autowired
    private StatutDemandeRepository statutDemandeRepository;

    @Autowired
    private DemandeRepository demandeRepository;
    @Autowired
    private RejetRepository rejetRepository;

    @Autowired
    private RessourceService ressourceService;

    @Autowired
    private PlanificationRepository planificationRepository;

    @Autowired
    private TravauxRepository travauxRepository;

    @Autowired
    RessourceTravauxRepository ressourceTravauxRepository;

    @Autowired
    NotifService notifService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hi admin!");
    }

    @PostMapping("/statut")
    public ResponseEntity<Statut> saveStatut(@RequestBody Statut statut) {

        return ResponseEntity.ok(statutService.saveStatut(statut));
    }

    @GetMapping("/statut")
    public ResponseEntity<?> getAllStatut() {

        return ResponseEntity.ok(statutService.getAllStatut());
    }

    @Autowired
    private DemandeService demandeService;

    @GetMapping("/demande/{id_statut}")
    public ResponseEntity<?> getDemande(@PathVariable int id_statut) {

        return ResponseEntity.ok(demandeService.findAllDemandesByStatut(id_statut));
    }

    @GetMapping("/demande/termine")
    public ResponseEntity<?> getDemande() {
        List<Demande> demandes = new ArrayList<>();
        demandes.addAll(demandeService.findAllDemandesByStatut(8));
        demandes.addAll(demandeService.findAllDemandesByStatut(10));
        return ResponseEntity.ok(demandes);
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
        if (statut.getId() == 7) {
            demande.getPlanification().setDateDebut(LocalDate.now());

        }
        if (statut.getId() == 8) {
            demande.getPlanification().setDateFin(LocalDate.now());

        }
        demandeRepository.save(demande);
        StatutDemande stdm = new StatutDemande();
        stdm.setDemande(demande);
        stdm.setStatut(statut);
        stdm.setDate_changement(LocalDateTime.now());
        statutDemandeRepository.save(stdm);
        return ResponseEntity.ok(stdm);
    }

    @PostMapping("/rejet")
    public ResponseEntity<?> reject(@RequestBody RejectRequest request) {
        Rejet rejet = new Rejet();
        Demande demande = demandeService.findById(request.getId_demande());
        rejet.setDemande(demande);
        rejet.setDate(LocalDateTime.now());
        rejet.setMotif(request.getMotif());

        return ResponseEntity.ok(rejetRepository.save(rejet));

    }

    @PostMapping("/ressource")
    public ResponseEntity<?> addRessource(@RequestBody Ressource ressource) {
        return ResponseEntity.ok(ressourceService.saveRessource(ressource));
    }

    @PostMapping("/ressource/approvisionner")
    public ResponseEntity<?> approvisionnerRessource(@RequestBody ApprovisionDTO approvisionDTO) {
        return ResponseEntity.ok(ressourceService.Approvisionner(approvisionDTO));
    }

    @GetMapping("/ressource")
    public ResponseEntity<?> getAllRessources() {
        return ResponseEntity.ok(ressourceService.findAllRessources());
    }

    @GetMapping("/ressource/{id}")
    public ResponseEntity<?> getRessourceById(@PathVariable int id) {
        return ResponseEntity.ok(ressourceService.findRessourceById(id));
    }

    @GetMapping("/ressource/dispo")
    public ResponseEntity<?> getAllRessourcesDispo() {
        return ResponseEntity.ok(ressourceService.findAllRessourcesDispo());
    }

    @GetMapping("/ressource/travaux/{id}")
    public ResponseEntity<?> getRessourcesByTravaux(@PathVariable("id") int id_Travaux) {
        return ResponseEntity.ok(ressourceService.findAllByTravauxId(id_Travaux));
    }

    @GetMapping("/ressource/travaux")
    public ResponseEntity<?> getAllRessourcesTravaux() {
        return ResponseEntity.ok(ressourceService.findAllTravaux());
    }

    @PostMapping("/planification")
    public ResponseEntity<?> planification(@RequestBody PlanificationRequest request) {
        Planification plan = planificationService.planification(request);

        return ResponseEntity.ok(plan);
    }

    @PostMapping("/reouverture")
    public ResponseEntity<?> reouverture(@RequestBody PlanificationRequest request) {
        Planification reouverture = planificationService.reouverture(request);

        return ResponseEntity.ok(reouverture);
    }

    @GetMapping("/notif")
    public ResponseEntity<?> notif() {
        List<Notif> notifs = notifService.getNewNotifs();
        return ResponseEntity.ok(notifs);
    }

    @PostMapping("/notif/seen")
    public ResponseEntity<?> seenNotif(@RequestBody List<Integer> notifs) {
        notifService.setSeen(notifs);
        return ResponseEntity.ok("Notifs seen");
    }

    @GetMapping("/statut/demande/{id_demande}/{id_statut}")
    public ResponseEntity<?> getStatutdemande(@PathVariable int id_demande, @PathVariable int id_statut) {
        return ResponseEntity.ok(statutService.getStatutDemandeByDemandeId(id_demande, id_statut));
    }

    @GetMapping("/statistique/demande")
    public ResponseEntity<?> getStatistiqueDemande() {
        return ResponseEntity.ok(demandeRepository.findAll());
    }

}