package fr.eni.eniEncheres.bll;

import fr.eni.eniEncheres.bo.Retrait;
import fr.eni.eniEncheres.dal.DalException;
import fr.eni.eniEncheres.dal.FactoryDao;
import fr.eni.eniEncheres.dal.dao.RetraitDao;
import fr.eni.eniEncheres.tools.EnchereLogger;

import java.sql.SQLException;
import java.util.logging.Logger;

public class RetraitManager {

    private static RetraitManager instance;
    private RetraitDao retraitDao;

    private Logger logger = EnchereLogger.getLogger("RetraitManager");

    //Private constractor
    private RetraitManager() { retraitDao = FactoryDao.getRetraitDao(); }

    //Only one instance
    public static RetraitManager getInstance() {
        if (instance == null) {
            return new RetraitManager();
        }
        return instance;
    }

    public Retrait getRetraitById(int retraitId) throws SQLException, BllException {
        Retrait retrait = new Retrait();
        try {
            retrait = retraitDao.selectRetraitById(retraitId);
        } catch(SQLException | DalException e){
            logger.severe("Error dans getRetraitById RetraitManager " + e.getMessage());
            throw new BllException("Error getRetraitById RetraitManager " + e.getMessage());
        }
        return retrait;
    }

    public Retrait addNewRetrait(Retrait newRetrait) throws Exception {
        Retrait addedRetrait = null;
        if (newRetrait.getRue().equals("")) {
            throw new Exception("Le point de retrait doit avoir un rue");
        } else if (newRetrait.getCodePostal().equals("")) {
            throw new Exception("Le point de retrait doit avoir un code postal");
        } else if (newRetrait.getVille().equals("")) {
            throw new Exception("Le point de retrait doit avoir une ville");
        } else {
            addedRetrait = retraitDao.insertRetrait(newRetrait);
        }
        return addedRetrait;
    }
}





















