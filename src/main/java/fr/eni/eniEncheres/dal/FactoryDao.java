package fr.eni.eniEncheres.dal;

import fr.eni.eniEncheres.dal.dao.UtilisateurDao;
import fr.eni.eniEncheres.dal.jdbc.UtilisateurDaoJdbcImpl;

public class FactoryDao {

    public static UtilisateurDao getPostDao() {
        return new UtilisateurDaoJdbcImpl();
    }

}
