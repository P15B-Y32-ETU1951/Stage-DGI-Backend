package com.example.Angular.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Angular.Entity.Rejet;

@Repository
public interface RejetRepository extends JpaRepository<Rejet, Integer> {
    Rejet findByDemande_Id(int iddemande);
}