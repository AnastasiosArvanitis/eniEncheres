package fr.eni.eniEncheres.servlets.utilisateur;

import fr.eni.eniEncheres.bll.BllException;
import fr.eni.eniEncheres.bll.CategorieManager;
import fr.eni.eniEncheres.bll.EnchereManager;
import fr.eni.eniEncheres.bll.UtilisateurManager;
import fr.eni.eniEncheres.bo.Categorie;
import fr.eni.eniEncheres.bo.Enchere;
import fr.eni.eniEncheres.bo.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeleteProfil extends HttpServlet {
    UtilisateurManager utilisateurManager =  null;
    EnchereManager enchereManager = null;
    CategorieManager categorieManager = null;
    private String message = "";
    private String error = "";

    @Override
    public void init() throws ServletException {
        super.init();
        utilisateurManager = UtilisateurManager.getInstance();
        enchereManager = EnchereManager.getInstance();
        categorieManager = CategorieManager.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Enchere> enchereList = new ArrayList<>();
        List<Categorie> categorieList = new ArrayList<>();
        int id = Integer.parseInt(request.getParameter("id"));
        boolean verifDelete = false;
        try {
            verifDelete = utilisateurManager.delete(id);
            enchereList = enchereManager.selectAllEnchere();
            categorieList = categorieManager.selectAllCategorie();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            error = e.getMessage();
        }
            if(verifDelete){
                session.invalidate();
                request.setAttribute("listCategorie", categorieList);
                request.setAttribute("enchereListe", enchereList);
                request.getRequestDispatcher("WEB-INF/Pages/welcome.jsp").forward(request,response);
            }else{
                request.setAttribute("message",error);
                request.setAttribute("utilisateur",session);
                request.getRequestDispatcher("WEB-INF/Pages/updateProfil.jsp").forward(request,response);
            }
    }


}
