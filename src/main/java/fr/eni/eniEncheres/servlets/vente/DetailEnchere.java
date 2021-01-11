package fr.eni.eniEncheres.servlets.vente;

import fr.eni.eniEncheres.bll.*;
import fr.eni.eniEncheres.bo.*;
import fr.eni.eniEncheres.dal.DalException;
import fr.eni.eniEncheres.dal.FactoryDao;

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
    UtilisateurManager utilisateurManager = null;
    private String message="";

    @Override
    public void init() throws ServletException {
        super.init();
        enchereManager = EnchereManager.getInstance();
        utilisateurManager = UtilisateurManager.getInstance();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        int idArticle = Integer.parseInt(request.getParameter("idArticle"));
        Enchere enchere = new Enchere();
        try {
           enchere = enchereManager.getEnchereArticle(idArticle);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (DalException e) {
            e.printStackTrace();
        } catch (BllException e) {
            e.printStackTrace();
        }
        request.setAttribute("utilisateur",utilisateur);
        request.setAttribute("enchere",enchere);
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Ventes/detailEnchere.jsp");
        rd.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

        int montantEnchere = Integer.parseInt( request.getParameter("montantEnchere"));
        int idArticle = Integer.parseInt(request.getParameter("idArticle"));
        int idUtilisateur = Integer.parseInt(request.getParameter("idUtilisateur"));
        Enchere enchereRetourner = null;
        Utilisateur utilisateurRenvoyer = null;
        Enchere enchere = null;
        Utilisateur acheteur = null;

            try {
                enchere = FactoryDao.getEnchereDao().selectEnchereByIdArticle(idArticle);
                acheteur = utilisateurManager.selectById(idUtilisateur);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (DalException e) {
                e.printStackTrace();
            } catch (BllException e) {
                e.printStackTrace();
            }

        //prix de vente est soit = au prix initial ou soit supperieur
        if((enchere.getArticle().getPrixInitial() <= enchere.getArticle().getPrixVente()) || (enchere.getArticle().getPrixVente() == 0)){
            //compare le montantEnchere avec le prix article
            if (enchere.getArticle().getPrixVente() < montantEnchere){
                //controle pour savoir si l'acheteur a deja fais la derniere enchere
                if((enchere.getUtilisateur() == null) || (acheteur.getId() != enchere.getUtilisateur().getId())) {
                //controle pour savoir si le credit de l'utilisateur est superrieur au prix de vente
                    if(acheteur.getCredit() >= enchere.getArticle().getPrixVente()) {
                        try {
                                enchereRetourner = enchereManager.addNewEnchere(acheteur,idArticle,montantEnchere);
                                utilisateurRenvoyer = utilisateurManager.selectById(idUtilisateur);
                                message="Votre enchere a reussi !";
                                session.setAttribute("utilisateur",utilisateurRenvoyer);
                                request.setAttribute("message_succes",message);
                                request.setAttribute("enchere", enchereRetourner);
                                request.getRequestDispatcher("WEB-INF/Ventes/detailEnchere.jsp").forward(request,response);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            } catch (BllException e) {
                                e.printStackTrace();
                            } catch (DalException e) {
                            e.printStackTrace();
                        }

                    }else {
                        message="Votre Credit est inferieur au montant de l'enchere";
                        session.setAttribute("utilisateur",acheteur);
                        request.setAttribute("enchere",enchere);
                        request.setAttribute("message",message);
                        request.getRequestDispatcher("WEB-INF/Ventes/detailEnchere.jsp").forward(request,response);
                    }
            } else{
                    message="Vous etes deja le dernier encherisseur";
                    session.setAttribute("utilisateur",acheteur);
                    request.setAttribute("enchere",enchere);
                    request.setAttribute("message",message);
                    request.getRequestDispatcher("WEB-INF/Ventes/detailEnchere.jsp").forward(request,response);
            }
            }else {
                message="Prix de vente supperieur au montant de l'enchere";
                session.setAttribute("utilisateur",acheteur);
                request.setAttribute("enchere",enchere);
                request.setAttribute("message",message);
                request.getRequestDispatcher("WEB-INF/Ventes/detailEnchere.jsp").forward(request,response);
            }
        }else{
            message="le prix initial est supperieur aux prix de vente ";
            session.setAttribute("utilisateur",acheteur);
            request.setAttribute("enchere",enchere);
            request.setAttribute("message",message);
            request.getRequestDispatcher("WEB-INF/Ventes/detailEnchere.jsp").forward(request,response);
        }
    }
}


















