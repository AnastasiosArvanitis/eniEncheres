package fr.eni.eniEncheres.dal.jdbc;

import fr.eni.eniEncheres.bo.Article;
import fr.eni.eniEncheres.bo.Categorie;
import fr.eni.eniEncheres.bo.Retrait;
import fr.eni.eniEncheres.bo.Utilisateur;
import fr.eni.eniEncheres.dal.DalException;
import fr.eni.eniEncheres.dal.FactoryDao;
import fr.eni.eniEncheres.dal.dao.ArticleDao;
import fr.eni.eniEncheres.dal.dao.CategorieDao;
import fr.eni.eniEncheres.dal.dao.RetraitDao;
import fr.eni.eniEncheres.dal.dao.UtilisateurDao;
import fr.eni.eniEncheres.dal.jdbcTools.JdbcConnection;
import fr.eni.eniEncheres.tools.EnchereLogger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.Date;

public class ArticleDaoJdbcImpl implements ArticleDao {

    private Logger logger = EnchereLogger.getLogger("ArticleDaoJdbcImpl");

    @Override
    public Article selectArticleById(int articleId) throws SQLException, DalException {
        Article article = null;
        final String SELECT_ARTICLE_BY_ID = "select * from ARTICLES where id = ?";

        try {
            Connection connection = JdbcConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ARTICLE_BY_ID);
            preparedStatement.setInt(1, articleId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                article = articleBuilder(resultSet);
            }
        } catch (SQLException e) {
            logger.severe("Error method selectArticleById " + e.getMessage() + "\n");
            throw new DalException(e.getMessage(), e);
        }

