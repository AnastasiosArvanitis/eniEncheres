<%@ page import="java.util.List" %>
<%@ page import="fr.eni.eniEncheres.bo.Categorie" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Enchere Eni</title>
    <style>
        <%@include file="../../css/_global.css" %>
        <%@include file="../../css/admin.css" %>
    </style>

</head>
<body>
<%@ include file="../Pages/header.jsp"%>
    <main>
        <h2>Administration</h2>

            <article class="admin">
                <table class="table-admin">
                    <tr>
                        <th>ID</th>
                        <th>PSEUDO</th>
                        <th>NOM</th>
                        <th>PRENOM</th>
                        <th>EMAIL</th>
                        <th>TELEPHONE</th>
                        <th>RUE</th>
                        <th>CODE POSTAL</th>
                        <th>VILLE</th>
                        <th>MOT DE PASSE</th>
                        <th>CREDIT</th>
                        <th>ADMINISTRATEUR</th>
                        <th>COMPTE ACTIF</th>
                    </tr>
                    <c:forEach items="${listUtilisateur}" var="utilisateur" >
                        <tr class="utilisateur">
                            <td>${utilisateur.id}</td>
                            <td>${utilisateur.pseudo}</td>
                            <td>${utilisateur.nom}</td>
                            <td>${utilisateur.prenom}</td>
                            <td>${utilisateur.email}</td>
                            <td>${utilisateur.telephone}</td>
                            <td>${utilisateur.rue}</td>
                            <td>${utilisateur.codePostal}</td>
                            <td>${utilisateur.ville}</td>
                            <td>${utilisateur.motDePasse}</td>
                            <td>${utilisateur.credit}</td>
                            <td>${utilisateur.administration}</td>
                            <td>${utilisateur.compteActif}</td>
                            <td>
                                <form method="post" action="admin">
                                    <input type="hidden" name="id" value="${utilisateur.id}">
                                    <input type="submit" value="SUPPRIMER" class="delete">
                                </form>

                            </td>
                            <td>
                                <form method="post" action="">
                                    <input type="hidden" name="id" value="${utilisateur.id}">
                                    <input type="submit" value="DESACTIVER" class="desactiver">
                                </form>

                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <p class="message-erreur">${message}</p>
                <p class="message-succes">${messageSucces}</p>
            </article>



    </main>


<%@include file="../Pages/footer.jsp" %>
</body>
</html>
