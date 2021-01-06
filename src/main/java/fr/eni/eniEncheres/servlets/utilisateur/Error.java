package fr.eni.eniEncheres.servlets.utilisateur;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class Error extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorParametere = request.getParameter("error");
        String errorMessage = "";

        switch (errorParametere) {
            case "userNotFound":
                errorMessage = "L'utilisateur n'exist pas...";
                break;
            default:
                errorMessage = "The page that you are looking for was not found, please try again";
                break;
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Pages/error.jsp?error=" +errorMessage);
        dispatcher.forward(request, response);
    }
}
