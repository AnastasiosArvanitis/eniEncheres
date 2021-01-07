package fr.eni.eniEncheres.bll;

import fr.eni.eniEncheres.bo.Article;
import fr.eni.eniEncheres.dal.DalException;
import fr.eni.eniEncheres.dal.FactoryDao;
import fr.eni.eniEncheres.dal.dao.ArticleDao;
import fr.eni.eniEncheres.tools.EnchereLogger;

import java.sql.SQLException;
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
            throw new Exception("L'article doit avoir un utilisatuer");
        } else if (newArticle.getCategorie() == null) {
            throw new Exception("L'article doit avoir une categorie");
        } else if (newArticle.getRetrait() == null) {
            throw new Exception("L'article doit avoir un point de retrait");
        } else if (newArticle.getNom().equals("")) {
            throw new Exception("L'article doit avoir un nom");
        } else if (newArticle.getDescription().equals("")) {
            throw new Exception("L'article doit avoir une description");
        } else if (newArticle.getDateDebutEncheres().equals("")) {
            throw new Exception("L'article doit avoir une date de debut d'enchere");
        } else if (newArticle.getDateFinEncheres() == null) {
            throw new Exception("L'article doit avoir une date de fin d'enchere");
        } else if (newArticle.getPrixInitial() == 0) {
            throw new Exception("L'article doit avoir une prix initial");
        } else {
            addedArticle = articleDao.insertArticle(newArticle);
        }
        return addedArticle;
    }

}
















