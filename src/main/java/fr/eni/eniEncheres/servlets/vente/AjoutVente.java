package fr.eni.eniEncheres.servlets.vente;

import fr.eni.eniEncheres.bll.*;
import fr.eni.eniEncheres.bo.Article;
import fr.eni.eniEncheres.bo.Categorie;
import fr.eni.eniEncheres.bo.Retrait;
import fr.eni.eniEncheres.bo.Utilisateur;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AjoutVente extends HttpServlet {
    CategorieManager categorieManager = null;
    RetraitManager retraitManager = null;
    UtilisateurManager utilisateurManager = null;
    ArticleManager articleManager = null;

    @Override
    public void init() throws ServletException {
        super.init();
        categorieManager = CategorieManager.getInstance();
        retraitManager = RetraitManager.getInstance();
        utilisateurManager = UtilisateurManager.getInstance();
        articleManager = ArticleManager.getInstance();
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Article newArticle = null;
        Article addedArticle = null;
        Utilisateur utilisateurArticle = null;
        Retrait retraitArticle = null;
        Categorie categorieArticle = null;

        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

        String articleName = request.getParameter("articleName");
        String description = request.getParameter("description");
        String categorie = request.getParameter("categorie");

        String prixInitialString = request.getParameter("prixInitial");
        int prixInitial = Integer.parseInt(prixInitialString);

        String dateDebutEnchereString = request.getParameter("dateDebutEnchere");
        Date dateDebutEnchere = Date.valueOf(dateDebutEnchereString);
        String dateFinEnchereString = request.getParameter("dateFinEnchere");
        Date dateFinEnchere = Date.valueOf(dateFinEnchereString);

        String rue = request.getParameter("rue");
        String codePostal = request.getParameter("codePostal");
        String ville = request.getParameter("ville");
        System.out.println("Ajoute Vente POST");
        if (utilisateur == null) {
            response.sendRedirect("/encheres/error?error=ventWithoutLogin");
        } else {
            try {
                categorieArticle = categorieManager.selectByName(categorie);
                System.out.println(categorieArticle.toString());
                retraitArticle = new Retrait(rue, codePostal, ville);
                newArticle = new Article(utilisateur,categorieArticle, retraitArticle, articleName, description,dateDebutEnchere,dateFinEnchere, prixInitial);
                addedArticle = articleManager.addNewArticle(newArticle);
                System.out.println(addedArticle.toString());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Pages/welcome.jsp");
                dispatcher.forward(request, response);

            } catch (BllException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}


















