package fr.eni.eniEncheres.dal;

import fr.eni.eniEncheres.dal.dao.ArticleDao;
import fr.eni.eniEncheres.dal.dao.CategorieDao;
import fr.eni.eniEncheres.dal.dao.RetraitDao;
import fr.eni.eniEncheres.dal.dao.UtilisateurDao;
import fr.eni.eniEncheres.dal.jdbc.ArticleDaoJdbcImpl;
import fr.eni.eniEncheres.dal.jdbc.CategorieDaoJdbcImpl;
import fr.eni.eniEncheres.dal.jdbc.RetraitDaoJdbcImpl;
import fr.eni.eniEncheres.dal.jdbc.UtilisateurDaoJdbcImpl;

public class FactoryDao {

    public static UtilisateurDao getUtilisateurDao() {
        return new UtilisateurDaoJdbcImpl();
    }

    public static CategorieDao getCategorieDao() { return new CategorieDaoJdbcImpl();}

    public static RetraitDao getRetraitDao() { return  new RetraitDaoJdbcImpl(); }

    public static ArticleDao getArticleDao() { return new ArticleDaoJdbcImpl(); }
}
