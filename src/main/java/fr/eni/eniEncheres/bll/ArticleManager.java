package fr.eni.eniEncheres.bll;

import fr.eni.eniEncheres.bo.Article;
import fr.eni.eniEncheres.dal.DalException;
import fr.eni.eniEncheres.dal.FactoryDao;
import fr.eni.eniEncheres.dal.dao.ArticleDao;
import fr.eni.eniEncheres.tools.EnchereLogger;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ArticleManager {

    private static ArticleManager instance;
    private ArticleDao articleDao;
    private Logger logger = EnchereLogger.getLogger("ArticleManager");

    //Private constractor
    private ArticleManager() { articleDao = FactoryDao.getArticleDao(); }

    //Only one instance
    public static ArticleManager getInstance() {
        if (instance == null) {
            return new ArticleManager();
        }
        return instance;
    }

    public List<Article> getAllArticles() throws SQLException, BllException {
        List<Article> articleList = new ArrayList<>();
        try {
            articleList = articleDao.selectAllArticles();
        } catch(SQLException | DalException e){
            logger.severe("Error dans getAllArticles ArticleManager " + e.getMessage());
            throw new BllException("Error getAllArticles ArticleManager " + e.getMessage());
        }
        return articleList;
    }

    public Article getArticleById(int articleId) throws SQLException, BllException {
        Article article = new Article();
        try {
            article = articleDao.selectArticleById(articleId);
        } catch(SQLException | DalException e){
            logger.severe("Error dans getArticleById ArticleManager " + e.getMessage());
            throw new BllException("Error getArticleById ArticleManager " + e.getMessage());
        }
        return article;
    }

    public Article addNewArticle(Article newArticle) throws Exception {
        Article addedArticle = null;
        if (newArticle.getUtilisateur() == null) {
            throw new BllException("L'article doit avoir un utilisatuer");
        } else if (newArticle.getCategorie() == null) {
            throw new BllException("L'article doit avoir une categorie");
        } else if (newArticle.getRetrait() == null) {
            throw new BllException("L'article doit avoir un point de retrait");
        } else if (newArticle.getNom().equals("")) {
            throw new BllException("L'article doit avoir un nom");
        } else if (newArticle.getDescription().equals("")) {
            throw new BllException("L'article doit avoir une description");
        } else if (newArticle.getDateDebutEncheres() == null) {
            throw new BllException("L'article doit avoir une date de debut d'enchere");
        } else if (newArticle.getDateFinEncheres() == null) {
            throw new BllException("L'article doit avoir une date de fin d'enchere");
        } else if (newArticle.getPrixInitial() == 0) {
            throw new BllException("L'article doit avoir une prix initial");
        } else {
            controleDateEnchere(newArticle);
            addedArticle = articleDao.insertArticle(newArticle);
        }
        return addedArticle;
    }

    public  Article updateArticle(Article updateArticle) throws Exception {
        controleDateEnchere(updateArticle);
        Article articleModifier = articleDao.updateArticle(updateArticle);
        return articleModifier ;

    }
    /**
     * Méthode controleDateEnchere
     * Utilisé sur updateArticle,addNewArticle
     * Contrôle date enchère,
     *              *vérifie que la date fin enchère est supérieur à la date début enchère
     *              *Vérifie que la date de début d'enchère est supérieure à la date du jour
     * @param article
     */
    private void controleDateEnchere(Article article) throws Exception {
        if (article.getDateFinEncheres().before(article.getDateDebutEncheres()))
        throw new BllException("La date de début d'enchère ne peut pas être située après la date de fin enchère");
        if (article.getDateDebutEncheres().before(Timestamp.from(Instant.now()))) {
            if (article.getDateDebutEncheres().getTime() < Timestamp.from(Instant.now()).getTime()) {
                throw new BllException("La date et l'heure de début ne peuvent pas être inférieur à l'heure du jour");
            } else if (article.getDateDebutEncheres().toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).equals(Timestamp.from(Instant.now()).toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))) {
                throw new BllException("Vous avez saisie la même date et heure sur les deux dates");
            }
        }}

        public boolean deleteArticle(Article articleSuppression) throws  SQLException, DalException{

            boolean articleKO = false;
                articleKO = articleDao.deleteArticle(articleSuppression);
                return articleKO;

        }

}
















