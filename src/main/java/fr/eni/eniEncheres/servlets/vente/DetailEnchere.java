package fr.eni.eniEncheres.servlets.vente;

import fr.eni.eniEncheres.bll.*;
import fr.eni.eniEncheres.bo.*;
import fr.eni.eniEncheres.dal.DalException;

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

public class DetailEnchere extends HttpServlet {
    EnchereManager enchereManager = null;

    @Override
    public void init() throws ServletException {
        super.init();
        enchereManager = EnchereManager.getInstance();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idArticle = Integer.parseInt(request.getParameter("idArticle"));

        Enchere enchere = new Enchere();
        try {
           enchere = enchereManager.getEnchereArticle(idArticle);
            System.out.println(enchere);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (DalException e) {
            e.printStackTrace();
        } catch (BllException e) {
            e.printStackTrace();
        }
        request.setAttribute("enchere",enchere);
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Ventes/detailEnchere.jsp");
        rd.forward(request,response);



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {





    }
}


















