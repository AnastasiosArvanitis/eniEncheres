package fr.eni.eniEncheres.bll;

import fr.eni.eniEncheres.bo.Utilisateur;
import fr.eni.eniEncheres.dal.DalException;
import fr.eni.eniEncheres.dal.FactoryDao;
import fr.eni.eniEncheres.dal.dao.UtilisateurDao;
import fr.eni.eniEncheres.tools.EnchereLogger;

import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilisateurManager {

    private static UtilisateurManager instance;
    private UtilisateurDao utilisateurDao;

    private Logger logger = EnchereLogger.getLogger("UtilisateurManager");

    //Constructeur privé => PATTERN SINGLETON
    private UtilisateurManager() {
        utilisateurDao = FactoryDao.getUtilisateurDao();
    }

    //Permet de récupérer l'instance (créee une seule fois)
    public static UtilisateurManager getInstance() {
        if (instance == null) {
            return new UtilisateurManager();
        }
        return instance;
    }

    public Utilisateur getUtilisateurLogin(String pseudoOuEmail, String password) throws SQLException, BllException {
        Utilisateur utilisateur = null;

        try {
            utilisateur = utilisateurDao.selectLogin(pseudoOuEmail, password);
        } catch (SQLException | DalException e) {
            logger.severe("Error getUtilisateurLogin " + e.getMessage());
            throw new BllException(e.getMessage(), e);
        }
        return utilisateur;
    }

    public Utilisateur selectById(int id) throws SQLException, BllException {
        Utilisateur utilisateur = null;
        try {
            utilisateur = utilisateurDao.selectById(id);
        } catch (SQLException | DalException e) {
            logger.severe("Error dans selectById UtilisateurManager " + e.getMessage());
            throw new BllException(e.getMessage(), e);
        }
        return utilisateur;
    }

    public Utilisateur update(Utilisateur utilisateur) throws Exception {
        Utilisateur utilisateurRetourne = null;
        formatEmail(utilisateur);
        formatPseudo(utilisateur);
        //VERIFIER PSEUDO ET EMAIL
        boolean verifEmail = utilisateurDao.verifEmail(utilisateur.getEmail(), utilisateur.getId());
        boolean verifPseudo = utilisateurDao.verifPseudo(utilisateur.getPseudo(), utilisateur.getId());
        if ((verifEmail) & (verifPseudo)) {
            throw new Exception("L'email et le pseudo sont déjà présent en base");
        } else if ((verifEmail) & (!verifPseudo)) {
            throw new Exception("L'email saisi est déjà utilisé");
        } else if ((!verifEmail) & (verifPseudo)) {
            throw new Exception("Le pseudo est déjà pris");
        } else {
            try {
                utilisateurRetourne = utilisateurDao.update(utilisateur);
            } catch (SQLException | DalException e) {
                logger.severe("Error updateManager " + e.getMessage());
                throw new BllException(e.getMessage(), e);
            }
        }
        return utilisateurRetourne;
    }

    public Utilisateur insert(Utilisateur ajoutUtilisateur) throws Exception {
        Utilisateur utilisateur = null;
        formatEmail(ajoutUtilisateur);
        formatPseudo(ajoutUtilisateur);
        boolean verifEmail = utilisateurDao.verifEmail ( ajoutUtilisateur.getEmail(), ajoutUtilisateur.getId());
        boolean verifPseudo = utilisateurDao.verifPseudo ( ajoutUtilisateur.getPseudo(), ajoutUtilisateur.getId());

        if ((verifEmail) & (verifPseudo)) {
            throw new Exception("L'email et le pseudo sont déjà présent en base");
        } else if ((verifEmail) & (!verifPseudo)) {
            throw new Exception("L'email saisi est déjà utilisé");
        } else if ((!verifEmail) & (verifPseudo)) {
            throw new Exception("Le pseudo est déjà pris");
        } else {
            utilisateur = utilisateurDao.insert(ajoutUtilisateur);
        }
        return utilisateur;
    }

    public boolean delete(int id) throws SQLException, BllException{
        boolean verifDelete = false;
        try{
          verifDelete = utilisateurDao.delete(id);
        }catch(SQLException | DalException e){
            logger.severe("Error lors de la suppression du membre dans la BLL" + e.getMessage());
            throw new BllException(e.getMessage(),e);
        }
        return verifDelete;
    }

    public void formatEmail(Utilisateur utilisateur) throws Exception {
        String regExpression = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
        Pattern p = Pattern.compile(regExpression);
        Matcher m = p.matcher(utilisateur.getEmail());
        boolean formatEmail = m.matches();
        if (!formatEmail) {
            logger.severe("Tentative de création de compte avec un email incorrect: " + utilisateur.getEmail());
            throw new Exception("L'adresse email n'est pas dans un format valide");
        }

    }
    public void formatPseudo(Utilisateur utilisateur) throws Exception {
        String regExpression = "[a-z\\d]*";
        Pattern p = Pattern.compile(regExpression);
        Matcher m = p.matcher(utilisateur.getPseudo());
        boolean formatPseudo = m.matches();
        if (!formatPseudo) {
            logger.severe("Le pseudo doit être au format alpha-numérique " + utilisateur.getPseudo());
            throw new Exception("Le pseudo doit être au format alpha numérique");
        }
    }


}















