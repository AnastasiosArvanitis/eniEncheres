package fr.eni.eniEncheres.dal.jdbc;

import fr.eni.eniEncheres.bo.Utilisateur;
import fr.eni.eniEncheres.dal.DalException;
import fr.eni.eniEncheres.dal.dao.UtilisateurDao;
import fr.eni.eniEncheres.tools.EnchereLogger;

import java.sql.SQLException;
import java.util.logging.Logger;

public class UtilisateurDaoJdbcImpl implements UtilisateurDao {

    private Logger logger = EnchereLogger.getLogger("UtilisateurDaoJdbcImpl");

    @Override
    public void update(Utilisateur utilisateur) throws SQLException, DalException {

    }

    @Override
    public Utilisateur selectById(int id) throws SQLException, DalException {
        return null;
    }

}
