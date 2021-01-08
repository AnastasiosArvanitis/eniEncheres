package fr.eni.eniEncheres.dal.dao;

import fr.eni.eniEncheres.bo.Utilisateur;
import fr.eni.eniEncheres.dal.DalException;

import java.sql.SQLException;

public interface UtilisateurDao {

    Utilisateur selectById(int id) throws SQLException, DalException;

    Utilisateur selectLogin(String pseudoOuEmail, String password) throws SQLException, DalException;

    Utilisateur update(Utilisateur utilisateur) throws SQLException, DalException;

    Utilisateur updateUtilisateurApresEnchere(Utilisateur utilisateur) throws SQLException, DalException;

    Utilisateur insert(Utilisateur utilisateur) throws SQLException, DalException;

    boolean delete(int id) throws SQLException, DalException;

    boolean verifEmail(String email, int id) throws SQLException,DalException;

    boolean verifPseudo(String pseudo, int id) throws  SQLException,DalException;


}
