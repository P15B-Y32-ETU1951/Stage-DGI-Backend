package com.example.Angular.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data

public class StatutDemandeRequest {
    int statut;
    int id_demande;
    LocalDateTime date_demande;
}