package fr.eni.eniEncheres.servlets.admin;

import fr.eni.eniEncheres.bll.BllException;
import fr.eni.eniEncheres.bll.CategorieManager;
import fr.eni.eniEncheres.bll.EnchereManager;
import fr.eni.eniEncheres.bll.UtilisateurManager;
import fr.eni.eniEncheres.bo.Categorie;
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

public class Admin extends HttpServlet {

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
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (utilisateur == null) {
            response.sendRedirect("/encheres/error?error=NotConnected");
        } else {
            List<Utilisateur> listUtilisateur = new ArrayList<>();
            List<Categorie> listeCategorie = new ArrayList<>();
            try {
                listUtilisateur = utilisateurManager.selectAllUtilisateur();
                listeCategorie = categorieManager.selectAllCategorie();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (BllException e) {
                e.printStackTrace();
            }
            request.setAttribute("listCategorie", listeCategorie);
            request.setAttribute("listUtilisateur", listUtilisateur);
            request.getRequestDispatcher("WEB-INF/Admin/admin.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        List<Utilisateur> listUtilisateur = new ArrayList<>();
        List<Categorie> listeCategorie = new ArrayList<>();
        String message = "";
        String messageSucces = "";
        boolean deleteOk = false;

        try {
            deleteOk = utilisateurManager.delete(id);
            listUtilisateur = utilisateurManager.selectAllUtilisateur();
            listeCategorie = categorieManager.selectAllCategorie();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (deleteOk == true){
            messageSucces = " Suppression de l'utilisateur reussi !";
            request.setAttribute("messageSucces",messageSucces);
            request.setAttribute("listUtilisateur", listUtilisateur);
            request.setAttribute("listCategorie", listeCategorie);
            request.getRequestDispatcher("WEB-INF/Admin/admin.jsp").forward(request,response);
        }else {
            message = " Erreur lors de la suppression de l'utilisateur ";
            request.setAttribute("message",message);
            request.setAttribute("listUtilisateur", listUtilisateur);
            request.setAttribute("listCategorie", listeCategorie);
            request.getRequestDispatcher("WEB-INF/Admin/admin.jsp").forward(request,response);
        }
    }
}
