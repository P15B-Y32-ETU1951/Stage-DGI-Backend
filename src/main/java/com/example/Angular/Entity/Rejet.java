package com.example.Angular.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Rejet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int id;

    @OneToOne
    @JoinColumn(name = "id_demande")
    Demande demande;

    @Column(columnDefinition = "TEXT")
    String motif;

    LocalDateTime date;
}