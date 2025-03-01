package com.example.Angular.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class PlanificationRequest {
    int id_demande;
    String nom;
    LocalDate dateDebut;
    LocalDate dateFin;
    List<TravauxDTO> travaux;
    double total;

}
