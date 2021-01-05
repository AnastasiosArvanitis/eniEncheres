package fr.eni.eniEncheres.dal.jdbc;

import fr.eni.eniEncheres.bo.Utilisateur;
import fr.eni.eniEncheres.dal.DalException;
import fr.eni.eniEncheres.dal.dao.UtilisateurDao;
import fr.eni.eniEncheres.dal.jdbcTools.JdbcConnection;
import fr.eni.eniEncheres.tools.EnchereLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class UtilisateurDaoJdbcImpl implements UtilisateurDao {

    private Logger logger = EnchereLogger.getLogger("UtilisateurDaoJdbcImpl");

    @Override
    public Utilisateur selectById(int id) throws SQLException, DalException {
        return null;
    }

    @Override
    public Utilisateur selectLogin(String pseudoOuEmail, String password) throws SQLException, DalException {
        Utilisateur utilisateur = null;
        final String sqlLogin = "select * from UTILISATEURS where (email=? or pseudo=?) and motDePasse=?";

        try (Connection connection =JdbcConnection.connect()) {

            PreparedStatement preparedStatement = connection.prepareStatement(sqlLogin);
            preparedStatement.setString(1, pseudoOuEmail);
            preparedStatement.setString(2, pseudoOuEmail);
            preparedStatement.setString(3, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                utilisateur = utilisateurBuilder(resultSet);
            }
        } catch (SQLException e) {
            logger.severe("Error method selectLogin " + e.getMessage() + "\n");
            throw new DalException(e.getMessage(), e);
        }

        return utilisateur;
    }

    @Override
    public void update(Utilisateur utilisateur) throws SQLException, DalException {

    }

    @Override
    public Utilisateur insert(Utilisateur utilisateur) throws SQLException, DalException {
        return null;
    }

    private Utilisateur utilisateurBuilder(ResultSet rs) throws SQLException {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(rs.getInt("id"));
        utilisateur.setPseudo(rs.getString("pseudo"));
        utilisateur.setNom(rs.getString("nom"));
        utilisateur.setPrenom(rs.getString("prenom"));
        utilisateur.setEmail(rs.getString("email"));
        utilisateur.setTelephone(rs.getString("telephone"));
        utilisateur.setRue(rs.getString("rue"));
        utilisateur.setCodePostal(rs.getString("codePostal"));
        utilisateur.setVille(rs.getString("ville"));
        utilisateur.setMotDePasse(rs.getString("motDePasse"));
        utilisateur.setCredit(rs.getInt("credit"));
        utilisateur.setAdministration(rs.getByte("administrateur"));
        utilisateur.setCompteActif(rs.getByte("compteActif"));

        return utilisateur;
    }

}















