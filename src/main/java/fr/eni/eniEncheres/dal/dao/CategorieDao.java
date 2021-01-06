package fr.eni.eniEncheres.dal.dao;

import fr.eni.eniEncheres.bo.Categorie;
import fr.eni.eniEncheres.dal.DalException;

import java.sql.SQLException;
import java.util.List;

public interface CategorieDao {

    /* METHODE POUR L ADMINISTRATION */
    Categorie insert(Categorie categorie) throws SQLException, DalException;
    Categorie update(Categorie categorie) throws SQLException, DalException;
    boolean delete(int id) throws SQLException, DalException;

    /* METHODE IMPORTANTE */
    List<Categorie> selectAllCategorie() throws SQLException, DalException;
    Categorie selectCategorieByID(int CategorieId) throws SQLException, DalException;
    Categorie selectByName(String nomCategorie) throws SQLException, DalException;

}
