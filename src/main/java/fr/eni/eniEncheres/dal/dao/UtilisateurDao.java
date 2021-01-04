package fr.eni.eniEncheres.dal.dao;

import fr.eni.eniEncheres.bo.Utilisateur;
import fr.eni.eniEncheres.dal.DalException;

import java.sql.SQLException;

public interface UtilisateurDao {

    public void update(Utilisateur utilisateur) throws SQLException, DalException;

    public Utilisateur selectById(int id) throws SQLException, DalException;

}
