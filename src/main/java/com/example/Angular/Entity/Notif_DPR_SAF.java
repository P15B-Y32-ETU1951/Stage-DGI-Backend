package com.example.Angular.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Notif_DPR_SAF {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int id;

    @ManyToOne
    @JoinColumn(name = "id_demande")
    Demande demande;
    boolean recent;

}