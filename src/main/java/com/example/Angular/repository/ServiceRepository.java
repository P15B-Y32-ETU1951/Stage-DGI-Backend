package com.example.Angular.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Angular.Entity.Services;

@Repository
public interface ServiceRepository extends JpaRepository<Services, Integer> {

}