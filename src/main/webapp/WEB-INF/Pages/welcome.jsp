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
   <%-- <link rel="stylesheet" type="text/css" href="./css/_global.css">
    <link rel="stylesheet" type="text/css" href="./css/welcom.css">--%>
</head>
<body>
<%@ include file="./header.jsp"%>
<main>
    <h1>Liste des encheres</h1>
    <div class="search-article-welcome">
        <%
            if ((utilisateur != null) && (utilisateur.getCompteActif())) {
        %>
        <!-- FORMULAIRE MODE CONNEXION-->
            <form method="post" action="search_co">
            <div>
                <p><label for="search-name">Filtres:</label></p>
                <p><input id="search-name" name="search-name" placeholder="le nom de l'article contient..." type="text"></p>
                <p>
                    <label for="search-categorie">Categorie:
                        <select name="search-categorie" id="search-categorie">
                            <option value="0" selected>Choix</option>
                            <% List<Categorie> listCategorie = (List<Categorie>) request.getAttribute("listCategorie");
                                for(Categorie ca : listCategorie){ %>
                            <option  value="<%=ca.getId() %>"><%=ca.getLibelle() %></option>
                            <%	}	%>
                        </select>
                    </label>
                </p>
                <div>
                    <script type="application/javascript">
                        window.onload = function() {
                            var radioAchat = document.getElementById("achat");
                            var enchereOuvert = document.getElementById("enchereOuvert");
                            var enchereEnCours = document.getElementById("enchereEnCours");
                            var enchereRemporte = document.getElementById("enchereRemporte");

                            var radioVente = document.getElementById("vente");
                            var venteEnCours = document.getElementById("venteEnCours");
                            var venteNonDebute = document.getElementById("venteNonDebute");
                            var venteTermine = document.getElementById("venteTermine");

                            radioAchat.checked = true;
                            enchereOuvert.checked = true;

                            radioAchat.onclick = achatClickHandler;
                            radioVente.onclick = venteClickHandler;

                            function achatClickHandler() {

                                venteEnCours.disabled = true;
                                venteNonDebute.disabled = true;
                                venteTermine.disabled = true;

                                radioAchat.checked = true;
                                enchereOuvert.checked = true;
                                radioVente.checked = false;

                                enchereOuvert.disabled = false;
                                enchereEnCours.disabled = false;
                                enchereRemporte.disabled = false;
                            }
                            function venteClickHandler() {

                                enchereOuvert.disabled = true;
                                enchereEnCours.disabled = true;
                                enchereRemporte.disabled = true;

                                radioAchat.checked = false;
                                radioVente.checked = true;

                                venteEnCours.disabled = false;
                                venteNonDebute.disabled = false;
                                venteTermine.disabled = false;
                            }
                        }




                    </script>
                    <%--------------------------    RADIO ACHATS                  --------------------------%>
                    <p>
                        <input type="radio" id="achat" name="radio-achat" value="radioAchat">
                        <label for="achat">Achats</label><br>

                        <input type="checkbox" id="enchereOuvert" name="check-enchereOuvert" value="enchereOuvert" class="checkbox">
                        <label for="enchereOuvert">enchere ouverte</label><br>

                        <input type="checkbox" id="enchereEnCours" name="check-enchereEnCours" value="enchereEnCours" class="checkbox">
                        <label for="enchereEnCours">mes enchere en cours</label><br>

                        <input type="checkbox" id="enchereRemporte" name="check-enchereRemporte" value="enchereRemporte" class="checkbox">
                        <label for="enchereRemporte">mes encheres remportes</label>
                    </p>
                        <%--------------------------    RADIO VENTES                  --------------------------%>
                    <p>
                        <input type="radio" id="vente" name="radio-vente" value="radioVente">
                        <label for="vente">Mes ventes</label><br>

                        <input type="checkbox" id="venteEnCours" name="check-venteEnCours" value="venteEnCours" class="checkbox">
                        <label for="venteEnCours">mes ventes en cours</label><br>

                        <input type="checkbox" id="venteNonDebute" name="check-venteNonDebute" value="venteNonDebute" class="checkbox">
                        <label for="venteNonDebute">ventes non debutes</label><br>

                        <input type="checkbox" id="venteTermine" name="check-venteTermine" value="venteTermine" class="checkbox">
                        <label for="venteTermine">ventes termines</label>
                    </p></div>
            </div>
                <input type="hidden" name="idUtilisateur" value="${utilisateur.id}">
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
                        <h3><a href="<%=request.getContextPath()%>/detailEnchere?idArticle=${enchere.article.id}">${enchere.article.nom}</a></h3>
                        <p>Prix : ${enchere.article.prixVente} points</p>
                        <p>Fin de l'enchere : <fmt:formatDate dateStyle = "long" timeStyle = "long" type = "date" value = "${date}" /> </p>
                        <p>Vendeur : <a href="<%=request.getContextPath()%>/view_vendeur?idVendeur=${enchere.article.utilisateur.id}">${enchere.article.utilisateur.pseudo}</a></p>
                    </div>
                </article>
            </c:forEach>
        </div>
        <%
            } else {
        %>
        <!-- FORMULAIRE MODE DECONNEXION-->
            <form method="post" action="search">
            <div>
                <p><label for="search-name">Filtres:</label></p>
                <p><input id="search-name" name="search-name" placeholder="le nom de l'article contient..." type="text"></p>
                <p>
                    <label for="search-categorie">Categorie:
                        <select name="search-categorie" id="search-categorie">
                            <option value= "0" selected>Choix</option>
                            <% List<Categorie> listCategorie = (List<Categorie>) request.getAttribute("listCategorie");
                                for(Categorie ca : listCategorie){ %>
                            <option  value="<%=ca.getId() %>"><%=ca.getLibelle() %></option>
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
                        <h3><a href="<%=request.getContextPath()%>/detailEnchere?idArticle=${enchere.article.id}">${enchere.article.nom}</a></h3>
                        <p>Prix : ${enchere.article.prixVente} points</p>
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
