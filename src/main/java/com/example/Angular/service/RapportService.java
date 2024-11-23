package com.example.Angular.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Angular.Entity.Demande;
import com.example.Angular.Entity.Rapport;
import com.example.Angular.dto.RapportRequest;
import com.example.Angular.repository.DemandeRepository;
import com.example.Angular.repository.RapportRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.http.HttpHeaders;

@Service
public class RapportService {
    // Implement the required methods for managing reports
    @Autowired
    RapportRepository rapportRepository;
    @Autowired
    DemandeRepository demandeRepository;

    /**
     * Fonction pour copier le fichier dans un répertoire spécifique et le renommer.
     */

    private static final String rapport_directory = "C:\\Users\\MSI\\Desktop\\ITU\\Rapports_path";

    public ResponseEntity<?> uploadRapport(
            MultipartFile file,
            int demandeId) {
        try {
            // Vérifier si la demande existe
            Demande demande = demandeRepository.findById(demandeId)
                    .orElseThrow(() -> new RuntimeException("Demande introuvable"));

            // Vérifier si le fichier n'est pas vide
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Le fichier est vide");
            }

            // Créer le nom de fichier avec l'ID de la demande
            String extension = getFileExtension(file.getOriginalFilename());
            String newFileName = "rapport-" + demandeId + "." + extension;

            // Enregistrer le fichier dans le répertoire spécifié
            Path filePath = Paths.get(rapport_directory, newFileName);
            Files.copy(file.getInputStream(), filePath);

            // Créer et sauvegarder le rapport
            Rapport rapport = new Rapport();
            rapport.setDemande(demande);
            rapport.setPath(filePath.toString());
            rapportRepository.save(rapport);

            return ResponseEntity.ok("Rapport importé avec succès");

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors de la sauvegarde du fichier");
        }
    }

    private String getFileExtension(String filePath) {
        String fileName = new File(filePath).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    public ResponseEntity<Resource> downloadRapport(int id) {
        Rapport rapport = rapportRepository.findByDemande_Id(id).orElse(null);
        if (rapport == null || rapport.getPath() == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            // Créer un chemin complet vers le fichier
            Path filePath = Paths.get(rapport_directory).resolve(rapport.getPath()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                // Utiliser "inline" au lieu de "attachment" pour afficher le PDF dans le
                // navigateur
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .contentType(MediaType.APPLICATION_PDF) // Type MIME pour un PDF
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
