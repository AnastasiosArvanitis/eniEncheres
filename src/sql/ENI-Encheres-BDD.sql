/*BASE DE DONNEE*/

USE master;
GO

/*CREATION BASE DE DONNEE*/

CREATE DATABASE ENI_ENCHERE
GO

/*CONNECTION BASE DE DONNEE*/

USE ENI_ENCHERE;
GO

/* CREATION DE TABLE */

CREATE TABLE CATEGORIES (
    id              INTEGER IDENTITY(1,1) CONSTRAINT categories_pk PRIMARY KEY,
    libelle         VARCHAR(30) NOT NULL
)

CREATE TABLE RETRAITS (
	id               INTEGER IDENTITY(1,1) CONSTRAINT retraits_pk PRIMARY KEY,
    rue              VARCHAR(30) NOT NULL,
    codePostal       VARCHAR(5) NOT NULL,
    ville            VARCHAR(30) NOT NULL
)


CREATE TABLE UTILISATEURS (
    id               INTEGER IDENTITY(1,1) CONSTRAINT utilisateurs_pk PRIMARY KEY,
    pseudo           VARCHAR(30) NOT NULL CONSTRAINT utilisateur_pseudo_uk UNIQUE,
    nom              VARCHAR(30) NOT NULL,
    prenom           VARCHAR(30) NOT NULL,
    email            VARCHAR(30) NOT NULL CONSTRAINT utilisateur_email_uk UNIQUE,
    telephone        VARCHAR(10) NULL,
    rue              VARCHAR(30) NOT NULL,
    codePostal       VARCHAR(5) NOT NULL,
    ville            VARCHAR(30) NOT NULL,
    motDePasse       VARCHAR(30) NOT NULL,
    credit           INTEGER NOT NULL CONSTRAINT utilisateur_credit_df DEFAULT 0,
    administrateur   bit NOT NULL CONSTRAINT utilisateur_administrateur_df DEFAULT 0,
    compteActif      bit NOT NULL CONSTRAINT utilisateur_compte_actif_df DEFAULT 1, 
)


CREATE TABLE ARTICLES (
    id                            INTEGER IDENTITY(1,1) CONSTRAINT articles_pk PRIMARY KEY,
    idUtilisateur                 INTEGER NOT NULL CONSTRAINT articles_id_utilisateur_fk FOREIGN KEY REFERENCES UTILISATEURS(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    idCategorie                   INTEGER NOT NULL CONSTRAINT articles_id_categorie_fk FOREIGN KEY REFERENCES CATEGORIES(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    idRetrait					  INTEGER NULL CONSTRAINT articles_id_retrait_fk FOREIGN KEY REFERENCES RETRAITS(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    nom                           VARCHAR(30) NOT NULL,
    description                   VARCHAR(300) NOT NULL,
    dateDebutEncheres             DATE NOT NULL,
    dateFinEncheres               DATE NOT NULL,
    prixInitial                   INTEGER NULL CONSTRAINT articles_prix_initial_chk CHECK (prixInitial >= 0),
    prixVente                     INTEGER NULL ,
    CONSTRAINT articles_date_fin_encheres_chk CHECK (dateFinEncheres > dateDebutEncheres),
    CONSTRAINT articles_prix_vente_chk CHECK ((prixVente >= prixInitial) OR (prixVente = NULL))
)


CREATE TABLE ENCHERES(
    id                        INTEGER IDENTITY(1,1) NOT NULL ,
    idArticle                 INTEGER NOT NULL CONSTRAINT encheres_id_article_fk FOREIGN KEY REFERENCES ARTICLES(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    idUtilisateur             INTEGER NOT NULL CONSTRAINT encheres_id_utilisateur_fk FOREIGN KEY REFERENCES UTILISATEURS(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    dateEnchere               datetime NOT NULL,
    montantEnchere            INTEGER NOT NULL,

    CONSTRAINT encheres_pk PRIMARY KEY (id,idArticle,idUtilisateur),
    CONSTRAINT encheres_montant_enchere_uk UNIQUE (idArticle,montantEnchere)
)
 


