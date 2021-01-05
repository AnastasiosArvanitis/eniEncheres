<%@ page import="fr.eni.eniEncheres.bo.Utilisateur" %><%--
  Created by IntelliJ IDEA.
  User: vincdev
  Date: 05/01/2021
  Time: 14:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="header.jsp" />
<%
    Utilisateur utilisateur =null;
    if (session.getAttribute("utilisateur") == null) {
        response.sendRedirect("/encheres/error?error=userNotExist");
    } else {
        utilisateur = (Utilisateur) session.getAttribute("utilisateur");
    }
%>
    <section id="monProfil">
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
    </section>

<jsp:include page="footer.jsp" />
</body>
</html>
