package fr.eni.eniEncheres.servlets.vente;

import fr.eni.eniEncheres.bll.*;
import fr.eni.eniEncheres.bo.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.*;


import java.nio.file.Paths;
import java.sql.SQLException;

import java.sql.Timestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB
public class AjoutVente extends HttpServlet {
    CategorieManager categorieManager = null;
    RetraitManager retraitManager = null;
    UtilisateurManager utilisateurManager = null;
    ArticleManager articleManager = null;
    EnchereManager enchereManager = null;

    @Override
    public void init() throws ServletException {
        super.init();
        categorieManager = CategorieManager.getInstance();
        retraitManager = RetraitManager.getInstance();
        utilisateurManager = UtilisateurManager.getInstance();
        articleManager = ArticleManager.getInstance();
        enchereManager = EnchereManager.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("entree dans la servlet");
        List<Categorie> listeCategorie = new ArrayList<>();
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        String idArticleString  = null;
        idArticleString =     request.getParameter("idArticle");

        Article article = null ;
        String action = null;

         if (idArticleString !=null)
        {
            try {
                article = articleManager.getArticleById(Integer.parseInt(idArticleString));

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (BllException e) {
                e.printStackTrace();
            }
        }

        try {
            listeCategorie = categorieManager.selectAllCategorie();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (BllException e) {
            e.printStackTrace();
        }
        // Je vérifie que le créateur de l'article demandé, correspond à l'utilisateur connecté avant de l'afficher dans l'écran de modification
        if (article != null){
            if (article.getUtilisateur().getId() == utilisateur.getId()){
                request.setAttribute("article",article);
                action ="maj";
                article.toString();
            }

        }
        request.setAttribute("action",action);
        request.setAttribute("listeCategorie",listeCategorie);
        request.getRequestDispatcher("WEB-INF/Ventes/ajoutVente.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Enchere> enchereList = new ArrayList<>();

        Article newArticle = null;
        Article addedArticle = null;

        Retrait retraitArticle = null;
        Retrait newRetrait = null;

        Categorie categorieArticle = null;
        List<Categorie> categorieList = new ArrayList<>();

        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

        String articleName = request.getParameter("articleName");
        String description = request.getParameter("description");
        String categorie = request.getParameter("categorie");

        InputStream fileContent = null;
        Part filePart = request.getPart("photo");

        String prixInitialString = request.getParameter("prixInitial");
        int prixInitial = Integer.parseInt(prixInitialString);


        String heureDebutEnchere = request.getParameter("heureDebutEnchere");
        String dateDebutEnchereString = request.getParameter("dateDebutEnchere");
        String dateHeureDebutEnchere = dateDebutEnchereString.concat(" ").concat(heureDebutEnchere).concat(":00");
        System.out.println("------------------------------- dateHeureDebutEnchere: " +dateHeureDebutEnchere);
        /*
        String dateDebutEnchereTimestamp = dateDebutEnchereString.replace("T", " ").concat(":00");*/
        Timestamp dateDebutEnchere = Timestamp.valueOf(dateHeureDebutEnchere);

        /*DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
        Instant instant = Instant.from(formatter.parse(dateDebutEnchereString));
        System.out.println("----------------- instant "+instant.toString());*/

        String heureFinEnchere = request.getParameter("heureFinEnchere");
        String dateFinEnchereString = request.getParameter("dateFinEnchere");
        String dateHeureFinEnchere = dateFinEnchereString.concat(" ").concat(heureFinEnchere).concat(":00");
        System.out.println("------------------------------- dateHeureFinEnchere: " +dateHeureFinEnchere);
        /*
        String dateFinEnchereTimestamp = dateFinEnchereString.replace("T", " ").concat(":00");*/
        Timestamp dateFinEnchere = Timestamp.valueOf(dateHeureFinEnchere);


        String rue = request.getParameter("rue");
        String codePostal = request.getParameter("codePostal");
        String ville = request.getParameter("ville");

        if (utilisateur == null) {
            response.sendRedirect("/encheres/error?error=NotConnected");
        } else {
            retraitArticle = new Retrait(rue, codePostal, ville);
            try {
                newRetrait = retraitManager.addNewRetrait(retraitArticle);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                categorieArticle = categorieManager.selectByName(categorie);
                System.out.println(newRetrait.toString());

                newArticle = new Article(utilisateur,categorieArticle, newRetrait, articleName, description,dateDebutEnchere,dateFinEnchere, prixInitial);
                addedArticle = articleManager.addNewArticle(newArticle);
                categorieList = categorieManager.selectAllCategorie();
                enchereList = enchereManager.selectAllEnchere();

                if (filePart != null && filePart.getSize() > 0) {
                    // prints out some information for debugging
                    System.out.println("-------------- uploaded file name: " +filePart.getName());
                    System.out.println("-------------- uploaded file size: " +filePart.getSize());
                    System.out.println("-------------- uploaded file type: " +filePart.getContentType());

                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    String[] fn = fileName.split("(\\.)");
                    String ext = fn[(fn.length-1)];

                    if (!ext.isEmpty() || !ext.equals("png") || !ext.equals("jpe") || !ext.equals("jpeg") || !ext.equals("jpg")) {
                        fileName = Integer.toString(addedArticle.getId()) + "." +ext;
                        String sContext = this.getServletContext().getRealPath("/");
                        System.out.println("------ VOUS FETES LE DOSSIERS \\upload\\images dans le dossier que vous voyez la ---> sContext: " +sContext);
                        System.out.println("--------------- new file name: " +fileName);
                        fileContent = filePart.getInputStream();
                        File file = new File(sContext + "\\WEB-INF\\upload\\images\\" +fileName);
                        try {
                            FileSave.receiveFile(fileContent, file);
                            System.out.println("-------------------------- I hope its ok");
                        } catch(IOException e) {
                            throw new Exception("Impossible de sauvagarder l'image");
                        }
                    }
                }

                request.setAttribute("enchereListe", enchereList);
                request.setAttribute("listCategorie", categorieList);
                System.out.println(addedArticle.toString());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Pages/welcome.jsp");
                dispatcher.forward(request, response);

            } catch (BllException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}


















