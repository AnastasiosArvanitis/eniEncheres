package fr.eni.eniEncheres.dal.dao;

import fr.eni.eniEncheres.bo.Article;
import fr.eni.eniEncheres.bo.Enchere;
import fr.eni.eniEncheres.bo.Utilisateur;
import fr.eni.eniEncheres.dal.DalException;

import java.sql.SQLException;
import java.util.List;

public interface EnchereDao {

    List<Enchere> selectAllEnchere() throws SQLException, DalException;
    List<Enchere> selectEnchereByUtilisateur(Utilisateur utilisateur, String filtreNom , String filtreCategorie ) throws SQLException, DalException;
    List<Enchere> selectEnchereByArticle(Article article) throws SQLException, DalException;
    Enchere selectById(int id) throws SQLException, DalException;

    Enchere addNewEnchere() throws SQLException,DalException;
    Enchere terminerEnchere() throws SQLException, DalException;

}
