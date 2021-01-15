<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Modification de votre acces</title>
    <style>
        <%@ include file="../../css/_global.css"%>
        <%@ include file="../../css/connection.css"%>
    </style>
    <script src="https://kit.fontawesome.com/0cf40fbd40.js" crossorigin="anonymous"></script>
</head>
<body>
<%@ include file="./header.jsp"%>
<main>
    <div class="inscription-container">
        <h3 style="text-align: center">Modification mot de passe</h3>

        <form action="RecuperationMdp" method="post">
            <div class="div-form">
<BR/>
                <p><label for="motDePasse">Mot de passe :</label>
                <input type="password" id="motDePasse" name="motDePasse" required="required" maxlength="30"/></p>
                <div/>
                <div class="div-form">
<BR/>
                <p><label for="mdpConfirm">Confirmation Mot de passe :</label>
                    <input type="password" id="mdpConfirm" name="mdpConfirm" required="required" maxlength="30"/></p>
                    <input type="hidden" id="cle" name="cle" value="${cle}"/></p>
                </div>
            </div>

            <BR/>

            <p class="input-inscription"><input type="submit" id="submit" value="Modifier le mot de passe"/><a href="accueil">  <input type="button" value="Retour sur la page d'Accueil"
                                                                                     onclick="window.location.href='<%=request.getContextPath()%>"/></a></p>
        </form>

        <p class="message-erreur">${erreur}</p>
    </div>
</main>
<%@ include file="./footer.jsp"%>
</body>
</html>
