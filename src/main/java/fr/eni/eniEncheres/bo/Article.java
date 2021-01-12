package fr.eni.eniEncheres.bo;

import java.sql.Timestamp;


public class Article {

    private int id;
    private Utilisateur utilisateur;
    private Categorie categorie;
    private Retrait retrait;
    private String nom;
    private String description;
    private Timestamp dateDebutEncheres;
    private Timestamp dateFinEncheres;
    private int prixInitial;
    private int prixVente;

    public Article() {}

    public Article(Utilisateur utilisateur, Categorie categorie, String nom, String description, Timestamp dateDebutEncheres, Timestamp dateFinEncheres, int prixInitial) {
        this.utilisateur = utilisateur;
        this.categorie = categorie;
        this.nom = nom;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.prixInitial = prixInitial;
    }

    public Article(Utilisateur utilisateur, Categorie categorie, Retrait retrait, String nom, String description, Timestamp dateDebutEncheres, Timestamp dateFinEncheres, int prixInitial) {
        this.utilisateur = utilisateur;
        this.categorie = categorie;
        this.retrait = retrait;
        this.nom = nom;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.prixInitial = prixInitial;
        this.prixVente = prixVente;
    }

    public Article(int id, Utilisateur utilisateur, Categorie categorie, Retrait retrait, String nom, String description, Timestamp dateDebutEncheres, Timestamp dateFinEncheres, int prixInitial, int prixVente) {
        this.id = id;
        this.utilisateur = utilisateur;
        this.categorie = categorie;
        this.retrait = retrait;
        this.nom = nom;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.prixInitial = prixInitial;
        this.prixVente = prixVente;
    }
    public Article(Utilisateur utilisateur, Categorie categorie, Retrait retrait, String nom, String description, Timestamp dateDebutEncheres, Timestamp dateFinEncheres, int prixInitial, int prixVente, int id) {
        this.utilisateur = utilisateur;
        this.categorie = categorie;
        this.retrait = retrait;
        this.nom = nom;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.prixInitial = prixInitial;
        this.prixVente = prixVente;
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Retrait getRetrait() {
        return retrait;
    }

    public void setRetrait(Retrait retrait) {
        this.retrait = retrait;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDateDebutEncheres() {
        return dateDebutEncheres;
    }

    public void setDateDebutEncheres(Timestamp dateDebutEncheres) {
        this.dateDebutEncheres = dateDebutEncheres;
    }

    public Timestamp getDateFinEncheres() {
        return dateFinEncheres;
    }

    public void setDateFinEncheres(Timestamp dateFinEncheres) {
        this.dateFinEncheres = dateFinEncheres;
    }

    public int getPrixInitial() {
        return prixInitial;
    }

    public void setPrixInitial(int prixInitial) {
        this.prixInitial = prixInitial;
    }

    public int getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(int prixVente) {
        this.prixVente = prixVente;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", utilisateur=" + utilisateur +
                ", categorie=" + categorie +
                ", retrait=" + retrait +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", dateDebutEncheres=" + dateDebutEncheres +
                ", dateFinEncheres=" + dateFinEncheres +
                ", prixInitial=" + prixInitial +
                ", prixVente=" + prixVente +
                '}';
    }
}
