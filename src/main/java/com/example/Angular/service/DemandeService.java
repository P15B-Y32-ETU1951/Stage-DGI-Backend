package com.example.Angular.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.Angular.Entity.Demande;
import com.example.Angular.Entity.Notif;
import com.example.Angular.Entity.Services;
import com.example.Angular.Entity.Statut;
import com.example.Angular.Entity.Utilisateur;
import com.example.Angular.dto.DemandeRequest;
import com.example.Angular.repository.DemandeRepository;
import com.example.Angular.repository.NotifRepository;
import com.example.Angular.repository.ServiceRepository;
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
    UtilisateurRepository utilisateurRepository;
    @Autowired
    NotifRepository notifRepository;

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
        if (request.getId_statut() == 2) {
            Notif notif = new Notif();
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