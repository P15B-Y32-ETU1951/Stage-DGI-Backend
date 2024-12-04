package com.example.Angular.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class TravauxDTO {
    String nom;
    LocalDate dateDebut;
    LocalDate dateFin;
    List<RessourceDTO> ressources;
    double total;
}
