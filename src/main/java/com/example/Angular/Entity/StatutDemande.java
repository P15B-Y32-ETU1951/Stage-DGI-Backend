package com.example.Angular.Entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class StatutDemande {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    // Relation avec la demande
    @ManyToOne
    @JoinColumn(name = "id_demande", nullable = false)
    @JsonBackReference // Pour gérer la sérialisation JSON et éviter les boucles infinies
    private Demande demande;

    // Relation avec le statut
    @ManyToOne
    @JoinColumn(name = "id_statut", nullable = false) // L'id du statut
    private Statut statut;

    // Date et heure du changement de statut
    private LocalDateTime date_changement;
}
