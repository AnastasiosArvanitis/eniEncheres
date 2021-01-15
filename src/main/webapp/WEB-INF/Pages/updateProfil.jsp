<%@ page import="fr.eni.eniEncheres.bo.Utilisateur" %><%--
  Created by IntelliJ IDEA.
  User: vincdev
  Date: 05/01/2021
  Time: 18:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ENI_Encheres</title>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=East+Sea+Dokdo&family=Open+Sans:wght@300;600&display=swap" rel="stylesheet">
    <style>
        <%@ include file="../../css/_global.css"%>
        <%@ include file="../../css/profil.css" %>

    </style>
    <script src="https://kit.fontawesome.com/0cf40fbd40.js" crossorigin="anonymous"></script>
</head>
<body>
<jsp:include page="header.jsp" />

    <main>
        <h2>Modifier Profil</h2>
        <article>
            <form method="post" action="<%=request.getContextPath()%>/update_profile">
                <input type="hidden" name="id" value="${utilisateur.id}">
                <div>
                    <div>
                        <p><label for="pseudo">Pseudo :</label>
                            <input type="text" name="pseudo" id="pseudo" value="${utilisateur.pseudo}" required></p>
                        <p><label for="prenom">Prenom :</label>
                            <input type="text" name="prenom" id="prenom" value="${utilisateur.prenom}" required></p>
                        <p><label for="telephone">Telephone :</label>
                            <input type="text" name="telephone" id="telephone" value="${utilisateur.telephone}"></p>
                        <p><label for="codePostal">Code Postal :</label>
                            <input type="text" name="codePostal" id="codePostal" value="${utilisateur.codePostal}" required></p>
                        <p><label for="motDePasse">Mot de Passe :</label>
                            <input type="text" name="motDePasse" id="motDePasse" value="${utilisateur.motDePasse}" required></p>
                    </div>
                    <div>
                        <p><label for="nom">Nom :</label>
                            <input type="text" name="nom" id="nom" value="${utilisateur.nom}" required></p>
                        <p><label for="email">Email :</label>
                            <input type="text" name="email" id="email" value="${utilisateur.email}" required></p>
                        <p><label for="rue">Rue :</label>
                            <input type="text" name="rue" id="rue" value="${utilisateur.rue}" required></p>
                        <p> <label for="ville">Ville :</label>
                            <input type="text" name="ville" id="ville" value="${utilisateur.ville}" required></p>
                        <p> <label for="confirmeMotDePasse">Confirmer Mot de passe :</label>
                            <input type="text" name="confirmeMotDePasse" id="confirmeMotDePasse" value="${utilisateur.motDePasse}" required> </p>
                    </div>
                </div>
                <p class="p-button">
                    <input type="submit" value="Enregistrer">
                    <input type="button" value="Retour" onclick="window.location.href='<%=request.getContextPath()%>/profile';" />
                </p>
            </form>
            <p class="message-erreur">${message}</p>
            <a href="#" onclick="document.getElementById('modalDelete').style.display='block'">Supprimer</a>
            <!-- MODALE DE CONFIRMATION AVANT SUPPRESSION PROFIL -->
            <div id="modalDelete" class="modal-delete" style="display: none">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4>ATTENTION !</h4>
                        <button onclick="document.getElementById('modalDelete').style.display='none'" class="button-modal"><span>X</span></button>
                    </div>
                    <div class="modal-body">
                        <p>Vous étes sur le pont de supprimer votre profil !</p>
                        <p>Etes vous bien sur de vouloir le supprimer ?</p>
                        <p>Action irreversible !</p>
                    </div>
                    <div class="modal-footer">
                        <form method="post" action="<%=request.getContextPath()%>/delete_profil">
                            <input type="hidden" name="id" value="${utilisateur.id}">
                            <input type="submit" value="SUPPRIMER">
                            <input type="button" value="ANNULER" onclick="window.location.href='<%=request.getContextPath()%>/update_profile';" />
                        </form>
                    </div>
                </div>
            </div>
        </article>
    </main>

<jsp:include page="footer.jsp" />
</body>
</html>
