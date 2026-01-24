-- ------------------------------------------------------
-- Table RETRAIT
-- ------------------------------------------------------
INSERT INTO RETRAIT (rue, code_postal, ville) VALUES
('1 Rue des Fleurs', '75001', 'Paris'),
('2 Avenue des Champs', '69001', 'Lyon'),
('3 Boulevard du Roi', '31000', 'Toulouse');


-- ------------------------------------------------------
-- Table CATEGORIES
-- ------------------------------------------------------
INSERT INTO CATEGORIES (libelle) VALUES
('Informatique'),
('Ameublement'),
('Vêtement'),
('Sport&Loisirs');

-- ------------------------------------------------------
-- Table UTILISATEURS
-- ------------------------------------------------------
INSERT INTO UTILISATEURS (
    id_retrait, pseudo, nom, prenom, email, telephone,
    mot_de_passe, credit, administrateur
) VALUES
(1, 'Alice', 'Dupont', 'Alice', 'alice@mail.com', '0600000001', 'mdp1', 500, 0),
(2, 'Bob', 'Martin', 'Bob', 'bob@mail.com', '0600000002', 'mdp2', 300, 0),
(3, 'Charlie', 'Durand', 'Charlie', 'charlie@mail.com', '0600000003', 'mdp3', 1000, 1);


-- ------------------------------------------------------
-- Table ARTICLES_VENDUS
-- ------------------------------------------------------
-- Articles en cours
INSERT INTO ARTICLES_VENDUS (id_retrait, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, etatVente, no_utilisateur, no_categorie) VALUES
(1, 'Laptop HP', 'Laptop HP 15 pouces', DATEADD(DAY, -2, GETDATE()), DATEADD(DAY, 3, GETDATE()), 300, NULL, 0, 1, 1),
(2, 'Chaise en bois', 'Chaise confortable', DATEADD(DAY, -1, GETDATE()), DATEADD(DAY, 2, GETDATE()), 50, NULL, 0, 2, 2);

-- Articles terminés
INSERT INTO ARTICLES_VENDUS (id_retrait, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, etatVente, no_utilisateur, no_categorie) VALUES
(3, 'Console PS5', 'Console Sony PlayStation 5', DATEADD(DAY, -10, GETDATE()), DATEADD(DAY, -1, GETDATE()), 400, 550, 1, 3, 4),
(1, 'Veste en cuir', 'Veste noire en cuir', DATEADD(DAY, -5, GETDATE()), DATEADD(DAY, -1, GETDATE()), 100, 150, 1, 1, 3);

-- Article sans enchère
INSERT INTO ARTICLES_VENDUS (id_retrait, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, etatVente, no_utilisateur, no_categorie) VALUES
(2, 'Table basse', 'Table en verre', DATEADD(DAY, -1, GETDATE()), DATEADD(DAY, 1, GETDATE()), 70, NULL, 0, 2, 2);

-- Article terminé sans enchère
INSERT INTO ARTICLES_VENDUS (id_retrait, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, etatVente, no_utilisateur, no_categorie) VALUES
(3, 'Tapis', 'Tapis persan', DATEADD(DAY, -5, GETDATE()), DATEADD(DAY, -1, GETDATE()), 200, NULL, 2, 3, 2);
-- ------------------------------------------------------
-- Table ENCHERES
-- ------------------------------------------------------
-- Enchères en cours
INSERT INTO ENCHERES (date_enchere, montant_enchere, no_article, no_utilisateur) VALUES
(GETDATE(), 320, 1, 2), -- Bob enchère sur Laptop
(GETDATE(), 330, 1, 3), -- Charlie enchère sur Laptop
(GETDATE(), 60, 2, 1);  -- Alice enchère sur Chaise

-- Enchères terminées
INSERT INTO ENCHERES (date_enchere, montant_enchere, no_article, no_utilisateur) VALUES
(DATEADD(DAY, -2, GETDATE()), 450, 3, 1), -- Alice sur PS5
(DATEADD(DAY, -1, GETDATE()), 500, 3, 2), -- Bob sur PS5
(DATEADD(DAY, -1, GETDATE()), 550, 3, 3), -- Charlie gagne PS5
(DATEADD(DAY, -3, GETDATE()), 120, 4, 2), -- Bob sur Veste
(DATEADD(DAY, -2, GETDATE()), 150, 4, 1); -- Alice gagne Veste


-- ------------------------------------------------------
-- Table ADRESSES
-- ------------------------------------------------------
INSERT INTO ADRESSES (id_retrait, no_utilisateur) VALUES
(1, 1),  -- Alice récupère ses articles au retrait 1
(2, 2),  -- Bob récupère ses articles au retrait 2
(3, 3);  -- Charlie récupère ses articles au retrait 3




