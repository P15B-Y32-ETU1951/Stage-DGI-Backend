package com.example.Angular.Entity;

import java.time.LocalDate;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
public class Demande {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_service")
    private Services service;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

    @Column(columnDefinition = "TEXT")
    private String motif;

    @Column(columnDefinition = "TEXT")
    private String description;
    @ManyToOne
    @JoinColumn(name = "id_statut")
    private Statut statut;

    @OneToOne(mappedBy = "demande", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Planification planification;

    @OneToOne(mappedBy = "demande", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Travaux travaux;

    @OneToOne(mappedBy = "demande", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Rapport rapport;

    private LocalDate date;

    @OneToMany(mappedBy = "demande", cascade = CascadeType.ALL)
    @JsonManagedReference // Pour gérer la sérialisation JSON et éviter les boucles infinies
    private List<StatutDemande> statutDemandes;

    @OneToMany(mappedBy = "demande", cascade = CascadeType.ALL)
    @JsonManagedReference // Pour gérer la sérialisation JSON et éviter les boucles infinies
    private List<Reclamation> reclamations;
}
