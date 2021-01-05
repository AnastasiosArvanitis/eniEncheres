<%--
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
    <section id="monProfil">
        <h2>Mon Profil</h2>
        <article class="monProfil">

            <p>Pseudo : <%=request.getAttribute("pseudo") %> </p>
            <p>Nom : <%=request.getAttribute("nom") %></p>

            <p>Prenom : </p>
            <p>Email : </p>
            <p>Téléphone : </p>
            <p>Rue : </p>
            <p>Code Postal : </p>
            <p>Ville : </p>
        </article>
        <a href="#">Modifier</a>
        <a href="#">Supprimer</a>
    </section>

<jsp:include page="footer.jsp" />
</body>
</html>
