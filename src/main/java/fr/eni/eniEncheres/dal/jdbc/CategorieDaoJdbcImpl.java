package fr.eni.eniEncheres.dal.jdbc;

import fr.eni.eniEncheres.bo.Categorie;
import fr.eni.eniEncheres.dal.DalException;
import fr.eni.eniEncheres.dal.dao.CategorieDao;
import fr.eni.eniEncheres.dal.jdbcTools.JdbcConnection;
import fr.eni.eniEncheres.tools.EnchereLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class CategorieDaoJdbcImpl implements CategorieDao {

    private Logger logger = EnchereLogger.getLogger("CategorieDaoJdbcImpl");

    @Override
    public Categorie selectCategorieByID(int CategorieId) throws SQLException, DalException {
        Categorie categorie = null;
        final String SELECT_BY_ID = "select * from CATEGORIES where id = ?";

        try {
            Connection connection = JdbcConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1, CategorieId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                categorie = categorieBuilder(resultSet);
            }
        } catch(SQLException e){
            logger.severe("Error method selectCategorieByID " + e.getMessage() + "\n");
            throw new DalException(e.getMessage(), e);
        }

        return categorie;
    }


    private Categorie categorieBuilder(ResultSet rs) throws SQLException {
        Categorie categorie = new Categorie();
        categorie.setId(rs.getInt("id"));
        categorie.setLibelle(rs.getString("libelle"));

        return categorie;
    }

}























