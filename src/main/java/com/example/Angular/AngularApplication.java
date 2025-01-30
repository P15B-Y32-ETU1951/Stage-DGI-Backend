package com.example.Angular;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.Angular.Entity.Role;
import com.example.Angular.Entity.Services;
import com.example.Angular.Entity.Statut;
import com.example.Angular.Entity.Utilisateur;
import com.example.Angular.repository.ServiceRepository;
import com.example.Angular.repository.StatutRepository;
import com.example.Angular.repository.UtilisateurRepository;

@SpringBootApplication
public class AngularApplication implements CommandLineRunner {

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@Autowired
	private StatutRepository statutRepository;

	@Autowired 
	private  ServiceRepository serviceRepository;

	@Override
public void run(String... args) throws Exception {


    // Vérification des statuts
    List<Statut> statut = statutRepository.findAll();
    if (statut.isEmpty()) {
        String[] descriptions = {
            "envoyée", "déclenchée", "prise en charge", "validée", "rejetée",
            "planifiée", "en cours de travaux", "terminée", "clôturée", "réclamation en cours"
        };
        for (String description : descriptions) {
            Statut newStatut = new Statut();
            newStatut.setDescription(description);
            statutRepository.save(newStatut);
        }
    }

    // Vérification des services
    List<Services> services = serviceRepository.findAll();
    if (services.isEmpty()) {
        // Liste des services avec "Service Administratif Financier" en 3ème position
        String[] serviceNames = {
            "Service de Recouvrement",
            "Service d'Accueil et Information",
            "Service Administratif Financier", // 3ème position
            "Service de Gestion",
            "Service de Contrôle",
            "Service Régionaux des Entreprises",
            "Service des Ressources Locales",
            "Service Fiscaux",
            "Service de la Législation Fiscale",
            "Service de la Fiscalité Internationale",
            "Service des Contentieux et des Poursuites",
            "Service de la Recherche",
            "Service de la Coordination et d'Appui au Contrôle Fiscale",
            "Service du Remboursement de Crédit de TVA",
            "Service Statistique et de la Prevision",
            "Service de la Comptabilité et d'Appui Technique",
            "Service de la Coordination de la Fiscalité Locle",
            "Service des Régimes Spéciaux",
            "Service des Etudes et de la Gestion des Carrières",
            "Service de la Formation",
            "Service de la Promotion du Civisme Fiscal",
            "Personne Responsable des Marchés Publiques",
            "Service d'Analyse Economique et Fiscale",
            "Service de Pilotage et de la communication",
            "Service de la Brigade d'Inspection",
            "Service du Système d'Information Fiscale",
            "Service au contribuables"
        };

        // Insertion des services
        for (String serviceName : serviceNames) {
            Services newService = new Services();
            newService.setNom(serviceName);
            serviceRepository.save(newService);
        }

		    // Vérification des utilisateurs
			List<Utilisateur> admin = utilisateurRepository.findByRole(Role.DPR_SAF);
			if (admin.isEmpty()) {
				Utilisateur user = new Utilisateur();
				user.setNom("DPR");
				user.setPrenom("SAF");
				user.setEmail("admin@gmail.com");
				user.setPassword(new BCryptPasswordEncoder().encode("admin"));
				user.setRole(Role.DPR_SAF);
				user.setEnabled(true);
				user.setPasswordExpiryDate(LocalDate.now().plusMonths(6));
				user.setService(serviceRepository.findById(3).get());
				utilisateurRepository.save(user);
			}
    }
}

public static void main(String[] args) {
    SpringApplication.run(AngularApplication.class, args);
}


}
