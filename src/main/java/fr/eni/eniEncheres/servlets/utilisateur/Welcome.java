package fr.eni.eniEncheres.servlets.utilisateur;

import fr.eni.eniEncheres.bll.BllException;
import fr.eni.eniEncheres.bll.CategorieManager;
import fr.eni.eniEncheres.bll.EnchereManager;
import fr.eni.eniEncheres.bll.UtilisateurManager;
import fr.eni.eniEncheres.bo.Categorie;
import fr.eni.eniEncheres.bo.Enchere;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Welcome extends HttpServlet {
        EnchereManager enchereManager = null;
        CategorieManager categorieManager = null;

    @Override
    public void init() throws ServletException {
        super.init();
        enchereManager = EnchereManager.getInstance();
        categorieManager = CategorieManager.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Enchere> enchereList = new ArrayList<>();
        List<Categorie> categorieList = new ArrayList<>();
        try {
            enchereList = enchereManager.selectAllEnchere();
            categorieList = categorieManager.selectAllCategorie();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (BllException e) {
            e.printStackTrace();
        }
        request.setAttribute("listCategorie", categorieList);
        request.setAttribute("enchereListe", enchereList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Pages/welcome.jsp");
        dispatcher.forward(request, response);
    }
}
