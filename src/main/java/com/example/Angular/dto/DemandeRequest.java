package com.example.Angular.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DemandeRequest {

    LocalDate date;
    String description;
    String motif;
    int id_statut;
}