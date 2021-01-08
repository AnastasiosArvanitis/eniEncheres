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

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import  java.sql.Date;
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
        System.out.println(SELECT_ALL_ENCHERE);
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
    public Enchere selectEnchereByIdArticle(int idArticle) throws SQLException, DalException {
        Enchere enchereRetourner = null;
        //selectionne l'article avec sont enchere maximum dans une ligne
        final String SELECT_BY_ID = "select a.id as art_id, a.idUtilisateur as art_idUtilisateur,a.idCategorie as art_idCategorie, a.idRetrait as art_idRetrait, nom ," +
                "                description, dateDebutEncheres, dateFinEncheres, prixInitial, prixVente, e.id as ench_id, e.idArticle as ench_idArticle," +
                "                e.idUtilisateur as ench_idUtilisateur, dateEnchere, montantEnchere from ARTICLES a" +
                "                LEFT JOIN ENCHERES e on  a.id =  e.idArticle and  e.id = ( select max(e.id) from ENCHERES e where a.id =  e.idArticle)"+
                "                        where a.dateDebutEncheres <= getdate() and a.dateFinEncheres > getdate() and a.id = ?";
        try(Connection connection = JdbcConnection.connect()){
            PreparedStatement requete = connection.prepareStatement(SELECT_BY_ID);
            requete.setInt(1, idArticle);
            ResultSet rs = requete.executeQuery();
            if (rs.next()){
                enchereRetourner = enchereBuilder(rs);
            }
        }catch(SQLException e){
            logger.severe("Error method selectById Enchere " + e.getMessage() + "\n");
            throw new DalException(e.getMessage(), e);
        }
        return enchereRetourner;
    }

    @Override
    public Enchere addNewEnchere(Utilisateur acheteur, int idArticle, int montantEnchere) throws SQLException, DalException {
       Enchere enchereAdd = new Enchere();
       Enchere enchere = null;
       Article article = null;
       ArticleDaoJdbcImpl articleDaoJdbc = new ArticleDaoJdbcImpl();
        java.sql.Date now = new java.sql.Date( new java.util.Date().getTime() );
       final String INSERT_NEW_ENCHERE ="INSERT INTO ENCHERES (idArticle, idUtilisateur, dateEnchere, montantEnchere) VALUES (?,?,?,?)";

       enchere = selectEnchereByIdArticle(idArticle);
       article = articleDaoJdbc.selectArticleById(idArticle);
        //enchere recuperer a soit un montant null ou soit un montant

            if( article.getPrixVente() == 0){
                System.out.println("enchere montant = 0");
                //compare le montantEnchere avec le prix article
                if (enchere.getMontantEnchere() < montantEnchere){
                    System.out.println("controle avant connexion");
                    try(Connection connection = JdbcConnection.connect()){
                        PreparedStatement requete = connection.prepareStatement(INSERT_NEW_ENCHERE, PreparedStatement.RETURN_GENERATED_KEYS);
                        requete.setInt(1,idArticle);
                        requete.setInt(2, acheteur.getId());
                        requete.setDate(3, now);
                        requete.setInt(4, montantEnchere);
                        requete.executeUpdate();
                        ResultSet rs = requete.getGeneratedKeys();
                        System.out.println("controle avant le if");

                        if(rs.next()){
                            Enchere enchereadd = new Enchere();
                            enchereadd.setId(rs.getInt("id"));
                            Article articleadd = this.getEnchereArticle(rs.getInt("idArticle"));
                            enchereadd.setArticle(articleadd);
                            Utilisateur utilisateur = this.getEnchereUtilisateur(rs.getInt("idUtilisateur"));
                            enchereadd.setUtilisateur(utilisateur);
                            enchereadd.setDateEnchere(rs.getDate("dateEnchere"));
                            enchereadd.setMontantEnchere(rs.getInt("montantEnchere"));
                            enchereAdd = enchereadd;
                        }
                        System.out.println("controle apres le if");
                    }catch (SQLException e){
                        logger.severe("Error method addNew Enchere " + e.getMessage() + "\n");
                        throw new DalException(e.getMessage(), e);
                    }
                }
            }else{
                //compare le montantEnchere avec le montant enchere precedente
                if(enchere.getMontantEnchere() < montantEnchere){
                    System.out.println("enchere montant > montant nouvelle enchere");
                }
            }

        //CONTROLE
        //si pas d'enchere compare le montant saisi > miontant article
        //sinon montant > montant recuperer par enchere par id


        //re ajout le montant de l'enchere du precedent utilisateur a sont compte
        //on enleve le montant de l'enchere du nouveau utilisateur
        //ajout le nouveau montant a la base de donné
        //retourne le nouvelle enchere
        return enchereAdd;
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
        Article article = this.getEnchereArticle(rs.getInt("art_id"));
        enchere.setArticle(article);
        Utilisateur utilisateur = this.getEnchereUtilisateur(rs.getInt("ench_idUtilisateur"));
        enchere.setUtilisateur(utilisateur);
        enchere.setDateEnchere(rs.getDate("dateEnchere"));
        enchere.setMontantEnchere(rs.getInt("montantEnchere"));

        return enchere;
    }


}
