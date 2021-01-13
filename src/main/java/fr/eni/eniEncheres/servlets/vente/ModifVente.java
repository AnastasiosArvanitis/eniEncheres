package fr.eni.eniEncheres.servlets.vente;

import fr.eni.eniEncheres.bll.*;
import fr.eni.eniEncheres.bo.*;
import fr.eni.eniEncheres.dal.DalException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "modifVente", value = "/ModifVente")
public class ModifVente extends HttpServlet {
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

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        String action = request.getParameter("action");
        String articleIdString = request.getParameter("article");
        int articleId = Integer.parseInt("articleIdString");
        String retraitIdString = request.getParameter("retrait");
        int retraitId = Integer.parseInt("retraitIdString");





        List<Enchere> enchereList = new ArrayList<>();

        Article modifArticle = null;

        Retrait modifRetrait = null;

        Categorie categorieArticle = null;
        List<Categorie> categorieList = new ArrayList<>();



        String articleName = request.getParameter("articleName");
        String description = request.getParameter("description");
        String categorie = request.getParameter("categorie");

        String prixInitialString = request.getParameter("prixInitial");
        int prixInitial = Integer.parseInt(prixInitialString);


        String heureDebutEnchere = request.getParameter("heureDebutEnchere");
        String dateDebutEnchereString = request.getParameter("dateDebutEnchere");
        String dateHeureDebutEnchere = dateDebutEnchereString.concat(" ").concat(heureDebutEnchere).concat(":00");
        System.out.println("------------------------------- dateHeureDebutEnchere: " +dateHeureDebutEnchere);

        Timestamp dateDebutEnchere = Timestamp.valueOf(dateHeureDebutEnchere);

        String heureFinEnchere = request.getParameter("heureFinEnchere");
        String dateFinEnchereString = request.getParameter("dateFinEnchere");
        String dateHeureFinEnchere = dateFinEnchereString.concat(" ").concat(heureFinEnchere).concat(":00");

        Timestamp dateFinEnchere = Timestamp.valueOf(dateHeureFinEnchere);

        String rue = request.getParameter("rue");
        String codePostal = request.getParameter("codePostal");
        String ville = request.getParameter("ville");

        if(action.equals("supprimer")) {
            // verif Idutilisateurconnecte;
            //ethodeSupprimer(idArticle);
        }
        else if(action.equals("modifier")){
            modifRetrait.setId(retraitId);
            modifRetrait.setRue(rue);
            modifRetrait.setCodePostal(codePostal);
            modifRetrait.setVille(ville);

            try {
                categorieArticle = categorieManager.selectByName(categorie);
                utilisateur = utilisateurManager.selectById(utilisateur.getId());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (BllException e) {
                e.printStackTrace();
            }

            modifArticle.setId(articleId);
            modifArticle.setUtilisateur(utilisateur);
            modifArticle.setCategorie(categorieArticle);
            modifArticle.setRetrait(modifRetrait);
            modifArticle.setNom(articleName);
            modifArticle.setDescription(description);
            modifArticle.setDateDebutEncheres(dateDebutEnchere);
            modifArticle.setDateFinEncheres(dateFinEnchere);
            modifArticle.setPrixInitial(prixInitial);

            System.out.println(modifArticle);
            int articleIdModifier= 0;
            try {
                 articleIdModifier = articleManager.updateArticle(modifArticle);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (DalException e) {
                e.printStackTrace();
            }

            RequestDispatcher rd = request.getRequestDispatcher("/detailEnchere?idArticle="+articleId);
            rd.forward(request,response);

        }
    }
}
