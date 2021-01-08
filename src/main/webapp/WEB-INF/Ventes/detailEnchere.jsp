<%@ page import="fr.eni.eniEncheres.bo.Categorie" %>
<%@ page import="java.util.List" %><%--

  Created by IntelliJ IDEA.
  User: vincdev
  Date: 06/01/2021
  Time: 21:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>ENI_Enchere</title>
    <style>
        <%@ include file="../../css/_global.css" %>
        <%@ include file="../../css/ajoutVente.css" %>
    </style>

</head>
<body>
<%@ include file="../Pages/header.jsp" %>
<main>
    <h2>Détail Vente</h2>
    <article>
        <img src="#" alt="">
    </article>
    <article>
        <h3></h3>
        <p>Description :${enchere.article.nom}</p>
        <p></p>
        <p>Catégorie :${enchere.article.categorie.libelle} </p>
        <p></p>
        <p>Meilleur Offre : ${enchere.montantEnchere}  pts par ${enchere.utilisateur.pseudo}</p>
        <p></p>
        <p>Mise a prix :  ${enchere.article.prixInitial} pts</p>
        <p></p>
        <p>Fin de l'enchère :  ${enchere.article.dateFinEncheres}</p>

        <p>Adresse de retrait : </p><p> ${enchere.article.retrait.rue}</p>
        <p> ${enchere.article.retrait.codePostal} ${enchere.article.retrait.ville}</p>
        <p>Vendeur :</p>

        <p>${enchere.article.utilisateur.pseudo}</p>

        <div>
            <form>
                <label for="montantEnchere"> </label>

                <input type="submit" value="Enchérir"/>

            </form>
        </div>

    </article>
</main>

<%@ include file="../Pages/footer.jsp" %>
</body>
</html>
