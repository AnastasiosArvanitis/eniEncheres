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
    <input type="text" id="pseudo" name="pseudo" required="required" maxlength="30"/>
    <BR/><BR/>
    <label for="nom">Nom : </label>
    <input type="text" id="nom" name="nom" required="required" maxlength="30"/>
    <BR/><BR/>
    <label for="prenom">Prénom :</label>
    <input type="text" id="prenom" name="prenom" required="required" maxlength="30"/>
    <BR/><BR/>
    <label for="email">Email :</label>
    <input type="email" id="email" name="email" required="required" maxlength="30"/>
    <BR/><BR/>
    <label for="telephone">Teléphone :</label>
    <input type="tel" id="telephone" name="telephone" maxlength="10"/>
    <BR/><BR/>
    <label for="rue">Rue :</label>
    <input type="text" id="rue" name="rue" required="required" maxlength="30"/>
    <BR/><BR/>
    <label for="codePostal">Code Postal :</label>
    <input type="text" id="codePostal" name="codePostal" required="required" maxlength="5"/>
    <BR/><BR/>
    <label for="ville">Ville :</label>
    <input type="text" id="ville" name="ville" required="required" maxlength="30"/>
    <BR/><BR/>
    <label for="motDePasse">Mot de passe :</label>
    <input type="password" id="motDePasse" name="motDePasse" required="required" maxlength="30"/>
    <BR/><BR/>
    <label for="passwordConfirm">Confirmation :</label>
    <input type="password" id="passwordConfirm" name="passwordConfirm" required="required" maxlength="30"/>
    <BR/><BR/>
    <input type="submit"  id="submit" value="Créer"/><a href="accueil"><input type="button" value="Annuler" /></a>



</form>

</body>
</html>
