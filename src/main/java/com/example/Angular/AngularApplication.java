package com.example.Angular;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.Angular.Entity.Role;
import com.example.Angular.Entity.Utilisateur;
import com.example.Angular.Entity.Statut;
import com.example.Angular.repository.StatutRepository;
import com.example.Angular.repository.UtilisateurRepository;

@SpringBootApplication
public class AngularApplication implements CommandLineRunner {

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@Autowired
	private StatutRepository statutRepository;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		List<Utilisateur> admin = utilisateurRepository.findByRole(Role.DPR_SAF);
		if (admin.isEmpty()) {
			Utilisateur user = new Utilisateur();
			user.setNom("DPR");
			user.setPrenom("SAF");
			user.setEmail("admin@gmail.com");
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			user.setRole(Role.DPR_SAF);
			user.setEnabled(true);
			utilisateurRepository.save(user);
		}

		List<Statut> statut = statutRepository.findAll();
		if (statut.isEmpty()) {
			Statut statut1 = new Statut();
			statut1.setDescription("existant");
			statutRepository.save(statut1);
			Statut statut2 = new Statut();
			statut2.setDescription("déclenchée");
			statutRepository.save(statut2);
			Statut statut3 = new Statut();
			statut3.setDescription("prise en charge");
			statutRepository.save(statut3);
			Statut statut4 = new Statut();
			statut4.setDescription("validée");
			statutRepository.save(statut4);
			Statut statut5 = new Statut();
			statut5.setDescription("rejetée");
			statutRepository.save(statut5);

			Statut statut6 = new Statut();
			statut6.setDescription("planifiée");
			statutRepository.save(statut6);
			Statut statut7 = new Statut();
			statut7.setDescription("en cours de travaux");
			statutRepository.save(statut7);
			Statut statut8 = new Statut();
			statut8.setDescription("terminée");
			statutRepository.save(statut8);

			Statut statut9 = new Statut();
			statut9.setDescription("clôturée");
			statutRepository.save(statut9);

			Statut statut10 = new Statut();
			statut10.setDescription("réclamation en cours");
			statutRepository.save(statut10);

		}
	}

	public static void main(String[] args) {

		SpringApplication.run(AngularApplication.class, args);
	}

}
