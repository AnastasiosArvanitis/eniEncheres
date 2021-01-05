package fr.eni.eniEncheres.servlets.utilisateur;

import fr.eni.eniEncheres.bll.BllException;
import fr.eni.eniEncheres.bll.UtilisateurManager;
import fr.eni.eniEncheres.bo.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class UpdateProfile extends HttpServlet {
    UtilisateurManager utilisateurManager =  null;
    private String message;

    @Override
    public void init() throws ServletException {
        super.init();
        utilisateurManager = UtilisateurManager.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        request.getRequestDispatcher("/WEB-INF/Pages/updateProfil.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String pseudo = request.getParameter("pseudo");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        String rue = request.getParameter("rue");
        String codePostal = request.getParameter("codePostal");
        String ville = request.getParameter("ville");
        String motDePasse = request.getParameter("motDePasse");
        String confirmeMotDePasse = request.getParameter("confirmeMotDePasse");

        if(motDePasse.equals(confirmeMotDePasse) ){
            Utilisateur utilisateur = new Utilisateur(pseudo,nom,prenom,email,telephone,rue,codePostal,ville,motDePasse,id);
            try {
                Utilisateur utilisateurRecuperer = utilisateurManager.update(utilisateur);
                message ="Update reussi !";
                request.setAttribute("id",utilisateurRecuperer.getId());
                request.setAttribute("pseudo", utilisateurRecuperer.getPseudo());
                request.setAttribute("nom", utilisateurRecuperer.getNom());
                request.setAttribute("prenom", utilisateurRecuperer.getPrenom());
                request.setAttribute("email", utilisateurRecuperer.getEmail());
                request.setAttribute("telephone", utilisateurRecuperer.getTelephone());
                request.setAttribute("rue", utilisateurRecuperer.getRue());
                request.setAttribute("codePostal", utilisateurRecuperer.getCodePostal());
                request.setAttribute("ville", utilisateurRecuperer.getVille());
                request.setAttribute("motDePasse", utilisateurRecuperer.getMotDePasse());
                request.setAttribute("message", message);
                request.getRequestDispatcher("/WEB-INF/Pages/profil.jsp").forward(request,response);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (BllException e) {
                e.printStackTrace();
            }
        }else{
            message="les mots de passe ne correspondent pas !";
            request.setAttribute("message",message);
        }

    }
}
