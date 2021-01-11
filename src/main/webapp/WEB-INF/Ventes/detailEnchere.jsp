<%@ page import="fr.eni.eniEncheres.bo.Categorie" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %><%--

  Created by IntelliJ IDEA.
  User: vincdev
  Date: 06/01/2021
  Time: 21:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<c:set var="vendeur" scope="session" value="${enchere.article.utilisateur.id}"/>
<c:set var="acheteur" scope="session" value="${utilisateur.id}"/>
<c:set var="meilleureEnchereUtilisateurId" scope="session" value="${enchere.utilisateur.id}"/>
<c:set var="dateDebut" scope="session" value="${enchere.article.dateDebutEncheres}"/>
<c:set var="dateFin" scope="session" value="${enchere.article.dateFinEncheres}"/>
<c:set var="maintenant" scope="session" value="<%= new Date() %>"/>

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

        <c:choose>
            <c:when test="${enchere.montantEnchere > 0}"><p>Meilleur Offre : </p><p>${enchere.montantEnchere}  pts par ${enchere.utilisateur.pseudo}</p></c:when>
            <c:otherwise ><p>Devenez le premier enchérisseur en faisant une proposition</p></c:otherwise>
        </c:choose>
        <p>Mise a prix : </p><p>${enchere.article.prixInitial} pts</p>
        <p>Fin de l'enchère :  </p><p>${enchere.article.dateFinEncheres}</p>
        <p>Adresse de retrait : </p><p> ${enchere.article.retrait.rue}</p>
        <p> ${enchere.article.retrait.codePostal} ${enchere.article.retrait.ville}</p>
        <p>Vendeur :</p><p>${enchere.article.utilisateur.pseudo}</p>

        <c:if test="${acheteur > 0}">

        <c:choose>

            <%--si Vendeur--%>

            <c:when test="${(acheteur == vendeur ) && (dateDebut > maintenant) }"><p>tu est le vendeur et la vente n'a pas commencé (lien modifier article et supprimer article)</p></c:when>

            <c:when test="${(acheteur == vendeur ) && (dateDebut < maintenant) && (dateFin < maintenant) && (meilleureEnchereUtilisateurId > 0)}"><p>tu est le vendeur et la vente est terminée</p></c:when>

            <c:when test="${(acheteur == vendeur ) && (dateDebut < maintenant) && (dateFin < maintenant) && (meilleureEnchereUtilisateurId = 0)}"><p>tu est le vendeur, malheureusement l'article n'a pas été vendu </p></c:when>

            <c:when test="${(acheteur == vendeur ) && (dateDebut < maintenant) && (dateFin > maintenant) }"><p>tu est le vendeur et la vente est en cours</p></c:when>

            <%--Si Acheteur--%>

            <c:when test="${(acheteur == meilleureEnchereUtilisateurId ) && (dateDebut < maintenant) && (dateFin < maintenant) }"><p>tu as remporté l'article</p></c:when>

            <c:when test="${((acheteur != vendeur ) && (dateDebut < maintenant) && (dateFin < maintenant))}"><p>La vente est terminée</p></c:when>

            <c:when test="${((acheteur != vendeur ) && (dateDebut < maintenant) && (dateFin > maintenant))}"> <%@ include file="../Ventes/BlocsDetailsEncheres/encherir.jsp" %></c:when>

        </c:choose>

        </c:if>
        <%--Si Non connecté et enchère en cours--%>

        <c:if test="${(empty acheteur)&&(dateDebut < maintenant) && (dateFin > maintenant)}">
            <p>Vous devez etre connecté pour enchérir</p>
        </c:if>









    </article>
</main>

<%@ include file="../Pages/footer.jsp" %>
</body>
</html>
