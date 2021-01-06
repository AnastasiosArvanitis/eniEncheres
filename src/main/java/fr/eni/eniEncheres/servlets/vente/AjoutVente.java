package fr.eni.eniEncheres.servlets.vente;

import fr.eni.eniEncheres.bll.BllException;
import fr.eni.eniEncheres.bll.CategorieManager;
import fr.eni.eniEncheres.bll.UtilisateurManager;
import fr.eni.eniEncheres.bo.Categorie;
import fr.eni.eniEncheres.bo.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AjoutVente extends HttpServlet {
    CategorieManager categorieManager = null;

    @Override
    public void init() throws ServletException {
        super.init();
        categorieManager = CategorieManager.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Categorie> listeCategorie = new ArrayList<>();
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        try {
            listeCategorie = categorieManager.selectAllCategorie();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (BllException e) {
            e.printStackTrace();
        }
        request.setAttribute("listeCategorie",listeCategorie);
        request.getRequestDispatcher("WEB-INF/Ventes/ajoutVente.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
