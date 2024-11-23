package com.example.Angular.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Angular.Entity.Travaux;

public interface TravauxRepository extends JpaRepository<Travaux, Integer> {

}