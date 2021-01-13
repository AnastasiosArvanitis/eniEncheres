package fr.eni.eniEncheres.servlets.utilisateur;

import fr.eni.eniEncheres.bll.BllException;
import fr.eni.eniEncheres.bll.UtilisateurManager;
import fr.eni.eniEncheres.bo.Utilisateur;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;


public class Connection extends HttpServlet {

    private UtilisateurManager utilisateurManager = null;
    private String error="";

    @Override
    public void init() throws ServletException {
        super.init();
        utilisateurManager = UtilisateurManager.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pseudoOuEmail = request.getParameter("connection-identifiant");
        String password = request.getParameter("password-identifiant");
        String souvenir = "";
        souvenir = request.getParameter("connection-remeber");
        Utilisateur utilisateur = null;
        RequestDispatcher dispatcher = null;

        if(souvenir != null){
            Cookie cookie = new Cookie("connection-identifiant", pseudoOuEmail);
            cookie.setMaxAge(60 * 60 * 24 * 30);
            response.addCookie(cookie);
            Cookie cookiePass = new Cookie("password-identifiant", password);
            cookiePass.setMaxAge(60 * 60 * 24 * 30);
            response.addCookie(cookiePass);
        }

        try {
            utilisateur = utilisateurManager.getUtilisateurLogin(pseudoOuEmail, password);
            if(utilisateur == null) {
                error = "Mot de passe ou pseudo incorrect";
                request.setAttribute("message",error);
                request.getRequestDispatcher("/WEB-INF/Pages/connection.jsp").forward(request,response);
                //response.sendRedirect("/encheres/error?error=userNotFound");
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("utilisateur", utilisateur);
                response.sendRedirect("/encheres/");
            }
        } catch (BllException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("connection-identifiant")) {
                    request.setAttribute("connection-identifiant", cookie.getValue());
                }
                if (cookie.getName().equals("password-identifiant")) {
                    request.setAttribute("password-identifiant", cookie.getValue());
                }

            }
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Pages/connection.jsp");
        dispatcher.forward(request, response);
    }
}
