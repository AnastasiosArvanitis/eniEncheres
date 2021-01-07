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
        final String SELECT_ALL_ENCHERE = "select a.id as art_id, a.idUtilisateur as art_idUtilisateur,a.idCategorie as art_idCategorie, a.idRetrait as art_idRetrait, nom ,\n" +
                "description, dateDebutEncheres, dateFinEncheres, prixInitial, prixVente, e.id as ench_id, e.idArticle as ench_idArticle, \n" +
                "e.idUtilisateur as ench_idUtilisateur, dateEnchere, montantEnchere from ARTICLES a" +
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

    /**
     *
     * @param
     * @apiNote  récupérer la liste d'enchêre en cours ou l'utilisateur a participé ( au moins une enchère)
     * avec filtre en entrée pour ajouter ou non le controle par catégorie et saisie nom
     * @return
     * @throws SQLException
     * @throws DalException
     */
    @Override
    public List<Enchere> selectEnchereByUtilisateur(Utilisateur utilisateur, String filtreNom, int filtreCategorie) throws SQLException, DalException {
        final String SELECT_ENCHERE_BY_USER = "select a.id as art_id, a.idUtilisateur as art_idUtilisateur,a.idCategorie as art_idCategorie, a.idRetrait as art_idRetrait, nom ,\n" +
                "description, dateDebutEncheres, dateFinEncheres, prixInitial, prixVente, e.id as ench_id, e.idArticle as ench_idArticle, \n" +
                "e.idUtilisateur as ench_idUtilisateur, dateEnchere, montantEnchere from ARTICLES a\n" +
                "        INNER JOIN ENCHERES e on  a.id =  e.idArticle and  e.id = ( select max(e.id) from ENCHERES e where a.id =  e.idArticle)\n" +
                "where a.dateDebutEncheres <= getdate() and a.dateFinEncheres > getdate()\n" +
                "and (exists (select  * from ENCHERES u where u.idArticle = e.idArticle and u.idUtilisateur = ? ))";
        String stringFiltreCategorie = "";
        List<Enchere> enchereList = new ArrayList<>();
        if (!filtreNom.equals("0")) {
            filtreNom = "and a.nom like '%" + filtreNom + "%' ";
        } else {
            filtreNom = "";
        }
        if (!(filtreCategorie == 0)) {
            stringFiltreCategorie = "and a.idCategorie =" + Integer.toString(filtreCategorie) + " ";
        }
        try (Connection connection = JdbcConnection.connect()) {
            PreparedStatement requete = connection.prepareStatement(SELECT_ENCHERE_BY_USER + filtreNom + stringFiltreCategorie);
            requete.setInt(1, utilisateur.getId());
            System.out.println(requete.toString());
            ResultSet rs = requete.executeQuery();
            while (rs.next()) {
                Enchere enchere = new Enchere();
                enchere = enchereBuilder(rs);
                enchereList.add(enchere);
            }
        } catch (SQLException e) {
            logger.severe("Error selectAllEnchere JDBC " + e.getMessage() + "\n");
            throw new DalException(e.getMessage(), e);
        }
        return enchereList;
    }

    @Override
    public List<Enchere> selectEnchereVictoire(Utilisateur utilisateur, String filtreNom, int filtreCategorie) throws SQLException, DalException {
        final String SELECT_ENCHERE_BY_USER = "select a.id as art_id, a.idUtilisateur as art_idUtilisateur,a.idCategorie as art_idCategorie, a.idRetrait as art_idRetrait, nom ,\n" +
                "description, dateDebutEncheres, dateFinEncheres, prixInitial, prixVente, e.id as ench_id, e.idArticle as ench_idArticle, \n" +
                "e.idUtilisateur as ench_idUtilisateur, dateEnchere, montantEnchere from ARTICLES a\n" +
                "INNER JOIN ENCHERES e on  a.id =  e.idArticle and  e.id = ( select max(e.id) from ENCHERES e where a.id =  e.idArticle) and e.idUtilisateur=? \n" +
                "where  a.dateFinEncheres <= getdate()";
        String stringFiltreCategorie = "";
        List<Enchere> enchereList = new ArrayList<>();
        if (!filtreNom.equals("0")) {
            filtreNom = "and a.nom like '%" + filtreNom + "%' ";
        } else {
            filtreNom = "";
        }
        if (!(filtreCategorie == 0)) {
            stringFiltreCategorie = "and a.idCategorie =" + Integer.toString(filtreCategorie) + " ";
        }

        try (Connection connection = JdbcConnection.connect()) {
            PreparedStatement requete = connection.prepareStatement(SELECT_ENCHERE_BY_USER + filtreNom + stringFiltreCategorie);
            requete.setInt(1, utilisateur.getId());
            ResultSet rs = requete.executeQuery();
            while (rs.next()) {
                Enchere enchere = new Enchere();
                enchere = enchereBuilder(rs);
                enchereList.add(enchere);
            }
        } catch (SQLException e) {
            logger.severe("Error selectAllEnchere JDBC " + e.getMessage() + "\n");
            throw new DalException(e.getMessage(), e);
        }
        return enchereList;
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
        enchere.setId(rs.getInt("ench_id"));
        Article article = this.getEnchereArticle(rs.getInt("ench_idArticle"));
        enchere.setArticle(article);
        Utilisateur utilisateur = this.getEnchereUtilisateur(rs.getInt("ench_idUtilisateur"));
        enchere.setUtilisateur(utilisateur);
        enchere.setDateEnchere(rs.getDate("dateEnchere"));
        enchere.setMontantEnchere(rs.getInt("montantEnchere"));

        return enchere;
    }


}
