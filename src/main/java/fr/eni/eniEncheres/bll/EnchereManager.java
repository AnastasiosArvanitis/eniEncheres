package fr.eni.eniEncheres.bll;

import fr.eni.eniEncheres.bo.Enchere;
import fr.eni.eniEncheres.bo.Utilisateur;
import fr.eni.eniEncheres.dal.DalException;
import fr.eni.eniEncheres.dal.FactoryDao;
import fr.eni.eniEncheres.dal.dao.EnchereDao;
import fr.eni.eniEncheres.tools.EnchereLogger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class EnchereManager {

    private static EnchereManager instance;
    private EnchereDao enchereDao;
    private Logger logger = EnchereLogger.getLogger("CategorieManager");

    //Constructeur privé
    private EnchereManager() {
        enchereDao = FactoryDao.getEnchereDao();
    }

    //Permet de récupérer l'instance (créee une seule fois)
    public static EnchereManager getInstance() {
        if (instance == null) {
            return new EnchereManager();
        }
        return instance;
    }

    public List<Enchere> selectAllEnchere() throws SQLException, BllException {
        List<Enchere> listEnchere = new ArrayList<>();
        try {
            listEnchere = enchereDao.selectAllEnchere();
        } catch (SQLException | DalException e) {
            logger.severe("Error dans selectAll EnchereManager " + e.getMessage());
            throw new BllException(e.getMessage(), e);
        }
        return listEnchere;
    }

    public List<Enchere> selectEnchereVictoire(Utilisateur utilisateur, String filtreNom, int filtreCategorie) throws SQLException, DalException, BllException {
        List<Enchere> listEnchere = new ArrayList<>();
        try {
            listEnchere = enchereDao.selectEnchereVictoire(utilisateur, filtreNom, filtreCategorie);
        } catch (SQLException | DalException e) {
            logger.severe("Error dans selectAll EnchereManager " + e.getMessage());
            throw new BllException(e.getMessage(), e);
        }
        return listEnchere;
    }

    public List<Enchere> selectEnchereByUtilisateur(Utilisateur utilisateur, String filtreNom, int filtreCategorie) throws SQLException, DalException, BllException {
        List<Enchere> listEnchere = new ArrayList<>();
        try {
            listEnchere = enchereDao.selectEnchereByUtilisateur(utilisateur, filtreNom, filtreCategorie);
        } catch (SQLException | DalException e) {
            logger.severe("Error dans selectAll EnchereManager " + e.getMessage());
            throw new BllException(e.getMessage(), e);
        }
        return listEnchere;
    }

    public Enchere getEnchereArticle(int articleId) throws SQLException, DalException, BllException {
        Enchere enchere = new Enchere();

        try {
            enchere = enchereDao.selectEnchereByIdArticle(articleId);
        } catch (SQLException | DalException e) {
            logger.severe("Error dans selectAll EnchereManager " + e.getMessage());
            throw new BllException(e.getMessage(), e);
        }
        return enchere;
    }

    public Enchere addNewEnchere(Utilisateur acheteur, int idArticle, int montantEnchere) throws SQLException, BllException, DalException {
        Enchere enchereRetourner = null;
        Enchere enchere = enchereDao.selectEnchereByIdArticle(idArticle);

        //prix de vente est soit = au prix initial ou soit supperieur
        if ((enchere.getArticle().getPrixInitial() <= enchere.getArticle().getPrixVente()) || (enchere.getArticle().getPrixVente() == 0)) {
            //compare le montantEnchere avec le prix article
            if (enchere.getArticle().getPrixVente() < montantEnchere) {
                //controle pour savoir si l'acheteur a deja fais la derniere enchere
                if ((enchere.getUtilisateur() == null) || (acheteur.getId() != enchere.getUtilisateur().getId())) {
                    //controle pour savoir si le credit de l'utilisateur est superrieur au prix de vente
                    if (acheteur.getCredit() >= enchere.getArticle().getPrixVente()) {
                        try {
                            enchereRetourner = enchereDao.addNewEnchere(acheteur, idArticle, montantEnchere);
                        } catch (SQLException | DalException e) {
                            logger.severe("Error dans addNewEncher EnchereManager " + e.getMessage());
                            throw new BllException(e.getMessage(), e);
                        }
                    } else {
                        new BllException("Votre Credit est inferieur au montant de l'enchere");
                    }
                } else {
                    throw new DalException("Vous etes deja le dernier encherisseur");
                }
            } else {
                new BllException("Prix de vente supperieur au montant de l'enchere");
            }
        } else {
            new BllException("le prix initial est supperieur aux prix de vente");
        }
        return enchereRetourner;
    }

    public List<Enchere> getEnchereVendeur(Utilisateur utilisateur, String filtreNom, int filtreCategorie) throws BllException {
        List<Enchere> enchereRetourner = null;
        try {
            enchereRetourner = enchereDao.selectEnchereVendeur(utilisateur, filtreNom, filtreCategorie);
        } catch (SQLException | DalException e) {
            logger.severe("Error dans getEnchereVendeur EnchereManager " + e.getMessage());
            throw new BllException(e.getMessage(), e);
        }
        return enchereRetourner;
    }

    public List<Enchere> getEnchereVendeurFutur(Utilisateur utilisateur, String filtreNom, int filtreCategorie) throws BllException {
        List<Enchere> enchereRetourner = null;
        try {
            enchereRetourner = enchereDao.selectEnchereVendeurFutur(utilisateur, filtreNom, filtreCategorie);
        } catch (SQLException | DalException e) {
            logger.severe("Error dans getEnchereVendeurFutur EnchereManager " + e.getMessage());
            throw new BllException(e.getMessage(), e);
        }
        return enchereRetourner;
    }

    public List<Enchere> getEnchereVendeurTermine(Utilisateur utilisateur, String filtreNom, int filtreCategorie) throws BllException {
        List<Enchere> enchereRetourner = null;
        try {
            enchereRetourner = enchereDao.selectEnchereVendeurTermine(utilisateur, filtreNom, filtreCategorie);
        } catch (SQLException | DalException e) {
            logger.severe("Error dans getEnchereVendeurTermine EnchereManager " + e.getMessage());
            throw new BllException(e.getMessage(), e);
        }
        return enchereRetourner;
    }

    /*requete pour afficher les choix d'affichage d'un future acheteur non connecter*/
    public List<Enchere> afficherRequete(String nomTitreArticle, int idCategorie) throws SQLException, DalException, BllException {
        List<Enchere> listeEnchere = new ArrayList<>();

        String condition = "";
        String conditionCategorie = "";
        String conditionNom = "";

        switch (idCategorie) {
            case 0:
                conditionCategorie = "where a.dateFinEncheres > GETDATE() and a.dateDebutEncheres <= GETDATE()";
                break;
            case 1:
                conditionCategorie = "where a.dateFinEncheres > GETDATE() and a.dateDebutEncheres <= GETDATE() AND c.id = 1";
                break;
            case 2:
                conditionCategorie = "where a.dateFinEncheres > GETDATE() and a.dateDebutEncheres <= GETDATE() AND c.id = 2";
                break;
            case 3:
                conditionCategorie = "where a.dateFinEncheres > GETDATE() and a.dateDebutEncheres <= GETDATE() AND c.id = 3";
                break;
            case 4:
                conditionCategorie = "where a.dateFinEncheres > GETDATE() and a.dateDebutEncheres <= GETDATE() AND c.id = 4";
                break;
            default:
                conditionCategorie = "where a.dateFinEncheres > GETDATE() and a.dateDebutEncheres <= GETDATE()";
        }

        if (!nomTitreArticle.equals("0")) {
            conditionNom = " AND a.nom LIKE '%" + nomTitreArticle + "%'";
        } else {
            conditionNom = "";
        }
        condition = conditionCategorie + conditionNom;

        try {
            listeEnchere = enchereDao.afficherRequete(condition);
        } catch (SQLException | DalException e) {
            logger.severe("Error dans afficherRequete EnchereManager " + e.getMessage());
            throw new BllException(e.getMessage(), e);
        }
        return listeEnchere;
    }


    /*requete pour afficher les choix d'affichage d'un future acheteur CONNECTER*/

    public List<Enchere> afficherRequetCo(String nomTitreArticle, int idCategorie, int checkbox, int idUtilisateur) throws SQLException, DalException, BllException {
        List<Enchere> listeEnchere = new ArrayList<>();

        String condition = "";
        String conditionCategorie = "";
        String conditionCheckBox = "";
        String conditionNom = " ";

        switch (idCategorie) {
            case 0:
                conditionCategorie = "";
                break;
            case 1:
                conditionCategorie = " AND c.id = 1";
                break;
            case 2:
                conditionCategorie = " AND c.id = 2";
                break;
            case 3:
                conditionCategorie = " AND c.id = 3";
                break;
            case 4:
                conditionCategorie = " AND c.id = 4";
                break;
            default:
                conditionCategorie = "";
        }
        switch (checkbox) {
            //enchereOuvert
            case 1:
            case 4:
                conditionCheckBox = " where a.dateFinEncheres > GETDATE() and a.dateDebutEncheres <= GETDATE() and a.idUtilisateur != " + idUtilisateur;
                break;
            //enchereEnCours
            case 2:
                conditionCheckBox = " where a.dateFinEncheres > GETDATE() and a.dateDebutEncheres <= GETDATE()  AND a.idUtilisateur != " + idUtilisateur + " AND exists(select * FROM ENCHERES en where a.id = en.idArticle AND en.idUtilisateur = " + idUtilisateur + ")";
                break;
            //enchereRemporte
            case 3:
                conditionCheckBox = " where a.idUtilisateur != " + idUtilisateur + " AND exists(select * FROM ENCHERES en where a.id = en.idArticle  AND a.dateFinEncheres < getDate()  ) AND e.idUtilisateur = " + idUtilisateur;
                break;

            //A FINIR ET TESTER

            //enchereOuvert && enchereRemporte meme que 7
            case 5:
            case 7:
                conditionCheckBox = " where ((a.dateFinEncheres > GETDATE() and a.dateDebutEncheres <= GETDATE() " +
                        "AND a.idUtilisateur != " + idUtilisateur + ") OR ( e.idUtilisateur = " + idUtilisateur + " AND a.dateFinEncheres <= GETDATE() ) )";
                break;
            //enchereEnCours && enchereRemporte
            case 6:
                conditionCheckBox = " where ((a.dateFinEncheres > GETDATE() and a.dateDebutEncheres <= GETDATE() " +
                        "and a.idUtilisateur != " + idUtilisateur + " AND  exists(select * FROM ENCHERES en where a.id = en.idArticle  " +
                        "AND   a.dateFinEncheres > getDate()   AND en.idUtilisateur = " + idUtilisateur + ") )" +
                        "OR ( e.idUtilisateur = " + idUtilisateur + " AND a.dateFinEncheres <= GETDATE() )) ";
                break;
        }

        if (!nomTitreArticle.isEmpty()) {

            conditionNom = " AND a.nom LIKE '%" + nomTitreArticle + "%'";

        } else {
            conditionNom = " ";
        }
        condition = conditionCheckBox + conditionCategorie + conditionNom;

        try {
            listeEnchere = enchereDao.afficherRequete(condition);
        } catch (SQLException | DalException e) {
            logger.severe("Error dans afficherRequete EnchereManager " + e.getMessage());
            throw new BllException(e.getMessage(), e);
        }
        return listeEnchere;
    }

    public List<Enchere> selectAllEncheresVendeur(int idUtilisateur, List<String> conditions, int idCategorie, String nomTitreArticle) {
        List<Enchere> listeEnchere = new ArrayList<>();
        try {
            listeEnchere = enchereDao.selectAllEncheresVendeur(idUtilisateur, conditions, idCategorie, nomTitreArticle);
        } catch (DalException e) {
            e.printStackTrace();
        }
        return listeEnchere;
    }


    public List<Enchere> selectEnchereByMontantMax(int idArticle) throws SQLException, BllException {
        List<Enchere> enchereList = new ArrayList<>();
        try {
            enchereList = enchereDao.selectEnchereByMontantMax(idArticle);
        } catch (DalException e) {
            e.printStackTrace();
        }
        return enchereList;
    }

}










