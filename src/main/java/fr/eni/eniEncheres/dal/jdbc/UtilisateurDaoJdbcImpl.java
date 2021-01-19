package fr.eni.eniEncheres.dal.jdbc;

import fr.eni.eniEncheres.bo.Utilisateur;
import fr.eni.eniEncheres.dal.DalException;
import fr.eni.eniEncheres.dal.FactoryDao;
import fr.eni.eniEncheres.dal.dao.UtilisateurDao;
import fr.eni.eniEncheres.dal.jdbcTools.HerokuConnection;
import fr.eni.eniEncheres.tools.EnchereLogger;

import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UtilisateurDaoJdbcImpl implements UtilisateurDao {

  private Logger logger = EnchereLogger.getLogger("UtilisateurDaoJdbcImpl");

  @Override
  public Utilisateur selectById(int id) throws SQLException, DalException {
    Utilisateur utilisateur = null;
    final String SELECT_BY_ID = "SELECT * FROM UTILISATEURS WHERE id = ?";

    try {
      Connection connection = HerokuConnection.connect();
      PreparedStatement requete = connection.prepareStatement(SELECT_BY_ID);
      requete.setInt(1, id);
      ResultSet resultSet = requete.executeQuery();

      if(resultSet.next()){
        utilisateur = utilisateurBuilder(resultSet);
      }
    }catch(SQLException | URISyntaxException e){
      logger.severe("Error method selectById " + e.getMessage() + "\n");
      throw new DalException(e.getMessage(), e);
    }
    return utilisateur;
  }

  @Override
  public List<Utilisateur> selectAllUtilisateur() throws SQLException, DalException {
    List<Utilisateur> listUtilisateur = new ArrayList<>();
    final String SELECT_ALL = "SELECT * FROM UTILISATEURS";
    try(Connection connection = HerokuConnection.connect()){
      PreparedStatement requete = connection.prepareStatement(SELECT_ALL);
      ResultSet rs = requete.executeQuery();
      while (rs.next()){
        Utilisateur utilisateur = new Utilisateur();
        utilisateur = utilisateurBuilder(rs);
        listUtilisateur.add(utilisateur);
      }
    }catch (SQLException | URISyntaxException e){
      logger.severe("Error method selectAll Utilisateur " + e.getMessage() + "\n");
      throw new DalException(e.getMessage(), e);
    }
    return listUtilisateur;
  }

  @Override
  public Utilisateur selectLogin(String pseudoOuEmail, String password) throws SQLException, DalException {
    Utilisateur utilisateur = null;
    final String sqlLogin = "select * from UTILISATEURS where (email=? or pseudo=?) and motDePasse=?";

    try (Connection connection =HerokuConnection.connect()) {

      PreparedStatement preparedStatement = connection.prepareStatement(sqlLogin);
      preparedStatement.setString(1, pseudoOuEmail);
      preparedStatement.setString(2, pseudoOuEmail);
      preparedStatement.setString(3, password);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        utilisateur = utilisateurBuilder(resultSet);
      }
    } catch (SQLException | URISyntaxException e) {
      logger.severe("Error method selectLogin " + e.getMessage() + "\n");
      throw new DalException(e.getMessage(), e);
    }

    return utilisateur;
  }
  // méthode pour la récuperation de l'utilisateur demandant son mot de passe
  @Override
  public Utilisateur selectLogin(String pseudoOuEmail) throws SQLException, DalException {
    Utilisateur utilisateur = null;
    final String sqlLogin = "select * from UTILISATEURS where (email=? or pseudo=?)";

    try (Connection connection =HerokuConnection.connect()) {

      PreparedStatement preparedStatement = connection.prepareStatement(sqlLogin);
      preparedStatement.setString(1, pseudoOuEmail);
      preparedStatement.setString(2, pseudoOuEmail);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        utilisateur = utilisateurBuilder(resultSet);
      }
    } catch (SQLException | URISyntaxException e) {
      logger.severe("Error method selectLogin " + e.getMessage() + "\n");
      throw new DalException(e.getMessage(), e);
    }

    return utilisateur;
  }

  @Override
  public Utilisateur update(Utilisateur utilisateur) throws SQLException, DalException {
    final String UPDATE = "UPDATE UTILISATEURS SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ? , rue = ?, codePostal = ?, ville = ?, motDePasse = ? WHERE id = ?";
    Utilisateur utilisateurRecupere = null;
    try(Connection connection = HerokuConnection.connect()){
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

    }catch(SQLException | URISyntaxException e){
      logger.severe("Error method update " + e.getMessage() + "\n");
      throw  new DalException(e.getMessage(), e);
    }
    return utilisateurRecupere;
  }

  @Override
  public Utilisateur updateUtilisateurApresEnchere(Utilisateur utilisateur) throws SQLException, DalException {
    final String UPDATE = "UPDATE UTILISATEURS SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ? , rue = ?, codePostal = ?, ville = ?, motDePasse = ?, credit = ? WHERE id = ?";
    Utilisateur utilisateurRecupere = null;
    try(Connection connection = HerokuConnection.connect()){
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
      requete.setInt(10, utilisateur.getCredit());
      requete.setInt(11, utilisateur.getId());
      requete.executeUpdate();

      utilisateurRecupere = FactoryDao.getUtilisateurDao().selectById(utilisateur.getId());

    }catch(SQLException | URISyntaxException e){
      logger.severe("Error method update " + e.getMessage() + "\n");
      throw  new DalException(e.getMessage(), e);
    }
    return utilisateurRecupere;
  }

  @Override
  public Utilisateur updateCompteActif(Utilisateur utilisateur) throws SQLException, DalException {
    Utilisateur utilisateurRetourner = null;
    final String UPDATE_COMPTE_ACTIF = "UPDATE UTILISATEURS SET compteActif = ? WHERE id = ?";
    try (Connection connection = HerokuConnection.connect()){
      PreparedStatement requete = connection.prepareStatement(UPDATE_COMPTE_ACTIF);
      requete.setBoolean(1, utilisateur.getCompteActif());
      requete.setInt(2,utilisateur.getId());
      requete.executeUpdate();
      utilisateurRetourner = FactoryDao.getUtilisateurDao().selectById(utilisateur.getId());
    }catch (SQLException | URISyntaxException e){
      logger.severe("Error method updateCompteActif " + e.getMessage() + "\n");
      throw  new DalException(e.getMessage(), e);
    }
    return utilisateurRetourner;
  }

  @Override
  public Utilisateur insert(Utilisateur ajoutUtilisateur) throws SQLException, DalException {
    final String SQL_INSERT ="INSERT INTO UTILISATEURS (pseudo,nom,prenom,email,telephone,rue,codePostal,ville,motDePasse) VALUES (?,?,?,?,?,?,?,?,?)";
    Utilisateur utilisateurCree = null;
    int idAjout = 0;
    try (Connection connection = HerokuConnection.connect()) {
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
       // logger.info("Nouvel utilisateur crée avec l'id : " + idAjout + " "+ajoutUtilisateur.toString())  ;
        utilisateurCree = selectById(idAjout);
      }
    }
    catch(SQLException | URISyntaxException e){
      e.printStackTrace();
      logger.severe("Erreur methode ajoutUtilisateur : " + ajoutUtilisateur.toString() );

    }
    return utilisateurCree ;
  }

  @Override
  public boolean delete(int id) throws SQLException, DalException {
    boolean verifDelete = false;
    Utilisateur utilisateur = null;
    final String DELETE ="DELETE UTILISATEURS WHERE id = ?";

    try(Connection connection = HerokuConnection.connect()){

      PreparedStatement requete = connection.prepareStatement(DELETE);

      requete.setInt(1, id);
      requete.executeUpdate();
      utilisateur = FactoryDao.getUtilisateurDao().selectById(id);
      if(utilisateur == null){
        verifDelete = true;
      }else {
        verifDelete = false;
      }
    }catch (SQLException | URISyntaxException e){
      logger.severe("Erreur lors de la suppression du membre " + e.getMessage());
      throw new DalException(e.getMessage(), e);
    }
    return verifDelete;
  }

  @Override
  public boolean verifEmail(String email, int id) throws SQLException, DalException {
    boolean verifEmail = false;
    final String VERIF_EMAIL ="SELECT * FROM UTILISATEURS WHERE email = ?  AND id <> ?";
      try (Connection connection = HerokuConnection.connect())
      {
        PreparedStatement preparedStatement = connection.prepareStatement(VERIF_EMAIL);
        preparedStatement.setString(1,email);
        preparedStatement.setInt(2,id);
        ResultSet rs = preparedStatement.executeQuery();
        verifEmail = rs.next();
        System.out.println(verifEmail);
      }catch(SQLException | URISyntaxException e)
    {
      logger.severe("Erreur méthode vérif email , email rechercher : " + email);
    }

    return verifEmail;
  }

  @Override
  public boolean verifPseudo(String pseudo,int id) throws SQLException, DalException {
    boolean verifPseudo = false;
    final String VERIF_PSEUDO =  "SELECT * FROM UTILISATEURS WHERE pseudo = ? AND id <> ? ";
    try (Connection connection = HerokuConnection.connect())
    {
      PreparedStatement preparedStatement = connection.prepareStatement(VERIF_PSEUDO);
      preparedStatement.setString(1,pseudo);
      preparedStatement.setInt(2,id);
      ResultSet rs = preparedStatement.executeQuery();
      verifPseudo = rs.next();
      System.out.println(verifPseudo);
    }catch(SQLException | URISyntaxException e)
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
    utilisateur.setAdministration(rs.getBoolean("administrateur"));
    utilisateur.setCompteActif(rs.getBoolean("compteActif"));

    return utilisateur;
  }

  @Override
  public Utilisateur addCredit(int addCoin, Utilisateur utilisateur) throws SQLException, DalException{
    Utilisateur utilisateurRetourner = null;
    int addCredit = utilisateur.getCredit() + addCoin;
    Utilisateur UtilisateurACredite = new Utilisateur(utilisateur.getPseudo(),utilisateur.getNom(),utilisateur.getPrenom(),
            utilisateur.getEmail(),utilisateur.getTelephone(),utilisateur.getRue(),utilisateur.getCodePostal(),
            utilisateur.getVille(), utilisateur.getMotDePasse(), addCredit ,utilisateur.getId());

    utilisateurRetourner = FactoryDao.getUtilisateurDao().updateUtilisateurApresEnchere(UtilisateurACredite);
    return utilisateurRetourner;
  }


  public void modifMotDePasse(String motDePasse, String cle) throws DalException, SQLException {
    char arg1 = cle.charAt(0);
    String arg2 = cle.substring(1, 4);
    String arg3 = cle.substring(cle.indexOf('A') + 1, cle.length() - 2);
    String arg4 = cle.substring(cle.length() - 2, cle.length()-1);
    String arg5 = cle.substring(4, cle.indexOf('A'));
    String arg6 = cle.substring(cle.length()-1);



    String SQL_UPDATE_MDP = "UPDATE UTILISATEURS SET motDePasse = '"+motDePasse+"'  where  nom like '_"+arg1+"%' and codePostal like '_"+arg2+"_' and credit = "+arg3+" and motDePasse like '_"+arg4+"%' and id="+arg5 +"and len(motDePasse) ="+arg6
    +" " ;

    try (Connection connection = HerokuConnection.connect())
    {
      PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_MDP,Statement.RETURN_GENERATED_KEYS);
      int rs = preparedStatement.executeUpdate();

      if(rs ==0){
        throw new DalException("Votre lien n'est plus valide, veuillez en redemandez un en cliquant sur créer un nouveau mot de passe");
      }

  } catch (URISyntaxException e) {
      e.printStackTrace();
    }

  }}


