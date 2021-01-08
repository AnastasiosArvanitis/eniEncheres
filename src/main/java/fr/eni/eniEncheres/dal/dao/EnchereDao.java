package fr.eni.eniEncheres.dal.dao;

import fr.eni.eniEncheres.bo.Article;
import fr.eni.eniEncheres.bo.Enchere;
import fr.eni.eniEncheres.bo.Utilisateur;
import fr.eni.eniEncheres.dal.DalException;

import java.sql.SQLException;
import java.util.List;

public interface EnchereDao {

    List<Enchere> selectAllEnchere() throws SQLException, DalException;

    List<Enchere> selectAllEnchere(Utilisateur utilisateur, String filtreNom , int filtreCategorie ) throws SQLException, DalException;

    List<Enchere> selectEnchereVictoire(Utilisateur utilisateur, String filtreNom , int filtreCategorie ) throws SQLException, DalException;

    List<Enchere> selectEnchereByUtilisateur(Utilisateur utilisateur, String filtreNom , int filtreCategorie ) throws SQLException, DalException;

    List<Enchere> selectEnchereByArticle(Article article) throws SQLException, DalException;

    Enchere selectEnchereByIdArticle(int idArticle) throws SQLException, DalException;

    Enchere addNewEnchere(Utilisateur acheteur, int idArticle, int montantEnchere) throws SQLException, DalException;

    Enchere terminerEnchere() throws SQLException, DalException;

    public Enchere selectById(int id) throws SQLException, DalException;

    public List<Enchere> selectEnchereVendeur(Utilisateur utilisateur, String filtreNom, int filtreCategorie) throws SQLException, DalException;

    public List<Enchere> selectEnchereVendeurFutur(Utilisateur utilisateur, String filtreNom, int filtreCategorie) throws SQLException, DalException;

    public List<Enchere> selectEnchereVendeurTermine(Utilisateur utilisateur, String filtreNom, int filtreCategorie) throws SQLException, DalException;
}
