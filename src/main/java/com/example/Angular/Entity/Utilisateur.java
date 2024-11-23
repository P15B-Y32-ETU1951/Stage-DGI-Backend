package com.example.Angular.Entity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "utilisateur")
public class Utilisateur implements UserDetails {
    private static final int PASSWORD_VALIDITY_PERIOD_MONTHS = 6; // Durée de validité des mots de passe (6 mois)

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String nom;

    @ManyToOne
    @JoinColumn(name = "id_service")
    private Services service;

    private String prenom;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Role role;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    // Champ pour gérer l'expiration du mot de passe
    @Column(name = "password_expiry_date")
    private LocalDate passwordExpiryDate;

    // Méthode pour vérifier si le mot de passe est expiré
    public boolean isPasswordExpired() {
        return LocalDate.now().isAfter(passwordExpiryDate);
    }

    // Mise à jour de la date d'expiration lors d'une modification du mot de passe
    public void updatePassword(String newPassword) {
        this.password = newPassword;
        this.passwordExpiryDate = LocalDate.now().plusMonths(PASSWORD_VALIDITY_PERIOD_MONTHS);
    }

    // Implémentation des méthodes UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
