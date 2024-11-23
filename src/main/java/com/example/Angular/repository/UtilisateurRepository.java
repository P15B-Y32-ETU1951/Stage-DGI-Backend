package com.example.Angular.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Angular.Entity.Role;
import com.example.Angular.Entity.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    Optional<Utilisateur> findByEmail(String email);

    List<Utilisateur> findByRole(Role role);
}