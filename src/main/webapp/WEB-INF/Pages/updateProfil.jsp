<%--
  Created by IntelliJ IDEA.
  User: vincdev
  Date: 05/01/2021
  Time: 18:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="header.jsp" />
    <section id="updateProfil">
        <article>
            <form method="post" action="${pageContext.request.contextPath}/update_profile">
                <input type="hidden" name="id" value="<%=request.getAttribute("id") %>">

                <label for="pseudo">Pseudo :</label>
                <input type="text" name="pseudo" id="pseudo" value="<%=request.getAttribute("pseudo") %>" required>

                <label for="nom">Nom :</label>
                <input type="text" name="nom" id="nom" value="<%=request.getAttribute("nom") %>" required>

                <label for="prenom">Prenom :</label>
                <input type="text" name="prenom" id="prenom" value="<%=request.getAttribute("prenom") %>" required>

                <label for="email">Email :</label>
                <input type="text" name="email" id="email" value="<%=request.getAttribute("email") %>" required>

                <label for="telephone">Telephone :</label>
                <input type="text" name="telephone" id="telephone" value="<%=request.getAttribute("telephone") %>">

                <label for="rue">Rue :</label>
                <input type="text" name="rue" id="rue" value="<%=request.getAttribute("rue") %>" required>

                <label for="codePostal">Code Postal :</label>
                <input type="text" name="codePostal" id="codePostal" value="<%=request.getAttribute("codePostal") %>" required>

                <label for="ville">Ville :</label>
                <input type="text" name="ville" id="ville" value="<%=request.getAttribute("ville") %>" required>

                <label for="motDePasse">Mot de Passe :</label>
                <input type="text" name="motDePasse" id="motDePasse" value="<%=request.getAttribute("motDePasse") %>" required>

                <label for="confirmeMotDePasse">Confirmer Mot de passe :</label>
                <input type="text" name="confirmeMotDePasse" id="confirmeMotDePasse" value="<%=request.getAttribute("motDePasse") %>" required>

                <input type="submit" value="Modifier">
                <input type="reset" value="Annuler">
            </form>
        </article>
    </section>

<jsp:include page="footer.jsp" />
</body>
</html>
