<%@ page import="fr.eni.eniEncheres.bo.Categorie" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: vincdev
  Date: 06/01/2021
  Time: 21:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <form action="" method="post">
            <p>
                <label for="article">Article : </label>
                <input type="text" id="article" name="article">
            </p>
            <p>
                <label for="description">Description :</label>
                <textarea name="description" id="description" col="10" rows="5"></textarea>
            </p>
            <p>
                <label for="categorie">Categorie :</label>
                <select name="categorie" id="categorie">
                    <option value="null" selected>Choix</option>
                    <% List<Categorie> listCategorie = (List<Categorie>) request.getAttribute("listeCategorie");
                        for(Categorie ca : listCategorie){ %>
                    <option  value="<%=ca.getId() %>"><%=ca.getLibelle() %></option>
                    <%	}	%>
                </select>
            </p>
            <p>
                <label for="fichier">Photo de l'article : </label>
                <input type="file" name="fichier" id="fichier">
            </p>
            <p>
                <label for="number">Mise à prix :</label>
                <input type="number" id="number" name="number" value="0">
            </p>
            <p>
                <label for="debutEnchere">Début de l'enchère :</label>
                <input type="date" name="debutEnchere" id="debutEnchere">
            </p>
            <p>
                <label for="finEnchere">Fin de l'enchère :</label>
                <input type="date" name="finEnchere" id="finEnchere">
            </p>

                <fieldset class="form-retrait">
                    <legend>Retrait</legend>
                <p>
                    <label for="rue">Rue :</label>
                    <input type="text" id="rue" name="rue" value="${utilisateur.rue}">
                </p>
                <p>
                    <label for="codePostal">Code Postal :</label>
                    <input type="text" id="codePostal" name="codePostal" value="${utilisateur.codePostal}">
                </p>
                <p>
                    <label for="ville">Ville :</label>
                    <input type="text" id="ville" name="ville" value="${utilisateur.ville}">
                </p>
                </fieldset>

            <input type="submit" value="Enregistrer">
            <input type="button" value="Annuler" onclick="window.location.href='<%=request.getContextPath()%>/';">
        </form>
        <p class="message-erreur">${message}</p>
    </article>
</main>

<%@ include file="../Pages/footer.jsp" %>
</body>
</html>
