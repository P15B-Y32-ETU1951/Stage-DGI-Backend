CREATE DATABASE dgi
WITH
    ENCODING = 'UTF8';
    \c dgi;
CREATE TABLE
    service (id INT PRIMARY KEY, nom VARCHAR(255));

CREATE TABLE
    statut (id INT PRIMARY KEY, description VARCHAR(255));

CREATE TABLE
    utilisateur (
        id INT PRIMARY KEY,
        id_service INT,
        role VARCHAR(50),
        nom VARCHAR(255),
        prenom VARCHAR(255),
        email VARCHAR(255),
        mdp VARCHAR(255),
        FOREIGN KEY (id_service) REFERENCES service (id)
    );

CREATE TABLE
    demande (
        id INT PRIMARY KEY,
        id_service INT,
        motif VARCHAR(255),
        description VARCHAR(255),
        id_statut INT,
        date DATE,
        FOREIGN KEY (id_service) REFERENCES service (id),
        FOREIGN KEY (id_statut) REFERENCES statut (id)
    );

CREATE TABLE
    travaux (
        id INT PRIMARY KEY,
        id_demande INT,
        FOREIGN KEY (id_demande) REFERENCES demande (id)
    );

CREATE TABLE
    planification (
        id INT PRIMARY KEY,
        id_demande INT,
        date_debut DATE,
        date_fin DATE,
        FOREIGN KEY (id_demande) REFERENCES demande (id)
    );

CREATE TABLE
    ressource (
        id INT PRIMARY KEY,
        nom VARCHAR(255),
        quantite DOUBLE PRECISION,
        valeur_unitaire DOUBLE PRECISION
    );

CREATE VIEW
    ressource_travaux AS
SELECT
    t.id AS id_travaux,
    r.id AS id_ressource,
    r.nom,
    t.quantite,
    r.valeur_unitaire,
    t.quantite * r.valeur_unitaire AS valeur_total
FROM
    travaux t
    JOIN ressource r ON t.id = r.id;



INSERT INTO
    Services (id, nom)
VALUES
    (
        DEFAULT,
        'Direction de la Législation Fiscale et du Contentieux'
    ),
    (
        DEFAULT,
        'Direction de la Recherche et du Contrôle Fiscal'
    ),
    (
        DEFAULT,
        'Direction de la Programmation des Ressources'
    ),
    (DEFAULT, 'Direction Technique'),
    (
        DEFAULT,
        'Direction de la Formation Professionnelle'
    ),
    (DEFAULT, 'Direction des Grandes Entreprises');

/ / / / / / / / / / / / / / / / / 



CREATE TABLE
    services (id INT PRIMARY KEY, nom VARCHAR(255));

CREATE SEQUENCE services_id_seq;

ALTER TABLE Services ALTER COLUMN id
SET
    DEFAULT nextval ('services_id_seq');

INSERT INTO services (id, nom) VALUES 
(1, 'Service de Recouvrement'),
(2, 'Service d\'Accueil et Information'),
(3, 'Service Administratif Financier'),
(4, 'Service de Gestion'),
(5, 'Service de Contrôle'),
(6, 'Service Régionaux des Entreprises'),
(7, 'Service des Ressources Locales'),
(8, 'Service Fiscaux'),
(9, 'Service de la Législation Fiscale'),
(10, 'Service de la Fiscalité Internationale'),
(11, 'Service des Contentieux et des Poursuites'),
(12, 'Service de la Recherche'),
(13, 'Service de la Coordination et d\'Appui au Contrôle Fiscale'),
(14, 'Service du Remboursement de Crédit de TVA'),
(15, 'Service Statistique et de la Prevision'),
(16, 'Service de la Comptabilité et d\'Appui Technique'),
(17, 'Service de la Coordination de la Fiscalité Locle'),
(18, 'Service des Régimes Spéciaux'),
(19, 'Service des Etudes et de la Gestion des Carrières'),
(20, 'Service de la Formation'),
(21, 'Service de la Promotion du Civisme Fiscal'),
(22, 'Personne Responsable des Marchés Publiques'),
(23, 'Service d\'Analyse Economique et Fiscale'),
(24, 'Service de Pilotage et de la communication'),
(25, 'Service de la Brigade d\'Inspection'),
(26, 'Service du Système d\'Information Fiscale'),
(27, 'Service au contribuables');
