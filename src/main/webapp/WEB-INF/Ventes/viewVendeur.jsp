<%--
  Created by IntelliJ IDEA.
  User: vincdev
  Date: 07/01/2021
  Time: 09:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ENI_Enchere</title>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=East+Sea+Dokdo&family=Open+Sans:wght@300;600&family=Orbitron:wght@500&display=swap" rel="stylesheet">
    <style>
        <%@ include file="../../css/_global.css" %>
        <%@ include file="../../css/profil.css" %>
    </style>
    <script src="https://kit.fontawesome.com/0cf40fbd40.js" crossorigin="anonymous"></script>
</head>
<body>
<%@ include file="../Pages/header.jsp" %>
<%
    Utilisateur vendeur = (Utilisateur) request.getAttribute("vendeur");
%>
    <main>
        <h2>Profil du vendeur</h2>
        <article>
            <p><span>Pseudo : </span><span> ${vendeur.pseudo}</span></p>
            <p><span>Nom :</span><span>${vendeur.nom}</span></p>
            <p><span>Prenom :</span><span>${vendeur.prenom}</span></p>
            <p><span>Email :</span><span>${vendeur.email}</span></p>
            <p><span>Téléphone :</span><span>${vendeur.telephone}<span></p>
            <p><span>Rue :</span><span>${vendeur.rue}<span/></p>
            <p><span>Code Postal :</span><span>${vendeur.codePostal}<span/></p>
            <p><span>Ville :</span><span>${vendeur.ville}</span></p>
        </article>
        <article style="text-align: left; width: 100%">
            <a href="<%=request.getContextPath()%>/">Retour</a>
        </article>
    </main>
<div class="seperator"></div>
<%@ include file="../Pages/footer.jsp" %>
</body>
</html>
