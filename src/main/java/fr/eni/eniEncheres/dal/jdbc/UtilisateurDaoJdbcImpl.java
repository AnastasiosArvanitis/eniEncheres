package fr.eni.eniEncheres.dal.jdbc;

import fr.eni.eniEncheres.bo.Utilisateur;
import fr.eni.eniEncheres.dal.DalException;
import fr.eni.eniEncheres.dal.dao.UtilisateurDao;

import java.sql.SQLException;

public class UtilisateurDaoJdbcImpl implements UtilisateurDao {

    @Override
    public void update(Utilisateur utilisateur) throws SQLException, DalException {

    }

    @Override
    public Utilisateur selectById(int id) throws SQLException, DalException {
        return null;
    }

}
