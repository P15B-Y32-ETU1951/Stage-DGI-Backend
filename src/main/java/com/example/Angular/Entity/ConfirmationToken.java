package com.example.Angular.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "confirmationToken")
@Data
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int tokenId;

    private String token;
    private LocalDateTime createdDate;

    @OneToOne(targetEntity = Utilisateur.class, fetch = FetchType.EAGER)

    @JoinColumn(nullable = false, name = "utilisateur_id")
    private Utilisateur utilisateur;

}
