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

    @Override
    public List<Enchere> selectAllEnchere(Utilisateur utilisateur, String filtreNom, int filtreCategorie) throws SQLException, DalException {
        final String SELECT_ALL_ENCHERE = "select a.id as art_id, a.idUtilisateur as art_idUtilisateur,a.idCategorie as art_idCategorie, a.idRetrait as art_idRetrait, nom ,\n" +
                "description, dateDebutEncheres, dateFinEncheres, prixInitial, prixVente, e.id as ench_id, e.idArticle as ench_idArticle, \n" +
                "e.idUtilisateur as ench_idUtilisateur, dateEnchere, montantEnchere from ARTICLES a" +
                "        LEFT JOIN ENCHERES e on  a.id =  e.idArticle and  e.id = ( select max(e.id) from ENCHERES e where a.id =  e.idArticle)" +
                "        where a.dateDebutEncheres <= getdate() and a.dateFinEncheres > getdate()";
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
            PreparedStatement requete = connection.prepareStatement(SELECT_ALL_ENCHERE + filtreNom + stringFiltreCategorie);
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
    public List<Enchere> selectEnchereVendeur(Utilisateur utilisateur, String filtreNom, int filtreCategorie) throws SQLException, DalException {
        final String SELECT_ENCHERE_BY_USER = "select a.id ,a.idUtilisateur as art_idUtilisateur,a.idCategorie as art_idCategorie, a.idRetrait as art_idRetrait, nom \n" +
                "       , dateDebutEncheres, dateFinEncheres, prixInitial, prixVente, e.id , e.idArticle as ench_idArticle,\n" +
                "       e.idUtilisateur as ench_idUtilisateur, dateEnchere, montantEnchere from ARTICLES a        LEFT JOIN ENCHERES e on  a.id =  e.idArticle and  e.id = ( select max(e.id) from ENCHERES e where a.id =  e.idArticle)\n" +
                "where a.dateDebutEncheres <= getdate() and a.dateFinEncheres > getdate()\n" +
                "and a.idUtilisateur = ?";
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
    public List<Enchere> selectEnchereVendeurFutur(Utilisateur utilisateur, String filtreNom, int filtreCategorie) throws SQLException, DalException {
        final String SELECT_ENCHERE_BY_USER = "select a.id ,a.idUtilisateur as art_idUtilisateur,a.idCategorie as art_idCategorie, a.idRetrait as art_idRetrait, nom \n" +
                "       , dateDebutEncheres, dateFinEncheres, prixInitial, prixVente, e.id , e.idArticle as ench_idArticle,\n" +
                "       e.idUtilisateur as ench_idUtilisateur, dateEnchere, montantEnchere from ARTICLES a        LEFT JOIN ENCHERES e on  a.id =  e.idArticle and  e.id = ( select max(e.id) from ENCHERES e where a.id =  e.idArticle)\n" +
                "where a.dateDebutEncheres > getdate() and a.dateFinEncheres > getdate()\n" +
                "and a.idUtilisateur = ?";
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
    public List<Enchere> selectEnchereVendeurTermine(Utilisateur utilisateur, String filtreNom, int filtreCategorie) throws SQLException, DalException {
        final String SELECT_ENCHERE_BY_USER = "select a.id ,a.idUtilisateur as art_idUtilisateur,a.idCategorie as art_idCategorie, a.idRetrait as art_idRetrait, nom \n" +
                "       , dateDebutEncheres, dateFinEncheres, prixInitial, prixVente, e.id , e.idArticle as ench_idArticle,\n" +
                "       e.idUtilisateur as ench_idUtilisateur, dateEnchere, montantEnchere from ARTICLES a        LEFT JOIN ENCHERES e on  a.id =  e.idArticle and  e.id = ( select max(e.id) from ENCHERES e where a.id =  e.idArticle)\n" +
                "where a.dateFinEncheres <= getdate()\n" +
                "and a.idUtilisateur = ?";
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
        Enchere enchereRetourner = new Enchere();
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
            logger.severe("Error method selectByIdArticle Enchere " + e.getMessage() + "\n");
            throw new DalException(e.getMessage(), e);
        }
        return enchereRetourner;
    }

    @Override
    public Enchere selectById(int id) throws SQLException, DalException{
        Enchere enchere = null;
        final String SELECT_BY_ID="SELECT * FROM ENCHERES WHERE id =?";

        try(Connection connection = JdbcConnection.connect()){
            PreparedStatement requete = connection.prepareStatement(SELECT_BY_ID);
            requete.setInt(1,id);
            ResultSet rs = requete.executeQuery();

            //JE RECUPERE TOUTES LES INFORMATION DE LA TABLE ENCHERE
            if(rs.next()){
                Enchere enchereNew = new Enchere();
                enchereNew.setId(rs.getInt("id"));
                Article article = this.getEnchereArticle(rs.getInt("idArticle"));
                enchereNew.setArticle(article);
                Utilisateur utilisateur = this.getEnchereUtilisateur(rs.getInt("idUtilisateur"));
                enchereNew.setUtilisateur(utilisateur);
                enchereNew.setDateEnchere(rs.getDate("dateEnchere"));
                enchereNew.setMontantEnchere(rs.getInt("montantEnchere"));
                enchere = enchereNew;
            }

        }catch (SQLException e){
            logger.severe("Error method selectById Enchere " + e.getMessage() + "\n");
            throw new DalException(e.getMessage(), e);
        }
        return enchere;
    }

    @Override
    public Enchere addNewEnchere(Utilisateur acheteur, int idArticle, int montantEnchere) throws SQLException, DalException {
       //enchere de retour
        Enchere enchereAdd = new Enchere();
        Enchere enchere = FactoryDao.getEnchereDao().selectEnchereByIdArticle(idArticle);
        //varaible pour recupere l'id de la nouvelle enchere
       int idAjout = 0;
       //varaiable pour initialiser la date du jour
       java.sql.Date now = new java.sql.Date( new java.util.Date().getTime() );

       final String INSERT_NEW_ENCHERE ="INSERT INTO ENCHERES (idArticle, idUtilisateur, dateEnchere, montantEnchere) VALUES (?,?,?,?)";

        //prix de vente est soit = au prix initial ou soit supperieur
            if((enchere.getArticle().getPrixInitial() <= enchere.getArticle().getPrixVente()) || (enchere.getArticle().getPrixVente() == 0)){
                //compare le montantEnchere avec le prix article
                if (enchere.getArticle().getPrixVente() < montantEnchere){
                    if(acheteur.getCredit() >= enchere.getArticle().getPrixVente()) {
                        try (Connection connection = JdbcConnection.connect()) {
                            PreparedStatement requete = connection.prepareStatement(INSERT_NEW_ENCHERE, PreparedStatement.RETURN_GENERATED_KEYS);
                            requete.setInt(1, idArticle);
                            requete.setInt(2, acheteur.getId());
                            requete.setDate(3, now);
                            requete.setInt(4, montantEnchere);
                            requete.executeUpdate();
                            ResultSet rs = requete.getGeneratedKeys();
                            if (rs.next()) {
                                idAjout = rs.getInt(1);
                            }
                        } catch (SQLException e) {
                            logger.severe("Error method addNew Enchere " + e.getMessage() + "\n");
                            throw new DalException(e.getMessage(), e);
                        }
                        //new prix de vente
                        int prixVente = montantEnchere + enchere.getArticle().getPrixVente();
                        //mise a jour de l'article apres enchere
                        updateArticleApresEnchere(enchere.getArticle(), montantEnchere);
                        //debit du montant de l'enchere du nouvelle encherisseur
                        deleteCredit(acheteur.getCredit(), montantEnchere, acheteur);
                        //ajout credit de l'enchere precedente a l'encherisseur precedent
                        addCredit(enchere.getUtilisateur().getCredit(), enchere.getMontantEnchere(), enchere.getUtilisateur());
                        //récuperation de l'enchere creer
                        enchereAdd = FactoryDao.getEnchereDao().selectById(idAjout);
                    }else{
                        throw new DalException("credit Acheteur inferieur au montant de l'enchere");
                    }
                }else {
                    throw new DalException("prix de vente supperieur au montant de l'enchere");
                }
            }else{
                throw new DalException("le prix initial est supperieur aux prix de vente ");
            }
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

    private void deleteCredit(int creditAcheteur, int montantEnchere , Utilisateur acheteur) throws SQLException,DalException{
        int credit = creditAcheteur - montantEnchere;
        Utilisateur newUtilisateur = new Utilisateur(acheteur.getPseudo(),acheteur.getNom(),acheteur.getPrenom(),
                acheteur.getEmail(),acheteur.getTelephone(),acheteur.getRue(),acheteur.getCodePostal(),
                acheteur.getVille(), acheteur.getMotDePasse(), credit ,acheteur.getId());

        FactoryDao.getUtilisateurDao().updateUtilisateurApresEnchere(newUtilisateur);

    }

    private void addCredit(int creditUtilisateur, int montantEnchere, Utilisateur acheteur) throws SQLException, DalException{
        int addCredit = creditUtilisateur + montantEnchere;
        Utilisateur UtilisateurACredité = new Utilisateur(acheteur.getPseudo(),acheteur.getNom(),acheteur.getPrenom(),
                acheteur.getEmail(),acheteur.getTelephone(),acheteur.getRue(),acheteur.getCodePostal(),
                acheteur.getVille(), acheteur.getMotDePasse(), addCredit ,acheteur.getId());

        FactoryDao.getUtilisateurDao().updateUtilisateurApresEnchere(UtilisateurACredité);
    }

    private void updateArticleApresEnchere(Article article, int montantEnchere) throws SQLException, DalException{
        Article newArticle = new Article(article.getUtilisateur(), article.getCategorie(),
                article.getRetrait(), article.getNom(), article.getDescription(),
                article.getDateDebutEncheres(), article.getDateFinEncheres(),
                article.getPrixInitial(), montantEnchere, article.getId());

        FactoryDao.getArticleDao().updateArticle(newArticle);
    }
}
