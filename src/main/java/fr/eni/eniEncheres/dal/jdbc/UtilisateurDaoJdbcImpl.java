package fr.eni.eniEncheres.dal.jdbc;

import fr.eni.eniEncheres.bo.Utilisateur;
import fr.eni.eniEncheres.dal.DalException;
import fr.eni.eniEncheres.dal.FactoryDao;
import fr.eni.eniEncheres.dal.dao.UtilisateurDao;
import fr.eni.eniEncheres.dal.jdbcTools.JdbcConnection;
import fr.eni.eniEncheres.tools.EnchereLogger;

import java.sql.*;
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
  public Utilisateur update(Utilisateur utilisateur) throws SQLException, DalException {
    final String UPDATE = "UPDATE UTILISATEURS SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ? , rue = ?, codePostal = ?, ville = ?, motDePasse = ? WHERE id = ?";
    Utilisateur utilisateurRecupere = null;
    try(Connection connection = JdbcConnection.connect()){
      PreparedStatement requete = connection.prepareStatement(UPDATE);
      requete.setString(1, utilisateur.getPseudo());
      requete.setString(2, utilisateur.getNom());
      requete.setString(3, utilisateur.getPrenom());
      requete.setString(4, utilisateur.getEmail());
      //gestion du null
      if(utilisateur.getTelephone() == null){
        requete.setNull(5, Types.VARCHAR);
      }else {
        requete.setString(5, utilisateur.getTelephone());
      }
      requete.setString(6, utilisateur.getRue());
      requete.setString(7, utilisateur.getCodePostal());
      requete.setString(8, utilisateur.getVille());
      requete.setString(9, utilisateur.getMotDePasse());
      requete.setInt(10, utilisateur.getId());
      requete.executeUpdate();

      utilisateurRecupere = FactoryDao.getUtilisateurDao().selectById(utilisateur.getId());

    }catch(SQLException e){
      logger.severe("Error method update " + e.getMessage() + "\n");
      throw  new DalException(e.getMessage(), e);
    }
    return utilisateurRecupere;
  }

  @Override
  public Utilisateur insert(Utilisateur ajoutUtilisateur) throws SQLException, DalException {
    final String SQL_INSERT ="INSERT INTO UTILISATEURS (pseudo,nom,prenom,email,telephone,rue,codePostal,ville,motDePasse) VALUES (?,?,?,?,?,?,?,?,?)";
    Utilisateur utilisateurCree = null;
    int idAjout = 0;
    try (Connection connection = JdbcConnection.connect()) {
      PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, ajoutUtilisateur.getPseudo());
      preparedStatement.setString(2, ajoutUtilisateur.getNom());
      preparedStatement.setString(3, ajoutUtilisateur.getPrenom());
      preparedStatement.setString(4, ajoutUtilisateur.getEmail());
      if (ajoutUtilisateur.getTelephone() == null) {
        preparedStatement.setNull(5, Types.VARCHAR);
      }
      else
      {
        preparedStatement.setString(5, ajoutUtilisateur.getTelephone());
      }

      preparedStatement.setString(6, ajoutUtilisateur.getRue());
      preparedStatement.setString(7, ajoutUtilisateur.getCodePostal());
      preparedStatement.setString(8, ajoutUtilisateur.getVille());
      preparedStatement.setString(9, ajoutUtilisateur.getMotDePasse());
      System.out.println(preparedStatement.toString());
      preparedStatement.executeUpdate();
      ResultSet resultSet = preparedStatement.getGeneratedKeys();

      if (resultSet.next()) {
        idAjout = resultSet.getInt(1);
        logger.info("Nouvel utilisateur crée avec l'id : " + idAjout + " "+ajoutUtilisateur.toString())  ;
        utilisateurCree = selectById(idAjout);
      }
    }
    catch(SQLException e){
      e.printStackTrace();
      logger.severe("Erreur methode ajoutUtilisateur : " + ajoutUtilisateur.toString() );

    }
    return utilisateurCree ;
  }

  @Override
  public boolean verifEmail(String email) throws SQLException, DalException {
    boolean verifEmail = false;
    final String VERIF_EMAIL ="SELECT * FROM UTILISATEURS WHERE email = ? ";
      try (Connection connection = JdbcConnection.connect())
      {
        PreparedStatement preparedStatement = connection.prepareStatement(VERIF_EMAIL);
        preparedStatement.setString(1,email);
        ResultSet rs = preparedStatement.executeQuery();
        verifEmail = rs.next();
        System.out.println(verifEmail);
      }catch( SQLException e)
    {
      logger.severe("Erreur méthode vérif email , email rechercher : " + email);
    }

    return verifEmail;
  }

  @Override
  public boolean verifPseudo(String pseudo) throws SQLException, DalException {
    boolean verifPseudo = false;
    final String VERIF_PSEUDO ="SELECT * FROM UTILISATEURS WHERE pseudo = ? ";
    try (Connection connection = JdbcConnection.connect())
    {
      PreparedStatement preparedStatement = connection.prepareStatement(VERIF_PSEUDO);
      preparedStatement.setString(1,pseudo);
      ResultSet rs = preparedStatement.executeQuery();
      verifPseudo = rs.next();
      System.out.println(verifPseudo);
    }catch( SQLException e)
    {
      logger.severe("Erreur méthode vérif pseudo , email pseudo : " + verifPseudo);
    }

    return verifPseudo;
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


