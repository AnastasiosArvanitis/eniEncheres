package fr.eni.eniEncheres.bll;

import fr.eni.eniEncheres.bo.Categorie;
import fr.eni.eniEncheres.dal.DalException;
import fr.eni.eniEncheres.dal.FactoryDao;
import fr.eni.eniEncheres.dal.dao.CategorieDao;
import fr.eni.eniEncheres.tools.EnchereLogger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CategorieManager {

    private static CategorieManager instance;
    private CategorieDao categorieDao;
    private Logger logger = EnchereLogger.getLogger("CategorieManager");

    //Constructeur privé => PATTERN SINGLETON
    private CategorieManager (){categorieDao = FactoryDao.getCategorieDao(); }

    //Permet de récupérer l'instance (créee une seule fois)
    public static CategorieManager getInstance() {
        if (instance == null) {
            return new CategorieManager();
        }
        return instance;
    }

    public List<Categorie> selectAllCategorie() throws SQLException, BllException {
        List<Categorie> listCategorie = new ArrayList<>();
        try {
            listCategorie = categorieDao.selectAllCategorie();
        }catch(SQLException | DalException e){
            logger.severe("Error dans selectALL CategorieManager " + e.getMessage());
            throw new BllException("Error selectAllCategorie bll" + e.getMessage());
        }
        return listCategorie;
    }

    public Categorie selectCategorieByID(int categorieId) throws SQLException, BllException {
        Categorie categorie = new Categorie();
        try{
            categorie = categorieDao.selectCategorieByID(categorieId);
        }catch(SQLException | DalException e){
            logger.severe("Error dans selectById CategorieManager " + e.getMessage());
            throw new BllException("Error selectById CategorieManager bll" + e.getMessage());
        }
        return categorie;
    }

    public Categorie selectByName(String nomCategorie) throws SQLException, BllException{
        Categorie categorie = new Categorie();
        try{
            categorie = categorieDao.selectByName(nomCategorie);
        }catch(SQLException | DalException e){
            logger.severe("Error dans selectByName CategorieManager " + e.getMessage());
            throw new BllException("Error selectByName CategorieManager bll" + e.getMessage());
        }
        return categorie;
    }

}