        return article;
    }


    @Override
    public List<Article> selectAllArticles() throws SQLException, DalException {
        List<Article> articles = new ArrayList<>();
        final String SELECT_ALL = "select * from ARTICLES";

        try {
            Connection connection = JdbcConnection.connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                articles.add(articleBuilder(resultSet));
            }
        } catch (SQLException e) {
            logger.severe("Error method selectAllArticles " + e.getMessage() + "\n");
            throw new DalException(e.getMessage(), e);
        }
        return articles;
    }

    @Override
    public List<Article> selectAllArticlesByUtilisateur(Utilisateur utilisateur) throws SQLException, DalException {
        List<Article> returnedArticles = new ArrayList<>();
        final String SELECT_BY_USER = "select * from ARTICLES where idUtilisateur = ? ";

        try {
            Connection connection = JdbcConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_USER);
            preparedStatement.setInt(1, utilisateur.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                returnedArticles.add(articleBuilder(resultSet));
            }
        } catch (SQLException e) {
            logger.severe("Error method selectAllArticlesByUtilisateur " + e.getMessage() + "\n");
            throw new DalException(e.getMessage(), e);
        }


        return returnedArticles;
    }


    @Override
    public Article insertArticle(Article ajoutArticle) throws SQLException, DalException {
        Article articleCree = null;
        Retrait retraitCree = null;
        Retrait retraitReturne = null;
        final String SQL_INSERT = "insert into ARTICLES (idUtilisateur, idCategorie, idRetrait," +
                "nom, description, dateDebutEncheres," +
                "dateFinEncheres, prixInitial, prixVente) values (?,?,?,?,?,?,?,?,?)";
        int idArticleAjout = 0;
        int idRetraitAjout = 0;
        String rueUtilisateur = ajoutArticle.getRetrait().getRue();
        String codePostalUtilisateur = ajoutArticle.getRetrait().getCodePostal();
        String villeUtilisateur = ajoutArticle.getRetrait().getVille();

        try {
            Connection connection = JdbcConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(4, ajoutArticle.getNom());
            preparedStatement.setString(5, ajoutArticle.getDescription());
            preparedStatement.setObject(6, ajoutArticle.getDateDebutEncheres());
            preparedStatement.setObject(7, ajoutArticle.getDateFinEncheres());
            preparedStatement.setInt(8, ajoutArticle.getPrixInitial());
            if (ajoutArticle.getPrixVente() != 0) {
                preparedStatement.setInt(9, ajoutArticle.getPrixVente());
            } else {
                preparedStatement.setNull(9, Types.INTEGER);
            }
            retraitCree = new Retrait(rueUtilisateur, codePostalUtilisateur, villeUtilisateur);
            retraitReturne = FactoryDao.getRetraitDao().insertRetrait(retraitCree);
            idRetraitAjout = retraitReturne.getId();
            preparedStatement.setInt(3, idRetraitAjout);
            preparedStatement.setInt(2, ajoutArticle.getCategorie().getId());
            preparedStatement.setInt(1, ajoutArticle.getUtilisateur().getId());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                idArticleAjout = resultSet.getInt(1);
                articleCree = selectArticleById(idArticleAjout);
            }
        } catch (SQLException e) {
            logger.severe("Error method insertArticle " + e.getMessage() + "\n");
            throw new DalException(e.getMessage(), e);
        }
        return articleCree;
    }

    @Override
    public Article updateArticle(Article updateArticle) throws SQLException, DalException {
        Article articleUpdate = null;
        final String UPDATE_ARTICLE = "UPDATE ARTICLES SET idUtilisateur = ?, idCategorie = ?, idRetrait = ?," +
                "nom = ?, description = ?, dateDebutEncheres = ?, dateFinEncheres = ?, prixInitial = ?, " +
                "prixVente = ? WHERE id = ?";

        try (Connection connection = JdbcConnection.connect()) {
            PreparedStatement requete = connection.prepareStatement(UPDATE_ARTICLE);
            requete.setInt(1, updateArticle.getUtilisateur().getId());
            requete.setInt(2, updateArticle.getCategorie().getId());
            requete.setInt(3, updateArticle.getRetrait().getId());
            requete.setString(4, updateArticle.getNom());
            requete.setString(5, updateArticle.getDescription());
            requete.setObject(6, updateArticle.getDateDebutEncheres());
            requete.setObject(7, updateArticle.getDateFinEncheres());
            requete.setInt(8, updateArticle.getPrixInitial());
            requete.setInt(9, updateArticle.getPrixVente());
            requete.setInt(10, updateArticle.getId());
            requete.executeUpdate();
            articleUpdate = FactoryDao.getArticleDao().selectArticleById(updateArticle.getId());
        } catch (SQLException e) {
            logger.severe("Error method updateArticle " + e.getMessage() + "\n");
            throw new DalException(e.getMessage(), e);
        }
        return articleUpdate;
    }

    private Article articleBuilder(ResultSet rs) throws SQLException, DalException {
        Categorie articleCategorie = this.getArticleCategorie(rs.getInt("idCategorie"));
        Retrait articleRetrait = this.getArticleRetrait(rs.getInt("idRetrait"));
        Utilisateur articleUtilisateur = this.getArticleUtilisateur(rs.getInt("idUtilisateur"));

        Article article = new Article();
        article.setId(rs.getInt("id"));
        article.setCategorie(articleCategorie);
        article.setRetrait(articleRetrait);
        article.setUtilisateur(articleUtilisateur);
        article.setNom(rs.getString("nom"));
        article.setDescription(rs.getString("description"));
        article.setDateDebutEncheres((Timestamp) rs.getObject("dateDebutEncheres"));
        article.setDateFinEncheres((Timestamp) rs.getObject("dateFinEncheres"));
        article.setPrixInitial(rs.getInt("prixInitial"));
        article.setPrixVente(rs.getInt("prixVente"));

        return article;
    }

    private Categorie getArticleCategorie(int categorieId) throws SQLException, DalException {
        Categorie articleCategorie = null;
        CategorieDao categorieDao = FactoryDao.getCategorieDao();
        try {
            articleCategorie = categorieDao.selectCategorieByID(categorieId);
        } catch (SQLException e) {
            logger.severe("Error getArticleCategorie... " + e.getMessage() + "\n");
            throw new DalException(e.getMessage(), e);
        }

        return articleCategorie;
    }

    private Retrait getArticleRetrait(int retraitId) throws SQLException, DalException {
        Retrait articleRetrait = null;
        RetraitDao retraitDao = FactoryDao.getRetraitDao();
        try {
            articleRetrait = retraitDao.selectRetraitById(retraitId);
        } catch (SQLException e) {
            logger.severe("Error getArticleRetrait... " + e.getMessage() + "\n");
            throw new DalException(e.getMessage(), e);
        }

        return articleRetrait;
    }

    private Utilisateur getArticleUtilisateur(int utilisateurId) throws SQLException, DalException {
        Utilisateur articleUtilisateur = null;
        UtilisateurDao utilisateurDao = FactoryDao.getUtilisateurDao();
        try {
            articleUtilisateur = utilisateurDao.selectById(utilisateurId);
        } catch (SQLException e) {
            logger.severe("Error getArticleUtilisateur... " + e.getMessage() + "\n");
            throw new DalException(e.getMessage(), e);
        }

        return articleUtilisateur;
    }

    /**
     * @param deleteArticle Récupère un boolean si l'execution du delete est ok
     * @return
     * @throws DalException
     */
    public boolean deleteArticle(Article articleSuppression) throws  SQLException, DalException{

        boolean effacerArticle = false;
        final String SQL_DELETE = "DELETE ARTICLES WHERE ID = ?";

        try {
            Connection connection = JdbcConnection.connect();
            PreparedStatement stmt = connection.prepareStatement(SQL_DELETE);
            stmt.setInt(1, articleSuppression.getId());
            effacerArticle = (!(stmt.executeUpdate() == 0));

        } catch (SQLException e) {
            logger.severe("Error method DeleteArticle " + e.getMessage() + "\n");
            throw new DalException(e.getMessage(), e);
        }
        return effacerArticle;
    }



}





















