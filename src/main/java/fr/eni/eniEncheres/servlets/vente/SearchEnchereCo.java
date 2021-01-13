package fr.eni.eniEncheres.servlets.vente;

import fr.eni.eniEncheres.bll.*;
import fr.eni.eniEncheres.bo.Categorie;
import fr.eni.eniEncheres.bo.Enchere;
import fr.eni.eniEncheres.dal.DalException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchEnchereCo extends HttpServlet {
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
        List<Enchere> listeEnchere = new ArrayList<>();
        List<Categorie> listeCategorie = new ArrayList<>();
        int idCategorie = Integer.parseInt(request.getParameter("search-categorie"));
        String nomTitreArticle = null;
        nomTitreArticle = request.getParameter("search-name");
        int idUtilisateur = Integer.parseInt(request.getParameter("idUtilisateur"));
        int checkbox = 0;
        String enchereOuvert = "";
        String enchereEnCours = "";
        String enchereRemporte ="";

        enchereOuvert = request.getParameter("check-enchereOuvert");
        System.out.println(enchereOuvert);
        enchereEnCours = request.getParameter("check-enchereEnCours");
        System.out.println(enchereEnCours);
        enchereRemporte = request.getParameter("check-enchereRemporte");
        System.out.println(enchereRemporte);

        if (enchereOuvert == null){
            enchereOuvert ="";
        }
        if(enchereEnCours == null){
            enchereEnCours = "";
        }
        if (enchereRemporte == null) {
            enchereRemporte = "";
        }

        if(enchereOuvert.equals("enchereOuvert")) {
            if(enchereOuvert.equals("enchereOuverte") && enchereEnCours.equals("enchereEnCours")){
                checkbox = 4;
            }else if(enchereOuvert.equals("enchereOuvert") && enchereRemporte.equals("enchereRemporte")){
                checkbox = 5;
            }else if((enchereOuvert.equals("enchereOuvert") && enchereRemporte.equals("enchereRemporte") && enchereEnCours.equals("enchereEnCours"))){
                checkbox = 7;
            }else {
                checkbox = 1;
            }
        }else if(enchereEnCours.equals("enchereEnCours")){
            if(enchereEnCours.equals("enchereEnCours") && enchereRemporte.equals("enchereRemporte")){
                checkbox = 6;
            }else {
                checkbox = 2;
            }
        }else if(enchereRemporte.equals("enchereRemporte")){
            checkbox = 3;
        }

        try {
            listeEnchere = enchereManager.afficherRequetCo(nomTitreArticle,idCategorie, checkbox, idUtilisateur);
            listeCategorie = categorieManager.selectAllCategorie();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DalException e) {
            e.printStackTrace();
        } catch (BllException e) {
            e.printStackTrace();
        }
        request.setAttribute("enchereListe", listeEnchere);
        request.setAttribute("listCategorie",listeCategorie);
        request.getRequestDispatcher("WEB-INF/Pages/welcome.jsp").forward(request,response);
    }
}