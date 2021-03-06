package fr.eni.eniEncheres.bo;

import java.sql.Timestamp;

public class Enchere {
    private int id;
    private Article article;
    private Utilisateur utilisateur;
    private Timestamp dateEnchere;
    private int montantEnchere;

    public Enchere (){}

    public Enchere(int id, Article article, Utilisateur utilisateur, Timestamp dateEnchere, int montantEnchere) {
        this.id = id;
        this.article = article;
        this.utilisateur = utilisateur;
        this.dateEnchere = dateEnchere;
        this.montantEnchere = montantEnchere;
    }

    public Enchere(Article article, Utilisateur utilisateur, Timestamp dateEnchere, int montantEnchere) {
        this.article = article;
        this.utilisateur = utilisateur;
        this.dateEnchere = dateEnchere;
        this.montantEnchere = montantEnchere;
    }

    @Override
    public String toString() {
        return "Dao{" +
                "id=" + id +
                ", article=" + article +
                ", utilisateur=" + utilisateur +
                ", dateEnchere=" + dateEnchere +
                ", montantEnchere=" + montantEnchere +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Timestamp getDateEnchere() {
        return dateEnchere;
    }

    public void setDateEnchere(Timestamp dateEnchere) {
        this.dateEnchere = dateEnchere;
    }

    public int getMontantEnchere() {
        return montantEnchere;
    }

    public void setMontantEnchere(int montantEnchere) {
        this.montantEnchere = montantEnchere;
    }
}
