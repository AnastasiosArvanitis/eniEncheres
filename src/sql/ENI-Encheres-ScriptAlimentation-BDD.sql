DELETE ENCHERES WHERE ID>0;
DBCC CHECKIDENT(ENCHERES,RESEED,0);

DELETE ARTICLES WHERE ID>0;
DBCC CHECKIDENT(ARTICLES,RESEED,0);

DELETE RETRAITS WHERE ID>0;
DBCC CHECKIDENT(RETRAITS,RESEED,0);

DELETE CATEGORIES WHERE ID>0;
DBCC CHECKIDENT(CATEGORIES,RESEED,0);

DELETE UTILISATEURS WHERE ID >0;
DBCC CHECKIDENT(UTILISATEURS,RESEED,0);

INSERT INTO CATEGORIES VALUES('Informatique');
INSERT INTO CATEGORIES VALUES('Ameublement');
INSERT INTO CATEGORIES VALUES('Vetement');
INSERT INTO CATEGORIES VALUES('Sport & Loisirs');

INSERT INTO UTILISATEURS VALUES('Alana001','Dupont','Alana','ada_eni@yopmail.com','0601020304','5 bis de l europe','44000','Nantes','abcd1234',0,0,1);
INSERT INTO UTILISATEURS VALUES('Za','Pécuchès','Amaïa','zpa_eni@yopmail.com','0706020303','15 allee des taneurs','85000','La Roche Sur Yon','1234ABCD',5,0,1);
INSERT INTO UTILISATEURS VALUES('Grugan','girardello','Hipolyte','ggh_eni@yopmail.com','0165653233','10 Rue Jean-claude Maisonneuve','44220','Couëron','AaBbCcDd',10,0,1);
INSERT INTO UTILISATEURS VALUES('Luc_hel','Pugin-Bron-Lucchino','Hélène','lph_eni@yopmail.com','0236565339','350 Bis Boulevard dumoulin','29000','Brest','@123_mdp',15,0,1);
INSERT INTO UTILISATEURS VALUES('Ant_pereira','viveiro-pereira','Marie-antoinette','avm_eni@yopmail.com','0601020308','6 rue yves montand','06000','Nice','aa-bb-cc-dd',20,0,1);
/* UTILISATEUR INACTIF */
INSERT INTO UTILISATEURS VALUES('PE_Coro','Coro','Utilisateur inactif','pcp_eni@yopmail.com','','66 Impasse de sembrumes','51290','Saint-Remy-en-Bouzemont','12345678',30,0,0);
/* ADMINISTRATEUR */
INSERT INTO UTILISATEURS VALUES('admin','administarteur','admin','admin@admin.fr','0609090909','rue de nantes','44000','Nantes','password',150,1,1);
INSERT INTO UTILISATEURS VALUES('vincdev','vincent','colas','vinc-dev@campus-eni.fr','0606060606','rue de la soif','44000','Nantes','password',350,1,1);

INSERT INTO RETRAITS VALUES('10 Rue des mouettes','44600','ST NAZAIRE');
INSERT INTO RETRAITS VALUES('15 allee des taneurs','85000','La Roche Sur Yon');
INSERT INTO RETRAITS VALUES('6 rue yves montand','06000','Nice');

INSERT INTO ARTICLES VALUES (1,2,1,'Tire bouchon','Magnifique Tire bouchon doré a lor fin','05/01/2021','06/01/2021',10,13);
INSERT INTO ARTICLES VALUES (3,4,2,'Test Article','On va le réussir ce projet','06/01/2021','12/01/2021',15,null);
INSERT INTO ARTICLES VALUES (2,1,1,'Ordinateur Omen','Meilleur ordinateur HP','06/01/2021','15/01/2021',150,null);

INSERT INTO ENCHERES VALUES(1,	2,	'03/01/2021',	10);
INSERT INTO ENCHERES VALUES(1,	3,	'04/01/2021',	11);
INSERT INTO ENCHERES VALUES(1,	4,	'05/01/2021',	12);
INSERT INTO ENCHERES VALUES(1,	2,	'06/01/2021',	13);
INSERT INTO ENCHERES VALUES(2,	1,	'07/01/2021',	16);


/* AJOUT DES UTILISATEURS PAR ERREUR AVEC FORCAGE DE LA PK*/

SET IDENTITY_INSERT UTILISATEURS ON;
INSERT INTO UTILISATEURS (id,pseudo,nom,prenom,email,telephone,rue,codePostal,ville,motDePasse,credit,administrateur,compteActif) VALUES (1, 'Alana001','Dupont','Alana','ada_eni@yopmail.com','0601020304','5 bis de l europe','44000','Nantes','abcd1234',750,0,1);
SET IDENTITY_INSERT UTILISATEURS OFF;

SET IDENTITY_INSERT CATEGORIES ON;
INSERT INTO CATEGORIES (id,libelle) VALUES (2, 'Ameublement');
SET IDENTITY_INSERT CATEGORIES OFF;