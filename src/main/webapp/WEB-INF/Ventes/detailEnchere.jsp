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
<c:set var="vendeur" scope="request" value="${enchere.article.utilisateur.id}"/>
<c:set var="acheteur" scope="request" value="${utilisateur.id}"/>
<c:set var="meilleureEnchereUtilisateurId" scope="request" value="${enchere.utilisateur.id}"/>
<c:set var="dateDebut" scope="request" value="${enchere.article.dateDebutEncheres}"/>
<c:set var="dateFin" scope="request" value="${enchere.article.dateFinEncheres}"/>
<c:set var="maintenant" scope="request" value="<%= new Date() %>"/>

<html>
<head>
    <title>ENI_Enchere</title>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=East+Sea+Dokdo&family=Open+Sans:wght@300;600&family=Orbitron:wght@500&display=swap" rel="stylesheet">
    <style>
        <%@ include file="../../css/_global.css" %>
        <%@ include file="../../css/ajoutVente.css" %>
    </style>
    <script src="https://kit.fontawesome.com/0cf40fbd40.js" crossorigin="anonymous"></script>
</head>
<body>
<%@ include file="../Pages/header.jsp" %>

<main>
    <c:choose>
        <%--Si l'utilisateur connecté est le vendeur et que l'enchère est remporté--%>
        <c:when test="${(utilisateur.id == vendeur) && ( dateFin < maintenant )&&( meilleureEnchereUtilisateurId > 0) }"><h2>${enchere.utilisateur.pseudo} a remporté l'enchère</h2></c:when>
        <%--Si l'utilisateur connecté est l'achetur et que la date de fin est passée--%>
        <c:when test="${(meilleureEnchereUtilisateurId == acheteur ) && ( dateFin < maintenant )}"><h2>Vous avez remporté la vente</h2></c:when>
        <%--Si l'utilisateur connecté est le vendeur et que l'enchère est KO--%>
        <c:when test="${(acheteur == vendeur ) && (dateDebut < maintenant) && (dateFin < maintenant) && (empty meilleureEnchereUtilisateurId)}"><h2>L'article n'a pas été vendu</h2></c:when>
        <%--Si l'utilisateur connecté n'est pas le vendeur et que la vente est terminée--%>
        <c:when test="${(acheteur != vendeur ) && (dateDebut < maintenant) && (dateFin < maintenant)}"><h2>La vente est terminée</h2></c:when>
        <c:otherwise><h2>Détail Vente</h2></c:otherwise>
    </c:choose>
<section>
    <article class="enchere-article">
        <div>
            <img src="#" alt="" >
        </div>

    </article>
    <article class="article-detail">
        <h3>${enchere.article.nom}</h3>
        <p>Description : ${enchere.article.description}</p>
        <p>Catégorie : ${enchere.article.categorie.libelle}</p>

        <c:choose>
            <c:when test="${enchere.montantEnchere > 0}"><p>Meilleur Offre : ${enchere.montantEnchere}  pts par ${enchere.utilisateur.pseudo}</p></c:when>
        </c:choose>
        <p>Mise a prix : ${enchere.article.prixInitial} pts</p>
        <p>Fin de l'enchère :  <fmt:formatDate pattern = "dd/MM/yyyy H:m" value = "${enchere.article.dateFinEncheres}" /></p>
        <p>Adresse de retrait : </p>
        <p> ${enchere.article.retrait.rue}</p>
        <p> ${enchere.article.retrait.codePostal} ${enchere.article.retrait.ville}</p>
        <p>Vendeur : ${enchere.article.utilisateur.pseudo}</p>


        <c:if test="${acheteur > 0}">

            <c:choose>

                <c:when test="${(acheteur == vendeur ) && (dateDebut > maintenant) &&(utilisateur.getCompteActif())}"><%@ include file="../Ventes/BlocsDetailsEncheres/modifierVente.jsp" %></c:when>

                <c:when test="${(acheteur == vendeur ) && (dateDebut < maintenant) && (dateFin > maintenant)&&(utilisateur.getCompteActif())  }"><p class="message-info">Vous ne pouvez plus modifier une vente en cours</p></c:when>

                <c:when test="${((acheteur != vendeur ) && (dateDebut < maintenant) && (dateFin > maintenant) && (utilisateur.getCompteActif()) )}"> <%@ include file="../Ventes/BlocsDetailsEncheres/encherir.jsp" %></c:when>

                <c:when test="${(utilisateur.id == vendeur) && ( dateFin < maintenant )&&( meilleureEnchereUtilisateurId > 0) &&(utilisateur.getCompteActif())}"><h2>Confirmer la réception de l'article ( boolean a rajouter table retrait avec boutton actif /inactif)</h2></c:when>

                <c:when test="${(!empty acheteur)&&(dateDebut < maintenant) && (dateFin > maintenant)&&(!utilisateur.getCompteActif())}"><p class="message-info">Votre accès est limité. Merci de contacter un administrateur</p></c:when>
            </c:choose>

        </c:if>
        <%--Si Non connecté et enchère en cours--%>

        <c:if test="${(empty acheteur)&&(dateDebut < maintenant) && (dateFin > maintenant)}">
            <p class="message-info">Vous devez etre connecté pour enchérir. <a href="/encheres/connection">Se connecter</a></p>
        </c:if>

    </article>

</section>
    <article style="text-align: left; width: 100%">
        <a  href="<%=request.getContextPath()%>/">Retour</a>
    </article>
</main>

<%@ include file="../Pages/footer.jsp" %>
</body>
</html>
