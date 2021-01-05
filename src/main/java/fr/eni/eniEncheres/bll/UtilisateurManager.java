package fr.eni.eniEncheres.bll;

import fr.eni.eniEncheres.bo.Utilisateur;
import fr.eni.eniEncheres.dal.DalException;
import fr.eni.eniEncheres.dal.FactoryDao;
import fr.eni.eniEncheres.dal.dao.UtilisateurDao;
import fr.eni.eniEncheres.tools.EnchereLogger;

import java.sql.SQLException;
import java.util.logging.Logger;

public class UtilisateurManager {

    private static UtilisateurManager instance;
    private UtilisateurDao utilisateurDao;

    private Logger logger = EnchereLogger.getLogger("UtilisateurManager");

    //Constructeur privé => PATTERN SINGLETON
    private UtilisateurManager() {
        utilisateurDao = FactoryDao.getUtilisateurDao();
    }

    //Permet de récupérer l'instance (créee une seule fois)
    public static UtilisateurManager getInstance() {
        if (instance == null) {
            return new UtilisateurManager();
        }
        return instance;
    }

    public Utilisateur getUtilisateurLogin(String pseudoOuEmail, String password) throws SQLException, DalException {
        Utilisateur utilisateur = null;

        try {
            utilisateur = utilisateurDao.selectLogin(pseudoOuEmail, password);
        } catch (SQLException e) {
            logger.severe("Error getUtilisateurLogin " +e.getMessage());
            throw new DalException(e.getMessage(), e);
        }
        return utilisateur;
    }

}















