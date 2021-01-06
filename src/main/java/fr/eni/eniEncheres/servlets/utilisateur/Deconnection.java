package fr.eni.eniEncheres.servlets.utilisateur;

import fr.eni.eniEncheres.tools.EnchereLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "Deconnection")
public class Deconnection extends HttpServlet {

    private Logger logger = EnchereLogger.getLogger("Deconnection");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            session.removeAttribute("utilisateur");
            session.invalidate();
            response.sendRedirect("/encheres/");
        } catch (Exception e) {
            logger.severe("Error servlet Deconnection " + e.getMessage() + "\n");
            e.getMessage();
        }
    }
}
