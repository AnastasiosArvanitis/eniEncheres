package fr.eni.eniEncheres.servlets.vente;

import fr.eni.eniEncheres.bll.*;
import fr.eni.eniEncheres.bo.Categorie;
import fr.eni.eniEncheres.bo.Enchere;
import fr.eni.eniEncheres.dal.DalException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchEnchere extends HttpServlet {
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
        String choixRadio = "";
        choixRadio   = request.getParameter("radio");
        RequestDispatcher rd = null;
        if (choixRadio.equals("radioAchat")) {
            rd = request.getRequestDispatcher("//search_co");
            rd.forward(request, response);
        } else if (choixRadio.equals("radioVente")) {
            rd = request.getRequestDispatcher("/searchEnchereVendeur");
            rd.forward(request, response);
        } else {
            List<Enchere> listeEnchere = new ArrayList<>();
            List<Categorie> listeCategorie = new ArrayList<>();
            int idCategorie = Integer.parseInt(request.getParameter("search-categorie"));
            String nomTitreArticle = request.getParameter("search-name");
            try {
                listeEnchere = enchereManager.afficherRequete(nomTitreArticle, idCategorie);
                listeCategorie = categorieManager.selectAllCategorie();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (DalException e) {
                e.printStackTrace();
            } catch (BllException e) {
                e.printStackTrace();
            }
            request.setAttribute("enchereListe", listeEnchere);
            request.setAttribute("listCategorie", listeCategorie);
            request.getRequestDispatcher("WEB-INF/Pages/welcome.jsp").forward(request, response);
        }
    }
}
