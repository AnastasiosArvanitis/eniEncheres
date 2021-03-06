<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Connection</title>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=East+Sea+Dokdo&family=Open+Sans:wght@300;600&family=Orbitron:wght@500&display=swap" rel="stylesheet">
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
            <p><input id="connection-identifiant" type="text" name="connection-identifiant" /></p>
            <p><label for="connection-password">Mot de passe:</label></p>
            <p><input id="connection-password" type="password" name="password-identifiant"/></p>
            <p><input id="connection-submit" type="submit" value="submit"/></p>
            <p></p>
            <p>
                <label for="connection-remember">Se souvenir de moi:
                    <input id="connection-remember" type="checkbox" name="connection-remeber" value="connection-remeber"></label>
            </p>
            <p></p>
            <p><a id="recupMdp" href="<%=request.getContextPath()%>/RecuperationMdp" onclick="envoiRecupMdp(event)">Mot de passe oublié</a></p>
            <p></p>
            <p><a href="<%=request.getContextPath()%>/inscription">Créer un compte</a></p>
        </form>

    </div>
    <p class="message-erreur">${message}</p>
    <p class="message-succes">${success}</p>
</main>



<%@ include file="./footer.jsp"%>
</body>
<script type="application/javascript">

    function envoiRecupMdp(event){
        //event.preventDefault() ; pour ne pas envoyer le formulaire
        var recupMdp = document.getElementById("recupMdp");

        var lien = "<%=request.getContextPath()%>/RecuperationMdp?connectionIdentifiant="+document.getElementById("connection-identifiant").value;

        recupMdp.setAttribute("href", lien);

    }


</script>
</html>
