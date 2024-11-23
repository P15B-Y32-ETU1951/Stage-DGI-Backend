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


CREATE DATABASE dgi
WITH
    ENCODING = 'UTF8';
    \c dgi;
CREATE SEQUENCE services_id_seq;

ALTER TABLE Services ALTER COLUMN id
SET
    DEFAULT nextval ('services_id_seq');