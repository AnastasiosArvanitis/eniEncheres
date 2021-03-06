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
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="vendeur" scope="request" value="${article.utilisateur.id}"/>
<c:set var="acheteur" scope="request" value="${utilisateur.id}"/>
<c:set var="meilleureEnchereUtilisateurId" scope="request" value="${utilisateur.id}"/>
<c:set var="dateDebut" scope="request" value="${article.dateDebutEncheres}"/>
<c:set var="dateFin" scope="request" value="${article.dateFinEncheres}"/>
<c:set var="retrait" scope="request" value="${article.retrait.id}"/>



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
    <h2>${action =="maj" && article.id > 0 ?"Modifier vente" : "Nouvelle vente"}</h2>
    <article>
        <img src="#" alt="">
    </article>
    <article>
        <%--<form action="/encheres/ajout_vente" method="post">--%>
            <form action="${action =="maj" && article.id > 0 ?"/encheres/ajout_vente" : "/encheres/ajout_vente" }" method="post" enctype='multipart/form-data' class="form-ajout-vente">
                <c:if test="${!empty action}">
                    <input type="hidden" id="idArticle" name="idArticle" value="${article.id}">
                    <input type="hidden" id="idRetrait" name="idRetrait" value="${article.retrait.id}">
                </c:if>

            <p>
                <label for="article">Article : </label>
                <input type="text" id="article" name="articleName" value="${article.nom}" required="required" />
            </p>
            <p>
                <label for="description">Description :</label>
                <textarea name="description" id="description" col="10" rows="5" required="required">${article.description}</textarea>
            </p>
            <p>
                <label for="categorie">Categorie :</label>
                <select name="categorie" id="categorie"  >${listCategorie}
                    <option   value="" >Choix</option>
                    <c:forEach items="${listeCategorie}" var="categorie">
                        <option value="${categorie.libelle}" ${categorie.libelle == article.categorie.libelle ? 'selected' : ''}>${categorie.libelle}</option>
                    </c:forEach>
                </select>
            </p>
            <p>
                <label for="fichier">Photo de l'article : </label>
                <input type="file" name="photo" id="fichier">
            </p>
            <p>
                <label for="number">Mise à prix :</label>
                <input type="number" id="number" name="prixInitial" value="${article.prixInitial}" min="1" required="required" >
            </p>
            <p>
                <label for="debutEnchere">Début de l'enchère :</label>
                <input type="date" name="dateDebutEnchere" id="debutEnchere" required="required"  value="<fmt:formatDate pattern = "yyyy-MM-dd" value = "${dateDebut}" />">
                <input type="time" name="heureDebutEnchere" id="heureDebutEnchere" required="required"  value="<fmt:formatDate pattern = "HH:mm" value = "${dateDebut}" />">
            </p>
            <p>
                <label for="finEnchere">Fin de l'enchère :</label>
                <input type="date" name="dateFinEnchere" id="finEnchere" required="required" value="<fmt:formatDate pattern = "yyyy-MM-dd" value = "${dateFin}" />">
                <input type="time" name="heureFinEnchere" id="heureFinEnchere" required="required"  value="<fmt:formatDate pattern = "HH:mm" value = "${dateFin}" />">
            </p>


            <fieldset class="form-retrait">
                <legend>Retrait</legend>
                <p>
                    <label for="rue">Rue :</label>
                    <input required="required" type="text" id="rue" name="rue"
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
                    <input required="required" type="text" id="codePostal" name="codePostal"
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
                    <input required="required" type="text" id="ville" name="ville"
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
        <c:choose>
            <c:when test="${action ==\"maj\" && article.id > 0}">
                <input id="submitVente" name="actionBouton" type="submit"  value="modifier"  />
                <input id="submitVente" name="actionBouton" type="submit"  value="supprimer" />
                <input type="button" value="Annuler" onclick="window.location.href='<%=request.getContextPath()%>/';" />
            </c:when>
            <c:otherwise>
                <input id="submitVente" type="submit" value="Enregistrer" />
                <input type="button" value="Annuler" onclick="window.location.href='<%=request.getContextPath()%>/';" />
            </c:otherwise>
        </c:choose>
        </form>
        <p class="message-erreur">${message}</p>
    </article>
</main>

<%@ include file="../Pages/footer.jsp" %>
</body>
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
</html>
