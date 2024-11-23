package com.example.Angular.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class PlanificationRequest {
    int id_demande;
    LocalDate dateDebut;
    LocalDate dateFin;
    List<RessourceDTO> ressources;
    double total;

}
