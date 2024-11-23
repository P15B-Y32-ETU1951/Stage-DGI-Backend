package com.example.Angular.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Angular.Entity.Statut;

@Repository
public interface StatutRepository extends JpaRepository<Statut, Integer> {

}