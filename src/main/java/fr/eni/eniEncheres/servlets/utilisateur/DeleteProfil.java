package fr.eni.eniEncheres.servlets.utilisateur;

import fr.eni.eniEncheres.bll.BllException;
import fr.eni.eniEncheres.bll.UtilisateurManager;
import fr.eni.eniEncheres.bo.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class DeleteProfil extends HttpServlet {
    UtilisateurManager utilisateurManager =  null;
    private String message = "";
    private String error = "";

    @Override
    public void init() throws ServletException {
        super.init();
        utilisateurManager = UtilisateurManager.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int id = Integer.parseInt(request.getParameter("id"));
        boolean verifDelete = false;
        try {
            verifDelete = utilisateurManager.delete(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            error = e.getMessage();
        }
            if(verifDelete){
                session.invalidate();
                request.getRequestDispatcher("WEB-INF/Pages/welcome.jsp").forward(request,response);
            }else{
                request.setAttribute("message",error);
                request.getRequestDispatcher("WEB-INF/Pages/profil.jsp").forward(request,response);
            }
    }
}