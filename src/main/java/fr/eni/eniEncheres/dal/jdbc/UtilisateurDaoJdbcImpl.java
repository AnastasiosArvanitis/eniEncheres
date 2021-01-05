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
        Utilisateur utilisateur = null;
        final String SELECT_BY_ID = "SELECT * FROM UTILISATEURS WHERE id = ?";

        try(Connection connection = JdbcConnection.connect()){

            PreparedStatement requete = connection.prepareStatement(SELECT_BY_ID);
            requete.setInt(1, id);
            ResultSet resultSet = requete.executeQuery();

            if(resultSet.next()){
                utilisateur = utilisateurBuilder(resultSet);
            }
        }catch(SQLException e){
            logger.severe("Error method selectById " + e.getMessage() + "\n");
            throw new DalException(e.getMessage(), e);
        }
        return utilisateur;
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
        final String UPDATE = "UPDATE UTILISATEURS SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ? , rue = ?, codePostal = ?, ville = ?, motDePasse = ? WHERE id = ?";
        try(Connection connection = JdbcConnection.connect()){
            PreparedStatement requete = connection.prepareStatement(UPDATE);
            requete.setString(1, utilisateur.getPseudo());
            requete.setString(2, utilisateur.getNom());
            requete.setString(3, utilisateur.getPrenom());
            requete.setString(4, utilisateur.getEmail());
            requete.setString(5, utilisateur.getTelephone());
            requete.setString(6, utilisateur.getRue());
            requete.setString(7, utilisateur.getCodePostal());
            requete.setString(8, utilisateur.getVille());
            requete.setString(9, utilisateur.getMotDePasse());
            requete.setInt(10, utilisateur.getId());
            requete.executeUpdate();
        }catch(SQLException e){
            logger.severe("Error method update " + e.getMessage() + "\n");
            throw  new DalException(e.getMessage(), e);
        }

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















