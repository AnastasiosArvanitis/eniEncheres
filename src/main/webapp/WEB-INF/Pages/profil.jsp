<%@ page import="fr.eni.eniEncheres.bo.Utilisateur" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
    <style>
        <%@ include file="../../css/_global.css"%>
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

            <p>Pseudo : <%=utilisateur.getPseudo() %> </p>
            <p>Nom : <%=utilisateur.getNom() %></p>
            <p>Prenom : <%=utilisateur.getPrenom() %></p>
            <p>Email : <%=utilisateur.getEmail() %></p>
            <p>Téléphone : <%=utilisateur.getTelephone() %></p>
            <p>Rue : <%=utilisateur.getRue() %></p>
            <p>Code Postal : <%=utilisateur.getCodePostal() %></p>
            <p>Ville : <%=utilisateur.getVille() %></p>
            <p style="display: none"><%=utilisateur.getMotDePasse() %></p>
        </article>
        <a href="<%=request.getContextPath()%>/update_profile">Modifier</a>
        <a href="#">Supprimer</a>
        <p style="color: red"><%=request.getAttribute("message")%></p>
    </main>

<jsp:include page="./footer.jsp" />
</body>
</html>
