package fr.eni.eniEncheres.dal.dao;

import fr.eni.eniEncheres.bo.Categorie;
import fr.eni.eniEncheres.dal.DalException;

import java.sql.SQLException;

public interface CategorieDao {

    Categorie selectCategorieByID(int CategorieId) throws SQLException, DalException;

}
