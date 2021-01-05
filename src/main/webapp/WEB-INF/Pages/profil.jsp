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
            <p>Prenom : <%=request.getAttribute("prenom") %></p>
            <p>Email : <%=request.getAttribute("email") %></p>
            <p>Téléphone : <%=request.getAttribute("telephone") %></p>
            <p>Rue : <%=request.getAttribute("rue") %></p>
            <p>Code Postal : <%=request.getAttribute("codePostal") %></p>
            <p>Ville : <%=request.getAttribute("ville") %></p>
            <p style="display: none"><%=request.getAttribute("motDePasse") %></p>
        </article>
        <a href="<%=request.getContextPath()%>/update_profile">Modifier</a>
        <a href="#">Supprimer</a>
        <p style="color: red"><%=request.getAttribute("message")%></p>
    </section>

<jsp:include page="footer.jsp" />
</body>
</html>
