package fr.eni.eniEncheres.dal.dao;

import fr.eni.eniEncheres.bo.Article;
import fr.eni.eniEncheres.bo.Utilisateur;
import fr.eni.eniEncheres.dal.DalException;

import java.sql.SQLException;
import java.util.List;

public interface ArticleDao {

    Article selectArticleById(int articleId) throws SQLException, DalException;

    List<Article> selectAllArticles() throws SQLException, DalException;

    List<Article> selectAllArticlesByUtilisateur(Utilisateur utilisateur) throws SQLException, DalException;

    Article insertArticle(Article article) throws SQLException, DalException;

}
