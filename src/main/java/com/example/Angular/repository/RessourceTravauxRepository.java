package com.example.Angular.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Angular.Entity.RessourceTravaux;

public interface RessourceTravauxRepository extends JpaRepository<RessourceTravaux, Integer> {

    List<RessourceTravaux> findAllByTravaux_Id(int idTravaux);
}