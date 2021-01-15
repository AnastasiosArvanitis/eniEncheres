package fr.eni.eniEncheres.servlets.utilisateur;

import fr.eni.eniEncheres.bll.BllException;
import fr.eni.eniEncheres.bll.UtilisateurManager;
import fr.eni.eniEncheres.bo.Utilisateur;
import fr.eni.eniEncheres.dal.DalException;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "RecuperationMdp", value = "/RecuperationMdp")
public class RecuperationMdp extends HttpServlet {

    private UtilisateurManager utilisateurManager = null;
    private String error = "";

    @Override
    public void init() throws ServletException {
        super.init();
        utilisateurManager = UtilisateurManager.getInstance();
    }



    @Override

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String connectionIdentifiant = request.getParameter("connectionIdentifiant");

        Utilisateur utilisateurmdp = null;
        String lienEmail = null;
        String cle = null ;
        cle = request.getParameter("cle");
        String error = "Vous n'êtes pas autorisé à la page demandé , veuillez vous identifier";
        if (cle!=null) {
            request.setAttribute("cle", cle);
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Pages/modificationMdp.jsp");
            rd.forward(request,response);
        }




        boolean utilisateurExist = false;

        try {
            utilisateurExist = utilisateurManager.verifUtilisateurLogin(connectionIdentifiant);
            if (!utilisateurExist) {

                error = "Mot de passe ou pseudo incorrect";
                request.setAttribute("message", error);
                request.getRequestDispatcher("/WEB-INF/Pages/connection.jsp").forward(request, response);

            } else {
                utilisateurmdp = utilisateurManager.selectLogin(connectionIdentifiant);
                String cleGenere = (utilisateurmdp.getNom().substring(1,2)+utilisateurmdp.getCodePostal().substring(1,4)+utilisateurmdp.getId()+"A"+utilisateurmdp.getCredit()+utilisateurmdp.getMotDePasse().substring(1,2) + utilisateurmdp.getMotDePasse().length());
                lienEmail = "localhost:8080\\encheres\\RecuperationMdp?cle="+cleGenere;


                error = "Vous allez recevoir un email vous invitant à modifier votre mot de passe :" + lienEmail;
                request.setAttribute("success", error);
                request.getRequestDispatcher("/WEB-INF/Pages/connection.jsp").forward(request, response);
            }
        } catch (BllException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (DalException e) {
            e.printStackTrace();
        }

        request.setAttribute("clef",cle);

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String motDePasse = null;
        String mdpConfirm = null;
        String cle = null;
        String erreur = null;

        motDePasse = request.getParameter("motDePasse");
        mdpConfirm = request.getParameter("mdpConfirm") ;
        cle= request.getParameter("cle");

        if (!mdpConfirm.equals(motDePasse)){
            erreur = "mot de passe non identique";
            request.setAttribute("erreur",erreur);
            request.setAttribute("cle",cle);
            RequestDispatcher dispatcher;
            dispatcher = request.getRequestDispatcher("/WEB-INF/Pages/modificationMdp.jsp");
            dispatcher.forward(request, response);}
        else{
            try {
                utilisateurManager.modifMotDePasse(motDePasse,cle);
                error = "Vous pouvez désormais vous connecter avec votre nouveau mot de passe";

            } catch (SQLException throwables) {
                error= "la modification n'a pas pu être effectué";
            } catch (DalException e) {
                error = e.getMessage();
            }

            request.setAttribute("success", error);
            request.getRequestDispatcher("/WEB-INF/Pages/connection.jsp").forward(request, response);



    }}
}
