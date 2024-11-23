package com.example.Angular.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Angular.Entity.Ressource;
import java.util.List;

@Repository
public interface RessourceRepository extends JpaRepository<Ressource, Integer> {

    List<Ressource> findAllByDispo(boolean dispo);
}
