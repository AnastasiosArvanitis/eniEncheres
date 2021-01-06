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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CategorieDaoJdbcImpl implements CategorieDao {

    private Logger logger = EnchereLogger.getLogger("CategorieDaoJdbcImpl");

    @Override
    public List<Categorie> selectAllCategorie() throws SQLException, DalException {
        List<Categorie> listCategorie = new ArrayList<>();
        final String SELECT_ALL_CATEGORIE="SELECT * FROM CATEGORIES";
        try(Connection connection = JdbcConnection.connect()){
            PreparedStatement requete = connection.prepareStatement(SELECT_ALL_CATEGORIE);
            ResultSet rs = requete.executeQuery();
            while(rs.next()){
                Categorie categorie = new Categorie();
                categorie.setId(rs.getInt("id"));
                categorie.setLibelle(rs.getString("libelle"));
                listCategorie.add(categorie);
            }
        }catch (SQLException e){
            logger.severe("Error method selectAllCategorie " + e.getMessage() + "\n");
            throw new DalException(e.getMessage(), e);
        }
        return listCategorie;
    }

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

    @Override
    public Categorie selectByName(String nomCategorie) throws SQLException, DalException {
        Categorie categorie = null;
        final String SELECT_BY_NAME="SELECT * FROM CATEGORIES WHERE libelle = ?";
        try(Connection connection = JdbcConnection.connect()){
            PreparedStatement requete = connection.prepareStatement(SELECT_BY_NAME);
            requete.setString(1, nomCategorie);
            ResultSet rs = requete.executeQuery();
            if (rs.next()){
                categorie = categorieBuilder(rs);
            }
        }catch (SQLException e){
            logger.severe("Error method selectByName " + e.getMessage() + "\n");
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



    /* METHODE POUR L ADMINISTRATION A FAIRE SI PROJET FINI*/
    @Override
    public Categorie insert(Categorie categorie) throws SQLException, DalException {
        return null;
    }
    @Override
    public Categorie update(Categorie categorie) throws SQLException, DalException {
        return null;
    }
    @Override
    public boolean delete(int id) throws SQLException, DalException {
        return false;
    }
}























