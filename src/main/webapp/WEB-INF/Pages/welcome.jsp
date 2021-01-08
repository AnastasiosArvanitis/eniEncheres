<%@ page import="fr.eni.eniEncheres.bo.Enchere" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.eni.eniEncheres.bo.Categorie" %>
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
        <%
            if ((utilisateur != null) && (utilisateur.getCompteActif())) {
        %>
            <form method="post" action="">
            <div>
                <p><label for="search-article">Filtres:</label></p>
                <p><input id="search-article" name="search-article" value="le nom de l'article contient..." type="text"></p>
                <p>
                    <label for="search-categorie">Categorie:
                        <select name="search-categorie" id="search-categorie">
                            <option value="null" selected>Choix</option>
                            <% List<Categorie> listCategorie = (List<Categorie>) request.getAttribute("listCategorie");
                                for(Categorie ca : listCategorie){ %>
                            <option  value="<%=ca.getLibelle() %>"><%=ca.getLibelle() %></option>
                            <%	}	%>
                        </select>
                    </label>
                </p>
                <div>
                    <p>
                        <input type="radio" id="achat" name="check" value="" checked>
                        <label for="achat">Achats</label><br>

                        <input type="checkbox" id="enchereOuvert" name="check" value="" class="checkbox" checked>
                        <label for="enchereOuvert">enchere ouverte</label><br>

                        <input type="checkbox" id="enchereEnCours" name="check" value="" class="checkbox">
                        <label for="enchereEnCours">mes enchere en cours</label><br>

                        <input type="checkbox" id="enchereRemporte" name="check" value="" class="checkbox">
                        <label for="enchereRemporte">mes encheres remportes</label>
                    </p>
                    <p>
                        <input type="radio" id="vente" name="check" value="">
                        <label for="vente">Mes ventes</label><br>

                        <input type="checkbox" id="venteEnCours" name="check" value="" class="checkbox">
                        <label for="venteEnCours">mes ventes en cours</label><br>

                        <input type="checkbox" id="venteNonDebute" name="check" value="" class="checkbox">
                        <label for="venteNonDebute">ventes non debutes</label><br>

                        <input type="checkbox" id="venteTermine" name="check" value="" class="checkbox">
                        <label for="venteTermine">ventes termines</label>
                    </p></div>
            </div>
            <div class="recherche">
                <input type="submit" value="Rechercher">
            </div>

        </form>
            <div class="list-encheres">
            <c:forEach items="${enchereListe}" var="enchere" >
                <c:set var = "date" value = "${enchere.article.dateFinEncheres}" />
                <article class="enchere-article">
                    <div >
                        <img src="#" alt="">
                    </div>
                    <div class="article-content">
                        <h3><a href="<%=request.getContextPath()%>/view_enchere?idArticle=${enchere.article.id}">${enchere.article.nom}</a></h3>
                        <p>Prix : ${enchere.article.prixInitial} points</p>
                        <p>Fin de l'enchere : <fmt:formatDate dateStyle = "long" timeStyle = "long" type = "date" value = "${date}" /> </p>
                        <p>Vendeur : <a href="<%=request.getContextPath()%>/view_vendeur?idVendeur=${enchere.article.utilisateur.id}">${enchere.article.utilisateur.pseudo}</a></p>
                    </div>
                </article>
            </c:forEach>
        </div>
        <%
            } else {
        %>
            <form method="post" action="">
            <div>
                <p><label for="search-article">Filtres:</label></p>
                <p><input id="search-article" name="search-article" value="le nom de l'article contient..." type="text"></p>
                <p>
                    <label for="search-categorie">Categorie:
                        <select name="search-categorie" id="search-categorie">
                            <option value="null" selected>Choix</option>
                            <% List<Categorie> listCategorie = (List<Categorie>) request.getAttribute("listCategorie");
                                for(Categorie ca : listCategorie){ %>
                            <option  value="<%=ca.getLibelle() %>"><%=ca.getLibelle() %></option>
                            <%	}	%>
                        </select>
                    </label>
                </p>
            </div>
            <div class="rechercheOne">
               <input type="submit" value="Rechercher">
            </div>
        </form>
            <div class="list-encheres">
            <c:forEach items="${enchereListe}" var="enchere" >
                <c:set var = "date" value = "${enchere.article.dateFinEncheres}" />
                <article class="enchere-article">
                    <div >
                        <img src="#" alt="">
                    </div>
                    <div class="article-content">
                        <h3><a href="<%=request.getContextPath()%>/view_enchere?idArticle=${enchere.article.id}">${enchere.article.nom}</a></h3>
                        <p>Prix : ${enchere.article.prixInitial} points</p>
                        <p>Fin de l'enchere : <fmt:formatDate dateStyle = "long" timeStyle = "long" type = "date" value = "${date}" /> </p>
                        <p>Vendeur : ${enchere.article.utilisateur.pseudo}</p>
                    </div>
                </article>
            </c:forEach>
        </div>
        <%
            }
        %>
    </div>
</main>
<%@ include file="./footer.jsp"%>
</body>
</html>
