package fr.eni.eniEncheres.servlets.vente;

import fr.eni.eniEncheres.bll.BllException;
import fr.eni.eniEncheres.bll.EnchereManager;
import fr.eni.eniEncheres.bll.UtilisateurManager;
import fr.eni.eniEncheres.bo.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ViewVendeur extends HttpServlet {

    UtilisateurManager utilisateurManager = null;

    @Override
    public void init() throws ServletException {
        super.init();
        utilisateurManager = UtilisateurManager.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idVendeur = Integer.parseInt(request.getParameter("idVendeur"));
        Utilisateur vendeur = new Utilisateur();
        try {
            vendeur = utilisateurManager.selectById(idVendeur);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (BllException e) {
            e.printStackTrace();
        }
        request.setAttribute("vendeur",vendeur);
        request.getRequestDispatcher("WEB-INF/Ventes/viewVendeur.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }
}
