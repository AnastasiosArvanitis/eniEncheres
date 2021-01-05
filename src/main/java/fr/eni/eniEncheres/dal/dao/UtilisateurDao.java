package fr.eni.eniEncheres.dal.dao;

import fr.eni.eniEncheres.bo.Utilisateur;
import fr.eni.eniEncheres.dal.DalException;

import java.sql.SQLException;

public interface UtilisateurDao {

    Utilisateur selectById(int id) throws SQLException, DalException;

    Utilisateur selectLogin(String pseudoOuEmail, String password) throws SQLException, DalException;

    void update(Utilisateur utilisateur) throws SQLException, DalException;



}
