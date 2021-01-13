package fr.eni.eniEncheres.servlets.utilisateur;

import fr.eni.eniEncheres.bll.BllException;
import fr.eni.eniEncheres.bll.UtilisateurManager;
import fr.eni.eniEncheres.bo.Utilisateur;
import fr.eni.eniEncheres.dal.DalException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ProfilAjoutCredit extends HttpServlet {
    UtilisateurManager utilisateurManager =  null;

    @Override
    public void init() throws ServletException {
        super.init();
        utilisateurManager = UtilisateurManager.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    int montant = Integer.parseInt(request.getParameter("montant"));
    int id = Integer.parseInt(request.getParameter("id"));
    Utilisateur utilisateurRetourner = null;
        try {
            Utilisateur utilisateur = utilisateurManager.selectById(id);
            utilisateurRetourner = utilisateurManager.addCredit(montant,utilisateur);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (BllException | DalException e) {
            e.printStackTrace();
        }
        request.setAttribute("utilisateur",utilisateurRetourner);
        request.getRequestDispatcher("/WEB-INF/Pages/profil.jsp").forward(request,response);
    }
}
