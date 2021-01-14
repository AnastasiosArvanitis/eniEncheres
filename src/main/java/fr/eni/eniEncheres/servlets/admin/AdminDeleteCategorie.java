package fr.eni.eniEncheres.servlets.admin;

import fr.eni.eniEncheres.bll.BllException;
import fr.eni.eniEncheres.bll.CategorieManager;
import fr.eni.eniEncheres.bll.UtilisateurManager;
import fr.eni.eniEncheres.bo.Categorie;
import fr.eni.eniEncheres.bo.Utilisateur;
import fr.eni.eniEncheres.dal.DalException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDeleteCategorie extends HttpServlet {
    UtilisateurManager utilisateurManager = null;
    CategorieManager categorieManager = null;

    @Override
    public void init() throws ServletException {
        super.init();
        utilisateurManager = UtilisateurManager.getInstance();
        categorieManager = CategorieManager.getInstance();

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Utilisateur> listUtilisateur = new ArrayList<>();
        List<Categorie> listeCategorie = new ArrayList<>();
        int id = Integer.parseInt(request.getParameter("id"));
        boolean delete = false;
        String message = "";
        try {
            delete = categorieManager.delete(id);
            listUtilisateur = utilisateurManager.selectAllUtilisateur();
            listeCategorie = categorieManager.selectAllCategorie();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (BllException e) {
            e.printStackTrace();
        } catch (DalException e) {
            e.printStackTrace();
        }
        if(delete != false){
            message = " suppression reussi !";
            request.setAttribute("messageSucces",message);
            request.setAttribute("listCategorie", listeCategorie);
            request.setAttribute("listUtilisateur", listUtilisateur);
            request.getRequestDispatcher("WEB-INF/Admin/admin.jsp").forward(request,response);
        }else{
            message = " Erreur lors de la suppression categorie ";
            request.setAttribute("message",message);
            request.setAttribute("listCategorie", listeCategorie);
            request.setAttribute("listUtilisateur", listUtilisateur);
            request.getRequestDispatcher("WEB-INF/Admin/admin.jsp").forward(request,response);
        }
    }
}
