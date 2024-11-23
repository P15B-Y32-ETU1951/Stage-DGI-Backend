package com.example.Angular.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Angular.Entity.Demande;

@Repository
public interface DemandeRepository extends JpaRepository<Demande, Integer> {

    List<Demande> findAllByStatut_Id(int idStatut);

    List<Demande> findAllByStatut_IdAndService_Id(int idStatut, int idService);

    List<Demande> findByUtilisateur_Id(int idUtilisateur);

    List<Demande> findByService_Id(int idService);

}