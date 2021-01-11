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

    private static  EnchereManager instance;
    private EnchereDao enchereDao;
    private Logger logger = EnchereLogger.getLogger("CategorieManager");

    //Constructeur privé
    private EnchereManager(){enchereDao = FactoryDao.getEnchereDao(); }

    //Permet de récupérer l'instance (créee une seule fois)
    public static EnchereManager getInstance() {
        if (instance == null) {
            return new EnchereManager();
        }
        return instance;
    }

    public List<Enchere> selectAllEnchere() throws SQLException, BllException{
        List<Enchere> listEnchere = new ArrayList<>();
        try{
            listEnchere = enchereDao.selectAllEnchere();
        }catch(SQLException | DalException e){
            logger.severe("Error dans selectAll EnchereManager " + e.getMessage());
            throw new BllException(e.getMessage(), e);
        }
        return listEnchere;
    }

    public List<Enchere> selectEnchereVictoire(Utilisateur utilisateur, String filtreNom, int filtreCategorie) throws SQLException, DalException, BllException {
        List<Enchere> listEnchere = new ArrayList<>();
        try{
            listEnchere = enchereDao.selectEnchereVictoire(utilisateur, filtreNom,filtreCategorie);
        }catch(SQLException | DalException e){
            logger.severe("Error dans selectAll EnchereManager " + e.getMessage());
            throw new BllException(e.getMessage(), e);
        }
        return listEnchere;
    }

    public List<Enchere> selectEnchereByUtilisateur (Utilisateur utilisateur, String filtreNom, int filtreCategorie) throws SQLException, DalException, BllException {
        List<Enchere> listEnchere = new ArrayList<>();
        try{
            listEnchere = enchereDao.selectEnchereByUtilisateur(utilisateur, filtreNom,filtreCategorie);
        }catch(SQLException | DalException e){
            logger.severe("Error dans selectAll EnchereManager " + e.getMessage());
            throw new BllException(e.getMessage(), e);
        }
        return listEnchere;
    }

    public Enchere getEnchereArticle(int articleId) throws SQLException, DalException, BllException {
        Enchere enchere = new Enchere() ;

        try{
            enchere = enchereDao.selectEnchereByIdArticle(articleId);
        }catch (SQLException | DalException e) {
            logger.severe("Error dans selectAll EnchereManager " + e.getMessage());
            throw new BllException(e.getMessage(), e);
        }
        return enchere;
    }

    public Enchere addNewEnchere(Utilisateur acheteur, int idArticle, int montantEnchere) throws SQLException, BllException, DalException {
        Enchere enchereRetourner = null;
        Enchere enchere = enchereDao.selectEnchereByIdArticle(idArticle);

        //prix de vente est soit = au prix initial ou soit supperieur
        if((enchere.getArticle().getPrixInitial() <= enchere.getArticle().getPrixVente()) || (enchere.getArticle().getPrixVente() == 0)){
            //compare le montantEnchere avec le prix article
            if (enchere.getArticle().getPrixVente() < montantEnchere){
                //controle pour savoir si l'acheteur a deja fais la derniere enchere
                if(acheteur.getId() != enchere.getUtilisateur().getId()) {
                    //controle pour savoir si le credit de l'utilisateur est superrieur au prix de vente
                    if(acheteur.getCredit() >= enchere.getArticle().getPrixVente()) {
                        try{
                            enchereRetourner = enchereDao.addNewEnchere(acheteur,idArticle,montantEnchere);
                        }catch (SQLException | DalException e){
                            logger.severe("Error dans addNewEncher EnchereManager " + e.getMessage());
                            throw new BllException(e.getMessage(), e);
                        }
                    }else {
                        new BllException("Votre Credit est inferieur au montant de l'enchere");
                    }
                }else{
                    new BllException("Vous etes deja le dernier encherisseur");
                }
            }else {
                new BllException("Prix de vente supperieur au montant de l'enchere");
            }
        }else{
            new BllException("le prix initial est supperieur aux prix de vente");
        }
        return enchereRetourner;
    }

    public List<Enchere> getEnchereVendeur(Utilisateur utilisateur, String filtreNom, int filtreCategorie) throws BllException {
        List<Enchere> enchereRetourner = null;
        try {
            enchereRetourner = enchereDao.selectEnchereVendeur(utilisateur, filtreNom, filtreCategorie);
        } catch (SQLException | DalException e){
            logger.severe("Error dans getEnchereVendeur EnchereManager " + e.getMessage());
            throw new BllException(e.getMessage(), e);
        }
        return enchereRetourner;
    }


    public List<Enchere> getEnchereVendeurFutur(Utilisateur utilisateur, String filtreNom, int filtreCategorie) throws BllException {
        List<Enchere> enchereRetourner = null;
        try {
            enchereRetourner = enchereDao.selectEnchereVendeurFutur(utilisateur, filtreNom, filtreCategorie);
        } catch (SQLException | DalException e){
            logger.severe("Error dans getEnchereVendeurFutur EnchereManager " + e.getMessage());
            throw new BllException(e.getMessage(), e);
        }
        return enchereRetourner;
    }

    public List<Enchere> getEnchereVendeurTermine(Utilisateur utilisateur, String filtreNom, int filtreCategorie) throws BllException {
        List<Enchere> enchereRetourner = null;
        try {
            enchereRetourner = enchereDao.selectEnchereVendeurTermine(utilisateur, filtreNom, filtreCategorie);
        } catch (SQLException | DalException e){
            logger.severe("Error dans getEnchereVendeurTermine EnchereManager " + e.getMessage());
            throw new BllException(e.getMessage(), e);
        }
        return enchereRetourner;
    }


}










