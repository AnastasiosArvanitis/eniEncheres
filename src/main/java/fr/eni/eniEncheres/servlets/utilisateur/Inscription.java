package fr.eni.eniEncheres.servlets.utilisateur;

import fr.eni.eniEncheres.bll.BllException;
import fr.eni.eniEncheres.bll.UtilisateurManager;
import fr.eni.eniEncheres.bo.Utilisateur;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

public class Inscription extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Pages/inscription.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pseudo       = request.getParameter("pseudo");
        String nom          = request.getParameter("nom");
        String prenom       = request.getParameter("prenom");
        String email        = request.getParameter("email");
        String telephone    = request.getParameter("telephone");
        String rue          = request.getParameter("rue");
        String codePostal   = request.getParameter("codePostal");
        String ville        = request.getParameter("ville");
        String motDePasse   = request.getParameter("motDePasse");
        String mdpConfirm = request.getParameter("mdpConfirm");


        Utilisateur ajoutUtilisateur = new Utilisateur(pseudo,nom,prenom,email,telephone,rue,codePostal,ville,motDePasse);
        UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
        Utilisateur utilisateur = null;
        RequestDispatcher dispatcher = null;
        String messageErreur = "" ;

        if (!mdpConfirm.equals(motDePasse)){

            request.setAttribute("ajoutUtilisateur", ajoutUtilisateur);
            dispatcher = request.getRequestDispatcher("/WEB-INF/Pages/inscription.jsp");
            dispatcher.forward(request, response);}
            else{
                try {
                    utilisateur = utilisateurManager.insert(ajoutUtilisateur);

                } catch (Exception e) {
                    // Je récupère le message de l'exception
                    messageErreur = e.getMessage();
                }

                if (utilisateur == null) {
                    request.setAttribute("Erreur", messageErreur);
                    request.setAttribute("ajoutUtilisateur", ajoutUtilisateur);
                    dispatcher = request.getRequestDispatcher("/WEB-INF/Pages/inscription.jsp");
                    dispatcher.forward(request, response);
                } else {
                    HttpSession session = request.getSession();
                    session.setAttribute("utilisateur", utilisateur);

                    response.sendRedirect("/encheres/profile");
                }
            }
            }
        }







