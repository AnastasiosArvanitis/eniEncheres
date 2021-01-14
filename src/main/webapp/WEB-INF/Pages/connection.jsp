<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Connection</title>
    <style>
        <%@ include file="../../css/_global.css"%>
        <%@ include file="../../css/connection.css"%>
    </style>
    <%--<link rel="stylesheet" type="text/css" href="./css/_global.css">--%>
    <script src="https://kit.fontawesome.com/0cf40fbd40.js" crossorigin="anonymous"></script>
</head>

<body>
<%@ include file="./header.jsp"%>
<main>
    <div class="connection-container">
        <h3>Connexion Utilisateur</h3>
        <form method="post" action="<%=request.getContextPath()%>/connection" class="connection-form">
            <p><label for="connection-identifiant">Email ou pseudo:</label></p>
            <p><input id="connection-identifiant" type="text" name="connection-identifiant"/></p>
            <p><label for="connection-password">Mot de passe:</label></p>
            <p><input id="connection-password" type="password" name="password-identifiant"/></p>
            <p><input id="connection-submit" type="submit" value="submit"/></p>
            <p></p>
            <p>
                <label for="connection-remember">Se souvenir de moi:
                    <input id="connection-remember" type="checkbox" name="connection-remeber" value="connection-remeber"></label>
            </p>
            <p></p>
            <p><a href="#">Mot de pass oublié</a></p>
            <p></p>
            <p><a href="<%=request.getContextPath()%>/inscription">Créer un compte</a></p>
        </form>
        <p class="message-erreur">${message}</p>
    </div>
</main>

<div class="seperator"></div>

<%@ include file="./footer.jsp"%>
</body>
</html>
