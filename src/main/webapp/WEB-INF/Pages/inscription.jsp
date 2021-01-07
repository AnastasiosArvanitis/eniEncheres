<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Formulaire d'Inscription</title>
    <style>
        <%@ include file="../../css/_global.css"%>

    </style>
</head>
<body>
<%@ include file="./header.jsp"%>
<main>
    <div class="inscription-container">
        <h3>Créer un compte</h3>
        <BR/>

        <form action="inscription" method="post">

            <label for="pseudo">Pseudo : </label>
            <input type="text" id="pseudo" name="pseudo" required="required" maxlength="30"
                   value="${ajoutUtilisateur.pseudo}"/>
            <BR/>
            <label for="nom">Nom : </label>
            <input type="text" id="nom" name="nom" required="required" maxlength="30" value="${ajoutUtilisateur.nom}"/>
            <BR/>
            <label for="prenom">Prénom :</label>
            <input type="text" id="prenom" name="prenom" required="required" maxlength="30"
                   value="${ajoutUtilisateur.prenom}"/>
            <BR/>
            <label for="email">Email :</label>
            <input type="email" id="email" name="email" required="required" maxlength="30"
                   value="${ajoutUtilisateur.email}"/>
            <BR/>
            <label for="telephone">Teléphone :</label>
            <input type="tel" id="telephone" name="telephone" maxlength="10" value="${ajoutUtilisateur.telephone}"/>
            <BR/>
            <label for="rue">Rue :</label>
            <input type="text" id="rue" name="rue" required="required" maxlength="30" value="${ajoutUtilisateur.rue}"/>
            <BR/>
            <label for="codePostal">Code Postal :</label>
            <input type="text" id="codePostal" name="codePostal" required="required" maxlength="5"
                   value="${ajoutUtilisateur.codePostal}"/>
            <BR/>
            <label for="ville">Ville :</label>
            <input type="text" id="ville" name="ville" required="required" maxlength="30"
                   value="${ajoutUtilisateur.ville}"/>
            <BR/>
            <label for="motDePasse">Mot de passe :</label>
            <input type="password" id="motDePasse" name="motDePasse" required="required" maxlength="30"/>
            <BR/>
            <label for="mdpConfirm">Confirmation :</label>
            <input type="password" id="mdpConfirm" name="mdpConfirm" required="required" maxlength="30"/>
            <BR/>
            <BR/>
            <input type="submit" id="submit" value="Creer"/><a href="accueil"><input type="button" value="Annuler"
                                                                                     onclick="window.location.href='<%=request.getContextPath()%>"/></a>
        </form>

        <p class="message-erreur">${Erreur}</p>
    </div>
</main>
<%@ include file="./footer.jsp"%>
</body>
</html>
