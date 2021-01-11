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
import java.sql.SQLException;
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
        String searchNom = request.getParameter("search-article");
        String searchCategorie = request.getParameter("search-categorie");

        String radioAchat = request.getParameter("radio-achat");
        String checkEnchereOuvert = request.getParameter("check-enchereOuvert");
        String checkEnchereEnCours = request.getParameter("check-enchereEnCours");
        String checkEnchereRemporte = request.getParameter("check-enchereRemporte");

        String radioVente = request.getParameter("radio-vente");
        String checkVenteEnCours = request.getParameter("check-venteEnCours");
        String checkVenteNonDebute = request.getParameter("check-venteNonDebute");
        String checkVenteTermine = request.getParameter("check-venteTermine");



        int categorieId = 0;
        Article article = null;
        Categorie categorie = null;
        Utilisateur utilisateur = null;

        List<Enchere>   enchereOuvertList = null;
        List<Enchere>   enchereEnCoursList = null;
        List<Enchere>   enchereRemporteesList = null;

        List<Enchere> venteEnCoursList = null;
        List<Enchere> venteNonDebuteeList = null;
        List<Enchere> venteTermineList = null;

        List<Enchere>   enchereListe = null;
        List<Categorie> listCategorie = null;

        HttpSession session = request.getSession();
        utilisateur = (Utilisateur) session.getAttribute("utilisateur");

        try {
            categorie = categorieManager.selectByName(searchCategorie);
            categorieId = categorie.getId();
            listCategorie = categorieManager.selectAllCategorie();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String filtreNom = (searchNom.equals("")) ? "0" : searchNom;
        int filtreCategorie = (searchCategorie.equals("")) ? 0 : categorieId; //to pairnei h oxi?

        request.setAttribute("listCategorie", listCategorie);
        RequestDispatcher dispatcher = null;

        if (utilisateur != null) {
            //mode connecté
            if (radioAchat.equals("radioAchat")) {
                //Encheres
                try {
                    enchereOuvertList = enchereManager.selectAllEnchere();
                    enchereEnCoursList = enchereManager.selectEnchereByUtilisateur(utilisateur, filtreNom, filtreCategorie);
                    enchereRemporteesList = enchereManager.selectEnchereVictoire(utilisateur, filtreNom, filtreCategorie);
                }   catch (Exception e) {
                    e.printStackTrace();
                }
              if ( checkEnchereOuvert.equals("enchereOuvert") ) {
                  enchereListe.addAll(enchereOuvertList);
              }
              if ( checkEnchereEnCours.equals("enchereEnCours") ) {
                  enchereListe.addAll(enchereEnCoursList);
              }
              if ( checkEnchereRemporte.equals("enchereRemporte") ) {
                  enchereListe.addAll(enchereRemporteesList);
              }
                if ( (enchereOuvertList != null) && (enchereEnCoursList != null) && (enchereRemporteesList != null) ) {
                    try {
                        enchereListe = enchereManager.selectAllEnchere();
                    }   catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } //end radioAchats

            else if (radioVente.equals("radioVente")) {
                //Ventes
                try {
                    venteEnCoursList = enchereManager.getEnchereVendeur(utilisateur, filtreNom, filtreCategorie);
                    venteNonDebuteeList = enchereManager.getEnchereVendeurFutur(utilisateur, filtreNom, filtreCategorie);
                    venteTermineList = enchereManager.getEnchereVendeurTermine(utilisateur, filtreNom, filtreCategorie);
                }  catch (Exception e) {
                    e.printStackTrace();
                }
                if ( (checkVenteEnCours.equals("venteEnCours"))) {
                   enchereListe.addAll(venteEnCoursList);
                }
                if (checkVenteNonDebute.equals("venteNonDebute")) {
                    enchereListe.addAll(venteNonDebuteeList);
                }
                if (checkVenteTermine.equals("venteTermine")) {
                    enchereListe.addAll(venteTermineList);
                }
                if ( (checkVenteEnCours == null) && (checkVenteNonDebute == null) && (checkVenteTermine == null)) {
                  try {
                      enchereListe = enchereManager.getEnchereVendeur(utilisateur, filtreNom, filtreCategorie);
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
                }
            }

            request.setAttribute("enchereListe", enchereListe);
            dispatcher = request.getRequestDispatcher("/WEB-INF/Pages/welcome.jsp");
            dispatcher.forward(request, response);

        } else {
            //mode deconnecté
            try {
                enchereListe = enchereManager.selectAllEnchere();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (BllException e) {
                e.printStackTrace();
            }
            request.setAttribute("enchereListe", enchereListe);
            dispatcher = request.getRequestDispatcher("/WEB-INF/Pages/welcome.jsp");
            dispatcher.forward(request, response);
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}














