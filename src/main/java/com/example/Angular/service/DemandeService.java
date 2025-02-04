package com.example.Angular.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.Angular.Entity.Demande;
import com.example.Angular.Entity.Notif_CHEF_SERVICE;
import com.example.Angular.Entity.Notif_DPR_SAF;
import com.example.Angular.Entity.Services;
import com.example.Angular.Entity.Statut;
import com.example.Angular.Entity.StatutDemande;
import com.example.Angular.Entity.Utilisateur;
import com.example.Angular.dto.DemandeRequest;
import com.example.Angular.repository.DemandeRepository;
import com.example.Angular.repository.NotifChefServiceRepository;
import com.example.Angular.repository.NotifRepository;
import com.example.Angular.repository.ServiceRepository;
import com.example.Angular.repository.StatutDemandeRepository;
import com.example.Angular.repository.StatutRepository;
import com.example.Angular.repository.UtilisateurRepository;

@Service
public class DemandeService {
    @Autowired
    DemandeRepository demoRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    StatutRepository statutRepository;

    @Autowired
    StatutDemandeRepository statutDemandeRepository;

    @Autowired
    UtilisateurRepository utilisateurRepository;
    @Autowired
    NotifRepository notifRepository;

    @Autowired
    NotifChefServiceRepository notifChefServiceRepository;

    public Demande saveDemande(DemandeRequest request) {
        Demande demande = new Demande();
        demande.setDate(request.getDate());
        demande.setDescription(request.getDescription());
        demande.setMotif(request.getMotif());
        String utilisateur = SecurityContextHolder.getContext().getAuthentication().getName();
        Utilisateur user = utilisateurRepository.findByEmail(utilisateur).orElseThrow();
        Services services = serviceRepository.findById(user.getService().getId()).get();
        demande.setService(services);
        Statut statut = statutRepository.findById(request.getId_statut()).get();
        demande.setStatut(statut);
        demande.setUtilisateur(user);
        Demande d = demoRepository.save(demande);
        StatutDemande statutDemande = new StatutDemande();
        statutDemande.setDemande(d);
        statutDemande.setStatut(statut);
        statutDemande.setDate_changement(LocalDateTime.now());
        statutDemandeRepository.save(statutDemande);
        if (request.getId_statut() == 1) {
            Notif_CHEF_SERVICE notif = new Notif_CHEF_SERVICE();
            notif.setDemande(d);
            notif.setRecent(true);
            notifChefServiceRepository.save(notif);
        }
        if (request.getId_statut() == 2) {
            Notif_DPR_SAF notif = new Notif_DPR_SAF();
            notif.setDemande(d);
            notif.setRecent(true);
            notifRepository.save(notif);
        }
        return d;

    }

    public List<Demande> findAllDemandesByStatutAndService(int idStatut) {
        String utilisateur = SecurityContextHolder.getContext().getAuthentication().getName();
        Utilisateur user = utilisateurRepository.findByEmail(utilisateur).orElseThrow();
        return demoRepository.findAllByStatut_IdAndService_Id(idStatut, user.getService().getId());

    }

    public List<Demande> findAllDemandesByStatut(int idStatut) {
        return demoRepository.findAllByStatut_Id(idStatut);

    }

    public Demande findById(int id) {
        return demoRepository.findById(id).orElseThrow();
    }

    public List<Demande> findByUtilisateur(int id) {
        return demoRepository.findByUtilisateur_Id(id);
    }

    public List<Demande> findByService(int id) {
        return demoRepository.findByService_Id(id);
    }
}