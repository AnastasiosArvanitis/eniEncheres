package fr.eni.eniEncheres.servlets.vente;

import fr.eni.eniEncheres.bll.*;
import fr.eni.eniEncheres.bo.Article;
import fr.eni.eniEncheres.bo.Categorie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ChercherEnchere extends HttpServlet {

    CategorieManager categorieManager = null;
    RetraitManager retraitManager = null;
    UtilisateurManager utilisateurManager = null;
    ArticleManager articleManager = null;
    EnchereManager enchereManager = null;

    @Override
    public void init() throws ServletException {
        super.init();
        categorieManager = CategorieManager.getInstance();
        retraitManager = RetraitManager.getInstance();
        utilisateurManager = UtilisateurManager.getInstance();
        articleManager = ArticleManager.getInstance();
        enchereManager = EnchereManager.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchArticle = request.getParameter("search-article");
        String searchCategorie = request.getParameter("search-categorie");

        String radioAchat = request.getParameter("radio-achat");
        String checkEnchereOuvert = request.getParameter("check-enchereOuvert");
        String checkEnchereEnCours = request.getParameter("check-enchereEnCours");
        String checkEnchereRemporte = request.getParameter("check-enchereRemporte");

        String radioVente = request.getParameter("radio-vente");
        String checkVenteEnCours = request.getParameter("check-venteEnCours");
        String checkVenteNonDebute = request.getParameter("check-venteNonDebute");
        String checkVenteTermine = request.getParameter("check-venteTermine");

        Article article = null;
        Categorie categorie = null;

        if (!searchArticle.equals("") || !searchCategorie.equals("")) {
            try {
                //articleManager.
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}














