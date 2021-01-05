package fr.eni.eniEncheres.servlets.utilisateur;

import fr.eni.eniEncheres.bll.BllException;
import fr.eni.eniEncheres.bll.UtilisateurManager;
import fr.eni.eniEncheres.bo.Utilisateur;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;


public class Connection extends HttpServlet {

    private UtilisateurManager utilisateurManager = null;

    @Override
    public void init() throws ServletException {
        super.init();
        utilisateurManager = UtilisateurManager.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pseudoOuEmail = request.getParameter("connection-identifiant");
        String password = request.getParameter("password-identifiant");
        Utilisateur utilisateur = null;
        RequestDispatcher dispatcher = null;

        try {
            utilisateur = utilisateurManager.getUtilisateurLogin(pseudoOuEmail, password);
            if(utilisateur == null) {
                dispatcher = request.getRequestDispatcher("/WEB-INF/Pages/error.jsp?error=userNotExist");
                dispatcher.forward(request, response);
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("utilisateur", utilisateur);

                response.sendRedirect("/encheres/profile");
            }
        } catch (BllException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Pages/connection.jsp");
        dispatcher.forward(request, response);
    }
}
