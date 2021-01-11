package fr.eni.eniEncheres.servlets.vente;

import fr.eni.eniEncheres.bll.*;
import fr.eni.eniEncheres.bo.*;

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("entree dans la servlet");
        List<Categorie> listeCategorie = new ArrayList<>();
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        String idArticleString  = null;
        idArticleString =     request.getParameter("idArticle");

        Article article = null ;
        String action = null;

         if (idArticleString !=null)
        {
            try {
                article = articleManager.getArticleById(Integer.parseInt(idArticleString));

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (BllException e) {
                e.printStackTrace();
            }
        }

        try {
            listeCategorie = categorieManager.selectAllCategorie();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (BllException e) {
            e.printStackTrace();
        }
        // Je vérifie que le créateur de l'article demandé, correspond à l'utilisateur connecté avant de l'afficher dans l'écran de modification
        if (article != null){
            if (article.getUtilisateur().getId() == utilisateur.getId()){
                request.setAttribute("article",article);
                action ="maj";
                article.toString();
            }

        }
        request.setAttribute("action",action);
        request.setAttribute("listeCategorie",listeCategorie);
        request.getRequestDispatcher("WEB-INF/Ventes/ajoutVente.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Enchere> enchereList = new ArrayList<>();

        Article newArticle = null;
        Article addedArticle = null;

        Retrait retraitArticle = null;
        Retrait newRetrait = null;

        Categorie categorieArticle = null;
        List<Categorie> categorieList = new ArrayList<>();

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

        if (utilisateur == null) {
            response.sendRedirect("/encheres/error?error=ventWithoutLogin");
        } else {
            retraitArticle = new Retrait(rue, codePostal, ville);
            try {
                newRetrait = retraitManager.addNewRetrait(retraitArticle);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                categorieArticle = categorieManager.selectByName(categorie);
                System.out.println(newRetrait.toString());

                newArticle = new Article(utilisateur,categorieArticle, newRetrait, articleName, description,dateDebutEnchere,dateFinEnchere, prixInitial);
                addedArticle = articleManager.addNewArticle(newArticle);
                categorieList = categorieManager.selectAllCategorie();
                enchereList = enchereManager.selectAllEnchere();

                request.setAttribute("enchereListe", enchereList);
                request.setAttribute("listCategorie", categorieList);
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


















