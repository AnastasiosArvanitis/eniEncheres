<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Formulaire d'Inscription</title>
    <style>
        <%@ include file="../../css/_global.css"%>
        <%@ include file="../../css/inscription.css"%>
    </style>
    <script src="https://kit.fontawesome.com/0cf40fbd40.js" crossorigin="anonymous"></script>
</head>
<body>
<%@ include file="./header.jsp"%>
<main>
    <div class="inscription-container">
        <h3 style="text-align: center">Créer un compte</h3>

        <form action="inscription" method="post">
            <div class="div-form">

                    <p><label for="motDePasse">Mot de passe :</label>
                        <input type="password" id="motDePasse" name="motDePasse" required="required" maxlength="30"/></p>

                    <p><label for="mdpConfirm">Confirmation Mot de passe :</label>
                    <input type="password" id="mdpConfirm" name="mdpConfirm" required="required" maxlength="30"/></p>
                </div>
            </div>

            <p class="input-inscription"><input type="submit" id="submit" value="Creer"/><a href="accueil"><input type="button" value="Retour sur la page d'Accueil"
                                                                                     onclick="window.location.href='<%=request.getContextPath()%>"/></a></p>
        </form>

        <p class="message-erreur">${Erreur}</p>
    </div>
</main>
<%@ include file="./footer.jsp"%>
</body>
</html>
