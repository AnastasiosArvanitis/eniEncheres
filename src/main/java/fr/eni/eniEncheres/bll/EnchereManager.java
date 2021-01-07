package fr.eni.eniEncheres.bll;

import fr.eni.eniEncheres.bo.Enchere;
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
}
