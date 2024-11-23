package com.example.Angular.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Angular.Entity.Planification;

public interface PlanificationRepository extends JpaRepository<Planification, Integer> {

}