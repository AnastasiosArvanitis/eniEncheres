package fr.eni.eniEncheres.servlets.vente;

import fr.eni.eniEncheres.bll.*;
import fr.eni.eniEncheres.bo.Article;
import fr.eni.eniEncheres.bo.Categorie;
import fr.eni.eniEncheres.bo.Enchere;
import fr.eni.eniEncheres.bo.Utilisateur;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


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
        String filtreNom = request.getParameter("search-article");
        String searchCategorie = request.getParameter("search-categorie");

        String radioAchat = request.getParameter("radio-achat");
        String checkEnchereOuvert = request.getParameter("check-enchereOuvert");
        String checkEnchereEnCours = request.getParameter("check-enchereEnCours");
        String checkEnchereRemporte = request.getParameter("check-enchereRemporte");

        String radioVente = request.getParameter("radio-vente");
        String checkVenteEnCours = request.getParameter("check-venteEnCours");
        String checkVenteNonDebute = request.getParameter("check-venteNonDebute");
        String checkVenteTermine = request.getParameter("check-venteTermine");

        System.out.println("radioAchat " +radioAchat);
        System.out.println("radioVente " +radioVente);

        int categorieId;
        Article article = null;
        Categorie categorie = null;
        Utilisateur utilisateur = null;
        List<Enchere> enchereListe = null;
        List<Categorie> listCategorie = null;

        HttpSession session = request.getSession();
        utilisateur = (Utilisateur) session.getAttribute("utilisateur");

        if (utilisateur != null) {
            if (!filtreNom.trim().equals("")) {
                if (!searchCategorie.trim().equals("")) {
                    try {
                        categorie = categorieManager.selectByName(searchCategorie);
                        categorieId = categorie.getId();
                        enchereListe = enchereManager.selectEnchereVictoire(utilisateur, filtreNom ,categorieId);
                        listCategorie = categorieManager.selectAllCategorie();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    categorieId = 0;
                    try {
                        enchereListe = enchereManager.selectEnchereVictoire(utilisateur, filtreNom ,categorieId);
                        listCategorie = categorieManager.selectAllCategorie();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                request.setAttribute("enchereListe", enchereListe);
                request.setAttribute("listCategorie", listCategorie);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Pages/welcome.jsp");
                dispatcher.forward(request, response);
            } else {
                //error here
            }
        } else {
            response.sendRedirect("/encheres/error?error=NotConnected");
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}














