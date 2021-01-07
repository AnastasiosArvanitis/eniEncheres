<%@ page import="fr.eni.eniEncheres.bo.Enchere" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Welcome</title>
    <style>
        <%@ include file="../../css/_global.css"%>
        <%@ include file="../../css/welcom.css"%>
    </style>
    <%--<link rel="stylesheet" type="text/css" href="./css/_global.css">--%>
</head>
<body>
<%@ include file="./header.jsp"%>
<main>
    <h1>Liste des encheres</h1>
    <div class="search-article-welcome">
        <form action="">
            <label for="search-article">Filtres:
                <input id="search-article" name="search-article" value="le nom de l'article contient..." type="text">
            </label>
            <label for="search-categorie">Categorie:
                <select name="search-categorie" id="search-categorie">
                    <option value="" selected disabled hidden>Toutes</option>
                    <option value="Informatique">Informatique</option>
                    <option value="Ameublement">Ameublement</option>
                    <option value="Vetement">Vetement</option>
                    <option value="Sport & Loisirs">Sport & Loisirs</option>
                </select>
            </label>
            <input type="submit" value="Rechercher">
        </form>
    </div>
    <div class="list-encheres">
        <c:forEach items="${enchereListe}" var="enchere" >
            <c:set var = "date" value = "${enchere.article.dateFinEncheres}" />
            <article class="enchere-article">
                <div >
                    <img src="#" alt="">
                </div>
                <div class="article-content">

                    <h3>${enchere.article.nom}</h3>
                    <p>Prix : ${enchere.article.prixInitial} points</p>
                    <p>Fin de l'enchere : <fmt:formatDate type = "date" value = "${date}" /> </p>
                    <p>Vendeur :
                        <a href="<%=request.getContextPath()%>/view_vendeur?idVendeur=${enchere.article.utilisateur.id}">${enchere.article.utilisateur.pseudo}</a>
                    </p>
                </div>
            </article>
        </c:forEach>

    </div>
</main>

<%@ include file="./footer.jsp"%>
</body>
</html>
