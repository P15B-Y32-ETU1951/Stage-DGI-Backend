package com.example.Angular.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.Angular.Entity.Demande;
import com.example.Angular.Entity.Planification;
import com.example.Angular.Entity.Ressource;
import com.example.Angular.Entity.RessourceTravaux;
import com.example.Angular.Entity.Statut;
import com.example.Angular.Entity.Travaux;
import com.example.Angular.dto.PlanificationRequest;
import com.example.Angular.dto.RessourceDTO;
import com.example.Angular.repository.DemandeRepository;
import com.example.Angular.repository.PlanificationRepository;
import com.example.Angular.repository.RejetRepository;
import com.example.Angular.repository.RessourceRepository;
import com.example.Angular.repository.RessourceTravauxRepository;
import com.example.Angular.repository.StatutDemandeRepository;
import com.example.Angular.repository.StatutRepository;
import com.example.Angular.repository.TravauxRepository;

@Service
public class PlanificationService {

    @Autowired
    StatutService statutService;

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
    private DemandeService demandeService;

    @Autowired
    RessourceTravauxRepository ressourceTravauxRepository;

    @Autowired
    StatutRepository statutRepository;

    @Autowired
    RessourceRepository ressourceRepository;

    public Planification planification(@RequestBody PlanificationRequest request) {
        Demande demande = demandeService.findById(request.getId_demande());
        Planification planification = new Planification();
        planification.setDateDebut(request.getDateDebut());
        planification.setDateFin(request.getDateFin());
        planification.setDemande(demande);
        planificationRepository.save(planification);
        Travaux travaux = new Travaux();
        travaux.setDemande(demande);
        travaux.setTotal(request.getTotal());
        Travaux save = travauxRepository.save(travaux);
        List<RessourceTravaux> ressourceTravaux = new ArrayList<>();
        for (RessourceDTO ressourceDTO : request.getRessources()) {
            RessourceTravaux ressourceTravaux1 = new RessourceTravaux();
            ressourceTravaux1.setRessource(ressourceService.findRessourceById(ressourceDTO.getId_ressource()));
            ressourceTravaux1.setQuantite(ressourceDTO.getQuantite());
            ressourceTravaux1.setTravaux(save);
            ressourceTravaux.add(ressourceTravaux1);
            Ressource res = ressourceRepository.findById(ressourceDTO.getId_ressource()).get();
            if (res.getQuantite() == ressourceDTO.getQuantite()) {
                res.setDispo(false);
            }
            res.setQuantite(res.getQuantite() - ressourceDTO.getQuantite());
            ressourceRepository.save(res);
        }

        ressourceTravauxRepository.saveAll(ressourceTravaux);
        Statut statut = statutRepository.findById(6).get();
        demande.setStatut(statut);
        demandeRepository.save(demande);
        return planification;
    }

    public Planification reouverture(@RequestBody PlanificationRequest request) {
        Demande demande = demandeService.findById(request.getId_demande());
        Planification planification = demande.getPlanification();
        planification.setDateFin(request.getDateFin());
        demande.getTravaux().setTotal(request.getTotal() + demande.getTravaux().getTotal());
        System.out.println(demande.getTravaux().getTotal() + "total---------------------");
        List<RessourceTravaux> ressourceTravaux = new ArrayList<>();
        for (RessourceDTO ressourceDTO : request.getRessources()) {
            RessourceTravaux ressourceTravaux1 = new RessourceTravaux();
            ressourceTravaux1.setRessource(ressourceService.findRessourceById(ressourceDTO.getId_ressource()));
            ressourceTravaux1.setQuantite(ressourceDTO.getQuantite());
            ressourceTravaux1.setTravaux(demande.getTravaux());
            ressourceTravaux.add(ressourceTravaux1);
            Ressource res = ressourceRepository.findById(ressourceDTO.getId_ressource()).get();
            if (res.getQuantite() == ressourceDTO.getQuantite()) {
                res.setDispo(false);
            }
            res.setQuantite(res.getQuantite() - ressourceDTO.getQuantite());
            ressourceRepository.save(res);
        }

        ressourceTravauxRepository.saveAll(ressourceTravaux);
        Statut statut = statutRepository.findById(7).get();
        demande.setStatut(statut);
        demandeRepository.save(demande);
        return planification;
    }

}