<%--
  Created by IntelliJ IDEA.
  User: Lolo formation
  Date: 04/01/2021
  Time: 21:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ENI-Enchères</title>
</head>
<body>
<h1>ENI-Enchères</h1>
<h3>Créer un compte</h3>
<form action="inscription" method="post">

    <label for="pseudo">Pseudo : </label>
    <input type="text" id="pseudo" name="pseudo" required="required" maxlength="30" value="${ajoutUtilisateur.pseudo}"/>
    <BR/><BR/>
    <label for="nom">Nom : </label>
    <input type="text" id="nom" name="nom" required="required" maxlength="30" value="${ajoutUtilisateur.nom}"/>
    <BR/><BR/>
    <label for="prenom">Prénom :</label>
    <input type="text" id="prenom" name="prenom" required="required" maxlength="30" value="${ajoutUtilisateur.prenom}"/>
    <BR/><BR/>
    <label for="email">Email :</label>
    <input type="email" id="email" name="email" required="required" maxlength="30" value="${ajoutUtilisateur.email}"/>
    <BR/><BR/>
    <label for="telephone">Teléphone :</label>
    <input type="tel" id="telephone" name="telephone" maxlength="10" value="${ajoutUtilisateur.telephone}"/>
    <BR/><BR/>
    <label for="rue">Rue :</label>
    <input type="text" id="rue" name="rue" required="required" maxlength="30" value="${ajoutUtilisateur.rue}"/>
    <BR/><BR/>
    <label for="codePostal">Code Postal :</label>
    <input type="text" id="codePostal" name="codePostal" required="required" maxlength="5" value="${ajoutUtilisateur.codePostal}"/>
    <BR/><BR/>
    <label for="ville">Ville :</label>
    <input type="text" id="ville" name="ville" required="required" maxlength="30" value="${ajoutUtilisateur.ville}"/>
    <BR/><BR/>
    <label for="motDePasse">Mot de passe :</label>
    <input type="password" id="motDePasse" name="motDePasse" required="required" maxlength="30" />
    <BR/><BR/>
    <label for="mdpConfirm">Confirmation :</label>
    <input type="password" id="mdpConfirm" name="mdpConfirm" required="required" maxlength="30" />
    <BR/><BR/>
    <input type="submit"  id="submit" value="Créer"/><a href="accueil"><input type="button" value="Annuler" /></a>



</form>

</body>
</html>
