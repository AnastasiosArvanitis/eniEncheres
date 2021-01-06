<%@ page import="fr.eni.eniEncheres.bo.Utilisateur" %><%--
  Created by IntelliJ IDEA.
  User: vincdev
  Date: 05/01/2021
  Time: 18:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ENI_Encheres</title>
    <style>
        <%@ include file="../../css/_global.css"%>
    </style>
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
    <main>
        <h2>Modifier Profil</h2>
        <article>
            <form method="post" action="<%=request.getContextPath()%>/update_profile">
                <input type="hidden" name="id" value="<%=utilisateur.getId() %>">

               <p><label for="pseudo">Pseudo :</label>
                <input type="text" name="pseudo" id="pseudo" value="<%=utilisateur.getPseudo() %>" required></p>

                <p><label for="nom">Nom :</label>
                <input type="text" name="nom" id="nom" value="<%=utilisateur.getNom() %>" required></p>

                <p><label for="prenom">Prenom :</label>
                <input type="text" name="prenom" id="prenom" value="<%=utilisateur.getPrenom() %>" required></p>

                <p><label for="email">Email :</label>
                <input type="text" name="email" id="email" value="<%=utilisateur.getEmail() %>" required></p>

                <p><label for="telephone">Telephone :</label>
                <input type="text" name="telephone" id="telephone" value="<%=utilisateur.getTelephone() %>"></p>

               <p><label for="rue">Rue :</label>
                <input type="text" name="rue" id="rue" value="<%=utilisateur.getRue()%>" required></p>

               <p><label for="codePostal">Code Postal :</label>
                <input type="text" name="codePostal" id="codePostal" value="<%=utilisateur.getCodePostal() %>" required></p>

               <p> <label for="ville">Ville :</label>
                <input type="text" name="ville" id="ville" value="<%=utilisateur.getVille() %>" required></p>

               <p><label for="motDePasse">Mot de Passe :</label>
                <input type="text" name="motDePasse" id="motDePasse" value="<%=utilisateur.getMotDePasse() %>" required></p>

               <p> <label for="confirmeMotDePasse">Confirmer Mot de passe :</label>
                <input type="text" name="confirmeMotDePasse" id="confirmeMotDePasse" value="<%=utilisateur.getMotDePasse() %>" required> </p>

                <input type="submit" value="Modifier">
                <input type="button" value="Annuler" onclick="window.location.href='<%=request.getContextPath()%>/profile';" />
            </form>
            <p style="color: red"><%String messageErrorUpdateProfil = (String) request.getAttribute("message");
            if (messageErrorUpdateProfil != null){
                out.println(messageErrorUpdateProfil);
            }else{
                out.println("");
            }
            %></p>
        </article>
    </main>

<jsp:include page="footer.jsp" />
</body>
</html>
