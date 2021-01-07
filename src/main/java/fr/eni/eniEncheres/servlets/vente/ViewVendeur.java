package fr.eni.eniEncheres.servlets.vente;

import fr.eni.eniEncheres.bo.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ViewVendeur extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Utilisateur vendeur = (Utilisateur) request.getAttribute("vendeur");
        Utilisateur vendeur = new Utilisateur(56,"tigre56","Alain","Terrieur","tigre@gmail.com","0602020202","rue de paris","75000","Paris","pass",0,false,true);
        request.setAttribute("vendeur",vendeur);
        request.getRequestDispatcher("WEB-INF/Ventes/viewVendeur.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }
}
