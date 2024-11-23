package com.example.Angular.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Angular.Entity.StatutDemande;
import java.util.List;

public interface StatutDemandeRepository extends JpaRepository<StatutDemande, Integer> {

    List<StatutDemande> findByDemandeIdAndStatutId(Integer demandeId, Integer statutId);

}