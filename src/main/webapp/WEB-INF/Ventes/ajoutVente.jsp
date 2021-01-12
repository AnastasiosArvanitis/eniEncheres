<%@ page import="fr.eni.eniEncheres.bo.Categorie" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: vincdev
  Date: 06/01/2021
  Time: 21:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <h2>Nouvelle vente</h2>
    <article>
        <img src="#" alt="">
    </article>
    <article>

        <p>${action}</p>
        <p>${article}</p>

        <%--<form action="/encheres/ajout_vente" method="post">--%>
            <form action="/encheres/ajout_vente" method="post">
            <p>
                <label for="article">Article : </label>
                <input type="text" id="article" name="articleName" value="${article.nom}">
            </p>
            <p>
                <label for="description">Description :</label>
                <textarea name="description" id="description" col="10" rows="5">${article.description}</textarea>
            </p>
            <p>
                <label for="categorie">Categorie :</label>
                <select name="categorie" id="categorie" value="${article.categorie.libelle}">
                    <option value="null" selected>Choix</option>
                    <% List<Categorie> listCategorie = (List<Categorie>) request.getAttribute("listeCategorie");
                        for (Categorie ca : listCategorie) { %>
                    <option value="<%=ca.getLibelle() %>"><%=ca.getLibelle() %>
                    </option>
                    <% } %>
                </select>
            </p>
            <p>
                <label for="fichier">Photo de l'article : </label>
                <input type="file" name="fichier" id="fichier">
            </p>
            <p>
                <label for="number">Mise à prix :</label>
                <input type="number" id="number" name="prixInitial" value="${article.prixInitial}">
            </p>
            <p>
                <label for="dateDebutEnchere">Début de l'enchère :</label>
                <input type="date" name="dateDebutEnchere" id="dateDebutEnchere">
                <input type="time" name="heureDebutEnchere" id="heureDebutEnchere">
            </p>
            <p>
                <label for="dateFinEnchere">Fin de l'enchère :</label>
                <input type="date" name="dateFinEnchere" id="dateFinEnchere">
                <input type="time" name="heureFinEnchere" id="heureFinEnchere">
            </p>
            <script type="application/javascript">
                var dateDebutEnchere = document.getElementById("dateDebutEnchere");
                var heureDebutEnchere = document.getElementById("heureDebutEnchere");
                var dateFinEnchere = document.getElementById("dateFinEnchere");
                var heureFinEnchere = document.getElementById("heureFinEnchere");
                var heureDateDebutEnchere = dateDebutEnchere.toString();
                function dates(event) {
                    event.preventDefault();
                    console.log("dateDebutEnchere: "+dateDebutEnchere.value);
                    console.log("heureDebutEnchere: "+ heureDebutEnchere.value);
                    console.log("dateFinEnchere: "+ dateFinEnchere.value);
                    console.log("heureFinEnchere: " + heureFinEnchere.value);
                    return true;
                }
            </script>

            <fieldset class="form-retrait">
                <legend>Retrait</legend>
                <p>
                    <label for="rue">Rue :</label>
                    <input type="text" id="rue" name="rue"
                    <c:choose>
                    <c:when test="${!empty article.retrait.rue}">
                           value="${article.retrait.rue}">
                    </c:when>
                    <c:otherwise>
                        value="${utilisateur.rue}">
                    </c:otherwise>
                    </c:choose>
                </p>
                <p>
                    <label for="codePostal">Code Postal :</label>
                    <input type="text" id="codePostal" name="codePostal"
                    <c:choose>
                    <c:when test="${!empty article.retrait.codePostal}">
                           value="${article.retrait.codePostal}">
                    </c:when>
                    <c:otherwise>
                        value="${utilisateur.codePostal}">
                    </c:otherwise>
                    </c:choose>
                </p>
                <p>
                    <label for="ville">Ville :</label>
                    <input type="text" id="ville" name="ville"
                    <c:choose>
                    <c:when test="${!empty article.retrait.ville}">
                           value="${article.retrait.ville}">
                    </c:when>
                    <c:otherwise>
                        value="${utilisateur.ville}">
                    </c:otherwise>
                    </c:choose>
                </p>
            </fieldset>

            <input id="submitVente" type="submit" value="Enregistrer" />
            <input type="button" value="Annuler" onclick="window.location.href='<%=request.getContextPath()%>/';" />
        </form>
        <p class="message-erreur">${message}</p>
    </article>
</main>

<%@ include file="../Pages/footer.jsp" %>
</body>
</html>
