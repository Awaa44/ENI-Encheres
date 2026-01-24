-- =============================================
-- Suppression des objets existants
-- =============================================

-- Supprimer les procédures si elles existent
IF OBJECT_ID('sp_Create_User', 'P') IS NOT NULL
DROP PROCEDURE sp_Create_User;
GO
IF OBJECT_ID('sp_Update_User', 'P') IS NOT NULL
DROP PROCEDURE sp_Update_User;
GO
IF OBJECT_ID('sp_Delete_User', 'P') IS NOT NULL
DROP PROCEDURE sp_Delete_User;
GO

-- Supprimer la vue
IF OBJECT_ID('View_Liste_Enchere', 'V') IS NOT NULL
DROP VIEW View_Liste_Enchere;
GO

-- Supprimer les tables dans le bon ordre (enfants d'abord)
DROP TABLE IF EXISTS ENCHERES;
DROP TABLE IF EXISTS ARTICLES_VENDUS;
DROP TABLE IF EXISTS ADRESSES;
DROP TABLE IF EXISTS UTILISATEURS;
DROP TABLE IF EXISTS CATEGORIES;
DROP TABLE IF EXISTS RETRAIT;
GO

-- =============================================
-- Création des tables
-- =============================================

CREATE TABLE RETRAIT (
                         id_retrait INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
                         rue VARCHAR(30) NOT NULL,
                         code_postal VARCHAR(15) NOT NULL,
                         ville VARCHAR(30) NOT NULL
);

CREATE TABLE CATEGORIES (
                            no_categorie INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
                            libelle VARCHAR(30) NOT NULL
);

CREATE TABLE UTILISATEURS (
                              no_utilisateur INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
                              id_retrait INT NULL,
                              pseudo VARCHAR(30) NOT NULL,
                              nom VARCHAR(30) NOT NULL,
                              prenom VARCHAR(30) NOT NULL,
                              email VARCHAR(50) NOT NULL,
                              telephone VARCHAR(15),
                              mot_de_passe VARCHAR(255) NOT NULL,
                              credit INT NOT NULL,
                              administrateur BIT NOT NULL,
                              CONSTRAINT utilisateurs_retrait_adresse_fk FOREIGN KEY (id_retrait)
                                  REFERENCES RETRAIT(id_retrait)
);

CREATE TABLE ARTICLES_VENDUS (
                                 no_article INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
                                 id_retrait INT NOT NULL,
                                 nom_article VARCHAR(30) NOT NULL,
                                 description VARCHAR(300) NOT NULL,
                                 date_debut_encheres DATETIME NOT NULL,
                                 date_fin_encheres DATETIME NOT NULL,
                                 prix_initial INT,
                                 prix_vente INT,
                                 etatVente INT NOT NULL,
                                 no_utilisateur INT NOT NULL,
                                 no_categorie INT NOT NULL,
                                 CONSTRAINT articles_vendus_retrait_adresse_fk FOREIGN KEY (id_retrait)
                                     REFERENCES RETRAIT(id_retrait),
                                 CONSTRAINT ventes_utilisateur_fk FOREIGN KEY (no_utilisateur)
                                     REFERENCES UTILISATEURS(no_utilisateur) ON DELETE CASCADE,
                                 CONSTRAINT articles_vendus_categories_fk FOREIGN KEY (no_categorie)
                                     REFERENCES CATEGORIES(no_categorie)
);

CREATE TABLE ENCHERES (
                          no_enchere INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
                          date_enchere DATETIME NOT NULL,
                          montant_enchere INT NOT NULL,
                          no_article INT NOT NULL,
                          no_utilisateur INT NOT NULL,
                          CONSTRAINT encheres_utilisateur_fk FOREIGN KEY (no_utilisateur)
                              REFERENCES UTILISATEURS(no_utilisateur) ON DELETE CASCADE,
                          CONSTRAINT encheres_no_article_fk FOREIGN KEY (no_article)
                              REFERENCES ARTICLES_VENDUS(no_article) ON DELETE NO ACTION
);

CREATE TABLE ADRESSES (
                          id_retrait INT NOT NULL,
                          no_utilisateur INT NOT NULL,
                          CONSTRAINT adresses_id_retrait_fk FOREIGN KEY (id_retrait)
                              REFERENCES RETRAIT(id_retrait),
                          CONSTRAINT adresses_no_utilisateur_fk FOREIGN KEY (no_utilisateur)
                              REFERENCES UTILISATEURS(no_utilisateur) ON DELETE CASCADE
);
GO

-- =============================================
-- Création de la vue
-- =============================================
SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;
GO

CREATE VIEW View_Liste_Enchere AS
SELECT
    art.no_article,
    art.id_retrait,
    art.nom_article,
    art.description,
    art.date_debut_encheres,
    art.date_fin_encheres,
    art.prix_initial,
    art.prix_vente,
    art.etatVente,
    art.no_utilisateur,
    art.no_categorie,
    cat.libelle AS libelleCategorie,
    util.pseudo
FROM ARTICLES_VENDUS art
         LEFT JOIN CATEGORIES cat ON cat.no_categorie = art.no_categorie
         LEFT JOIN UTILISATEURS util ON util.no_utilisateur = art.no_utilisateur;
GO

-- =============================================
-- Procédure CREATE USER
-- =============================================
USE [ENCHERES]
GO
/****** Object:  StoredProcedure [dbo].[sp_Create_User]    Script Date: 17/12/2025 12:10:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Procédure CREATE USER
-- =============================================
GO
CREATE PROCEDURE [dbo].[sp_Create_User]
    @pseudo VARCHAR(30),
    @nom VARCHAR(30),
    @prenom VARCHAR(30),
    @email VARCHAR(50),
    @telephone VARCHAR(15),
    @mot_de_passe VARCHAR(255),
    @credit INT,
    @administrateur BIT,
    @rue VARCHAR(30),
    @code_postal VARCHAR(15),
    @ville VARCHAR(30)
AS
BEGIN
    -- 1. Insérer un Retrait
INSERT INTO RETRAIT(rue,code_postal,ville)
VALUES (@rue,@code_postal,@ville);

DECLARE @id_retrait INT = SCOPE_IDENTITY();

    -- 2. Insérer l'utilisateur
INSERT INTO UTILISATEURS (id_retrait,pseudo,nom,prenom,email,telephone,mot_de_passe,credit,administrateur)
VALUES (@id_retrait,@pseudo,@nom,@prenom,@email,@telephone,@mot_de_passe,@credit,@administrateur);

DECLARE @userId INT = SCOPE_IDENTITY();

    -- 3. Insérer l'adresse liée à l'utilisateur
INSERT INTO ADRESSES (no_utilisateur,id_retrait)
VALUES (@userId, @id_retrait);

-- 4. Retourner l'ID de l'utilisateur
SELECT @userId AS userId;
SELECT @id_retrait AS id_retrait;
END;
-- =============================================
-- Procédure UPDATE USER
-- =============================================
GO
CREATE PROCEDURE sp_Update_User
    @no_utilisateur INT,
    @pseudo VARCHAR(30),
    @nom VARCHAR(30),
    @prenom VARCHAR(30),
    @email VARCHAR(50),
    @telephone VARCHAR(15),
    @mot_de_passe VARCHAR(255),
    @id_retrait INT,
    @rue VARCHAR(30),
    @code_postal VARCHAR(15),
    @ville VARCHAR(30)
AS
BEGIN
    -- 1. Modification des coordonnées
UPDATE RETRAIT SET
                   rue = @rue,
                   code_postal = @code_postal,
                   ville = @ville
WHERE id_retrait = @id_retrait;

-- 2. Modification des informations utilisateur
UPDATE UTILISATEURS SET
                        pseudo = @pseudo,
                        nom = @nom,
                        prenom = @prenom,
                        email = @email,
                        telephone = @telephone,
                        mot_de_passe = @mot_de_passe
WHERE no_utilisateur = @no_utilisateur;
END;
GO

-- =============================================
-- Procédure DELETE USER
-- =============================================
GO
CREATE PROCEDURE sp_Delete_User
    @no_utilisateur INT
AS
BEGIN
    SET NOCOUNT ON;

BEGIN TRANSACTION;

BEGIN TRY
        -- Supprimer les enchères sur les articles de l'utilisateur
DELETE FROM ENCHERES
WHERE no_article IN (
    SELECT no_article
    FROM ARTICLES_VENDUS
    WHERE no_utilisateur = @no_utilisateur
);

-- Supprimer les enchères de l'utilisateur
DELETE FROM ENCHERES
WHERE no_utilisateur = @no_utilisateur;

-- Supprimer les articles vendus par l'utilisateur
DELETE FROM ARTICLES_VENDUS
WHERE no_utilisateur = @no_utilisateur;

        -- Détacher le retrait de l'utilisateur
        UPDATE UTILISATEURS
        SET id_retrait = NULL
        WHERE no_utilisateur = @no_utilisateur;

        -- Supprimer les liaisons dans ADRESSES de l'utilisateur
        DELETE FROM ADRESSES
        WHERE no_utilisateur = @no_utilisateur;

        --Supprimer tous les retraits orphelins dans ADRESSES
            DELETE FROM RETRAIT
        WHERE id_retrait NOT IN (
            SELECT DISTINCT id_retrait
            FROM ADRESSES
            );

-- Supprimer l'utilisateur
DELETE FROM UTILISATEURS
WHERE no_utilisateur = @no_utilisateur;

COMMIT TRANSACTION;
END TRY
BEGIN CATCH
ROLLBACK TRANSACTION;
        THROW;
END CATCH
END;