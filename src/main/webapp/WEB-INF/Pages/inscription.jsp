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
                <div>
                    <p><label for="pseudo">Pseudo : </label>
                        <input type="text" id="pseudo" name="pseudo" required="required" maxlength="30" value="${ajoutUtilisateur.pseudo}"/>
                    </p>
                    <p> <label for="nom">Nom : </label>
                        <input type="text" id="nom" name="nom" required="required" maxlength="30" value="${ajoutUtilisateur.nom}"/>
                    </p>
                    <p><label for="prenom">Prénom :</label>
                        <input type="text" id="prenom" name="prenom" required="required" maxlength="30" value="${ajoutUtilisateur.prenom}"/>
                    </p>
                    <p><label for="telephone">Teléphone :</label>
                        <input type="tel" id="telephone" name="telephone" maxlength="10" value="${ajoutUtilisateur.telephone}"/>
                    </p>
                    <p><label for="email">Email :</label>
                        <input type="email" id="email" name="email" required="required" maxlength="30" value="${ajoutUtilisateur.email}"/>
                    </p>
                </div>
                <div>
                    <p><label for="rue">Rue :</label>
                        <input type="text" id="rue" name="rue" required="required" maxlength="30" value="${ajoutUtilisateur.rue}"/>
                    </p>
                    <p><label for="codePostal">Code Postal :</label>
                        <input type="text" id="codePostal" name="codePostal" required="required" maxlength="5"  value="${ajoutUtilisateur.codePostal}"/>
                    </p>
                    <p><label for="ville">Ville :</label>
                    <input type="text" id="ville" name="ville" required="required" maxlength="30" value="${ajoutUtilisateur.ville}"/>
                    </p>
                    <p><label for="motDePasse">Mot de passe :</label>
                        <input type="password" id="motDePasse" name="motDePasse" required="required" maxlength="30"/></p>

                    <p><label for="mdpConfirm">Confirmation Mot de passe :</label>
                    <input type="password" id="mdpConfirm" name="mdpConfirm" required="required" maxlength="30"/></p>
                </div>
            </div>

            <p class="input-inscription"><input type="submit" id="submit" value="Creer"/><a href="accueil"><input type="button" value="Annuler"
                                                                                     onclick="window.location.href='<%=request.getContextPath()%>"/></a></p>
        </form>

        <p class="message-erreur">${Erreur}</p>
    </div>
</main>
<%@ include file="./footer.jsp"%>
</body>
</html>
