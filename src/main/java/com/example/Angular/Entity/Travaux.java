package com.example.Angular.Entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Travaux {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    String nom;
    LocalDate dateDebut;
    LocalDate dateFin;

    @ManyToOne
    @JoinColumn(name = "id_demande")
    @JsonBackReference
    private Demande demande;

    @OneToMany(mappedBy = "travaux", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<RessourceTravaux> ressourceTravaux;

    double total;

    // Getters and Setters
}
