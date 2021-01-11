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
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
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
        <p>Description : </p><p>${enchere.article.nom}</p>
        <p>Catégorie : </p><p>${enchere.article.categorie.libelle}</p>
        <p>Meilleur Offre : </p><p>${enchere.montantEnchere}  pts par ${enchere.utilisateur.pseudo}</p>
        <p>Mise a prix : </p><p>${enchere.article.prixInitial} pts</p>
        <p>Fin de l'enchère :  </p><p>${enchere.article.dateFinEncheres}</p>
        <p>Adresse de retrait : </p><p> ${enchere.article.retrait.rue}</p>
        <p> ${enchere.article.retrait.codePostal} ${enchere.article.retrait.ville}</p>
        <p>Vendeur :</p><p>${enchere.article.utilisateur.pseudo}</p>
        <div>
            <form method ="post" action="detailEnchere">
                <label for="montantEnchere">Ma Proposition : </label>
                <input type="number" id ="montantEnchere" name="montantEnchere"
                       min =<c:choose>
                               <c:when test="${enchere.montantEnchere > 0}">"${enchere.montantEnchere +1}" value="${enchere.montantEnchere+1}" </c:when>
                                <c:otherwise>"${enchere.article.prixInitial}" value="${enchere.article.prixInitial}"</c:otherwise>
                            </c:choose>
                />
                <input type="hidden" value="${enchere.article.id}" name="idArticle"/>
                <input type="hidden" value="${utilisateur.id}" name="idUtilisateur"/>
                <input type="submit" value="Enchérir" />
            </form>
            <p class="message-erreur">${message}</p>
            <p class="message-succes">${message_succes}</p>
        </div>

    </article>
</main>

<%@ include file="../Pages/footer.jsp" %>
</body>
</html>
