<%@ page import="fr.eni.eniEncheres.bo.Utilisateur" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Profil</title>
    <style>
        <%@ include file="../../css/_global.css"%>
        <%@ include file="../../css/profil.css" %>
    </style>
    <%--<link rel="stylesheet" type="text/css" href="./css/_global.css">--%>
</head>
<body>
<jsp:include page="./header.jsp" />
<%
    Utilisateur utilisateur =null;
    if (session.getAttribute("utilisateur") == null) {
        response.sendRedirect("/encheres/error?error=userNotExist");
    } else {
        utilisateur = (Utilisateur) session.getAttribute("utilisateur");
    }
%>
    <main>
        <h2>Mon Profil</h2>
        <article class="monProfil">

            <p><span>Pseudo : </span><span> ${utilisateur.pseudo}</span></p>
            <p><span>Nom :</span><span>${utilisateur.nom}</span></p>
            <p><span>Prenom :</span><span>${utilisateur.prenom}</span></p>
            <p><span>Email :</span><span>${utilisateur.email}</span></p>
            <p><span>Téléphone :</span><span>${utilisateur.telephone}<span></p>
            <p><span>Rue :</span><span>${utilisateur.rue}<span/></p>
            <p><span>Code Postal :</span><span>${utilisateur.codePostal}<span/></p>
            <p><span>Ville :</span><span>${utilisateur.ville}</span></p>
            <p style="display: none">${utilisateur.motDePasse}</p>
            <p><span>Credit :</span><span>${utilisateur.credit}</span></p>
        </article>
        <p class="btn-update-profile">
            <a href="<%=request.getContextPath()%>/update_profile">Modifier</a>
        </p>

            <!-- MESSAGE EN CAS DE SUCCES LORS DE L UPDATE PROFIL -->
            <p style="color: green">${message}</p>
    </main>

<jsp:include page="./footer.jsp" />
</body>
</html>
