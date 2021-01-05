<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
    <style>
        <%@ include file="../../css/_global.css"%>
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
        <p>La on va mettre les encheres en cours...</p>
    </div>
</main>

<%@ include file="./footer.jsp"%>
</body>
</html>
