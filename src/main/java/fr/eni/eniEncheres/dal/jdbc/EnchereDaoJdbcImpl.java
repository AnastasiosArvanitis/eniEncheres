package fr.eni.eniEncheres.dal.jdbc;

import fr.eni.eniEncheres.bo.Article;
import fr.eni.eniEncheres.bo.Enchere;
import fr.eni.eniEncheres.bo.Utilisateur;
import fr.eni.eniEncheres.dal.DalException;
import fr.eni.eniEncheres.dal.FactoryDao;
import fr.eni.eniEncheres.dal.dao.ArticleDao;
import fr.eni.eniEncheres.dal.dao.EnchereDao;
import fr.eni.eniEncheres.dal.dao.UtilisateurDao;
import fr.eni.eniEncheres.dal.jdbcTools.JdbcConnection;
import fr.eni.eniEncheres.tools.EnchereLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class EnchereDaoJdbcImpl implements EnchereDao {

    private Logger logger = EnchereLogger.getLogger("EnchereDaoJdbcImpl");

    @Override
    public List<Enchere> selectAllEnchere() throws SQLException, DalException {
        List<Enchere> enchereList = new ArrayList<>();
        final String SELECT_ALL_ENCHERE = "select a.*,e.* from ARTICLES a" +
        "        LEFT JOIN ENCHERES e on  a.id =  e.idArticle and  e.id = ( select max(e.id) from ENCHERES e where a.id =  e.idArticle)" +
                "        where a.dateDebutEncheres <= getdate() and a.dateFinEncheres > getdate()";
        try(Connection connection = JdbcConnection.connect()){
            PreparedStatement requete = connection.prepareStatement(SELECT_ALL_ENCHERE);
            ResultSet rs = requete.executeQuery();
            while (rs.next()){
                Enchere enchere = new Enchere();
               enchere = enchereBuilder(rs);
               enchereList.add(enchere);
            }
        }catch (SQLException e){
            logger.severe("Error selectAllEnchere JDBC " + e.getMessage() + "\n");
            throw new DalException(e.getMessage(), e);

        }
        return enchereList;
    }

    @Override
    public List<Enchere> selectEnchereByUtilisateur(Utilisateur utilisateur) throws SQLException, DalException {
        return null;
    }

    @Override
    public List<Enchere> selectEnchereByArticle(Article article) throws SQLException, DalException {
        return null;
    }

    @Override
    public Enchere selectById(int id) throws SQLException, DalException {
        return null;
    }

    @Override
    public Enchere addNewEnchere() throws SQLException, DalException {
        return null;
    }

    @Override
    public Enchere terminerEnchere() throws SQLException, DalException {
        return null;
    }


    private Utilisateur getEnchereUtilisateur(int utilisateurId) throws SQLException, DalException  {
        Utilisateur enchereUtilisateur = null;
        UtilisateurDao utilisateurDao = FactoryDao.getUtilisateurDao();
        try {
            enchereUtilisateur = utilisateurDao.selectById(utilisateurId);
        } catch (SQLException e) {
            logger.severe("Error getEnchereUtilisateur " + e.getMessage() + "\n");
            throw new DalException( e.getMessage(), e);
        }

        return enchereUtilisateur;
    }

    private Article getEnchereArticle(int articleId) throws SQLException, DalException{
        Article enchereArticle = null;
        ArticleDao articleDao = FactoryDao.getArticleDao();
        try{
            enchereArticle = articleDao.selectArticleById(articleId);
        }catch (SQLException e){
            logger.severe("Error getEnchereArticle  " + e.getMessage() + "\n");
            throw new DalException( e.getMessage(), e);
        }
        return enchereArticle;
    }

    private Enchere enchereBuilder(ResultSet rs) throws SQLException, DalException{
        Enchere enchere = new Enchere();
        enchere.setId(rs.getInt("id"));
        Article article = this.getEnchereArticle(rs.getInt("id"));
        enchere.setArticle(article);
        Utilisateur utilisateur = this.getEnchereUtilisateur(rs.getInt("id"));
        enchere.setUtilisateur(utilisateur);
        enchere.setDateEnchere(rs.getDate("dateEnchere"));
        enchere.setMontantEnchere(rs.getInt("montantEnchere"));

        return enchere;
    }


}
