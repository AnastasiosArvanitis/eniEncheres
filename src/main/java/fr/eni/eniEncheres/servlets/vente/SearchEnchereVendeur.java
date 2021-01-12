package fr.eni.eniEncheres.servlets.vente;

import fr.eni.eniEncheres.bll.*;
import fr.eni.eniEncheres.bo.Categorie;
import fr.eni.eniEncheres.bo.Enchere;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




public class SearchEnchereVendeur extends HttpServlet {
    CategorieManager categorieManager = null;
    UtilisateurManager utilisateurManager = null;
    ArticleManager articleManager = null;
    EnchereManager enchereManager = null;

    @Override
    public void init() throws ServletException {
        super.init();
        categorieManager = CategorieManager.getInstance();
        utilisateurManager = UtilisateurManager.getInstance();
        articleManager = ArticleManager.getInstance();
        enchereManager = EnchereManager.getInstance();
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("entre dans la servlet encherevendeur");
        List<Enchere> listeEnchere = new ArrayList<>();
        List<Categorie> listeCategorie = new ArrayList<>();

        String check_venteEnCours = null;
        String check_venteNonDebute   = null;
        String check_venteTermine   = null;

        int idUtilisateur = 0;
        int idCategorie   =0 ;
        String nomTitreArticle = "0";

        // Je récupère l'utilisateur

        idUtilisateur = Integer.parseInt(request.getParameter("idUtilisateur"));
        idCategorie   = Integer.parseInt(request.getParameter("search-categorie"));
        nomTitreArticle = request.getParameter("search-name");

        nomTitreArticle = request.getParameter("search-name");


        // Je récupere les paramètres du checkbox
         check_venteEnCours   = request.getParameter("check-venteEnCours");
         check_venteNonDebute = request.getParameter("check-venteNonDebute");
         check_venteTermine   = request.getParameter("check-venteTermine");


        List<String> conditions  =  new ArrayList<>();
        // Je check les valeurs coché dans une liste
        if(check_venteEnCours != null)
         conditions.add(check_venteEnCours) ;
        if(check_venteNonDebute != null)
         conditions.add(check_venteNonDebute) ;
        if(check_venteTermine != null)
         conditions.add(check_venteTermine) ;


        try {
            listeEnchere = enchereManager.selectAllEncheresVendeur( idUtilisateur,  conditions, idCategorie, nomTitreArticle);
            listeCategorie = categorieManager.selectAllCategorie();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (BllException e) {
            e.printStackTrace();
        }

        request.setAttribute("enchereListe", listeEnchere);
        request.setAttribute("listCategorie",listeCategorie);
        request.getRequestDispatcher("WEB-INF/Pages/welcome.jsp").forward(request,response);


    }
}


