package com.example.Angular.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Angular.Entity.Ressource;
import com.example.Angular.Entity.RessourceTravaux;
import com.example.Angular.dto.ApprovisionDTO;
import com.example.Angular.repository.RessourceRepository;
import com.example.Angular.repository.RessourceTravauxRepository;

@Service
public class RessourceService {
    @Autowired
    RessourceRepository ressourceRepository;

    @Autowired
    RessourceTravauxRepository ressourceTravauxRepository;

    public Ressource saveRessource(Ressource ressource) {
        ressource.setDispo(true);
        return ressourceRepository.save(ressource);
    }

    public List<Ressource> findAllRessources() {
        return ressourceRepository.findAll();
    }

    public List<Ressource> findAllRessourcesDispo() {
        return ressourceRepository.findAllByDispo(true);
    }

    public Ressource findRessourceById(Integer id) {
        return ressourceRepository.findById(id).get();
    }

    public List<RessourceTravaux> findAllByTravauxId(int idTravaux) {
        return ressourceTravauxRepository.findAllByTravaux_Id(idTravaux);
    }

    public List<RessourceTravaux> findAllTravaux() {
        return ressourceTravauxRepository.findAll();
    }

    public Ressource Approvisionner(ApprovisionDTO approvisionDTO) {
        Ressource ressource = ressourceRepository.findById(approvisionDTO.getId_ressource()).get();
        ressource.setQuantite(ressource.getQuantite() + approvisionDTO.getQuantite());
        ressource.setDispo(true);
        return ressourceRepository.save(ressource);

    }
}