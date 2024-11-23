package com.example.Angular.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Angular.Entity.Rapport;

@Repository
public interface RapportRepository extends JpaRepository<Rapport, Integer> {
    Optional<Rapport> findByDemande_Id(int iddemande);
}