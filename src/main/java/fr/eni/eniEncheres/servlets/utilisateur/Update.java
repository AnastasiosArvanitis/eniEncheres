package fr.eni.eniEncheres.servlets.utilisateur;

import fr.eni.eniEncheres.bll.BllException;
import fr.eni.eniEncheres.bll.UtilisateurManager;
import fr.eni.eniEncheres.bo.Utilisateur;
import fr.eni.eniEncheres.dal.DalException;
import fr.eni.eniEncheres.dal.FactoryDao;
import fr.eni.eniEncheres.dal.jdbc.UtilisateurDaoJdbcImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class Update extends HttpServlet {
    UtilisateurManager utilisateurManager =  null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         utilisateurManager = UtilisateurManager.getInstance();
        Utilisateur utilisateur1 = null;
        try {
            utilisateur1 = utilisateurManager.selectById(1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (BllException e) {
            e.printStackTrace();
        }

        assert utilisateur1 != null;
        request.setAttribute("pseudo", utilisateur1.getPseudo());
        request.setAttribute("nom", utilisateur1.getNom());
        request.setAttribute("prenom", utilisateur1.getPrenom());
        request.setAttribute("email", utilisateur1.getEmail());
        request.setAttribute("telephone", utilisateur1.getTelephone());
        request.setAttribute("rue", utilisateur1.getRue());
        request.setAttribute("codePostal", utilisateur1.getCodePostal());
        request.setAttribute("ville", utilisateur1.getVille());
        request.setAttribute("motDePasse", utilisateur1.getMotDePasse());
        request.getRequestDispatcher("/WEB-INF/Pages/profil.jsp").forward(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
