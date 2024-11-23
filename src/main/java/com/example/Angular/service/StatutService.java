package com.example.Angular.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Angular.Entity.Statut;
import com.example.Angular.Entity.StatutDemande;
import com.example.Angular.repository.StatutDemandeRepository;
import com.example.Angular.repository.StatutRepository;

@Service
public class StatutService {

    @Autowired
    StatutRepository statutRepository;

    @Autowired
    StatutDemandeRepository statutDemandeRepository;

    public Statut saveStatut(Statut statut) {
        return statutRepository.save(statut);
    }

    public Statut getStatut(int id) {
        return statutRepository.findById(id).get();
    }

    public List<Statut> getAllStatut() {
        return statutRepository.findAll();
    }

    public StatutDemande saveStatutDemande(StatutDemande statutDemande) {
        return statutDemandeRepository.save(statutDemande);
    }

    public List<StatutDemande> getStatutDemandeByDemandeId(int idDemande, int id_statut) {
        return statutDemandeRepository.findByDemandeIdAndStatutId(idDemande, id_statut);
    }

}