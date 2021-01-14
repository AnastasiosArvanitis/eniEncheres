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
        <%@include file="../../css/admin.css" %>
    </style>

</head>
<body>
<%@ include file="../Pages/header.jsp"%>
    <main>
        <h2>Administration</h2>

            <article class="admin">
                <h3>Utilisateur</h3>
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
                        <th>ADMIN</th>
                        <th>COMPTE ACTIF</th>
                    </tr>
                    <c:forEach items="${listUtilisateur}" var="acheteur" >
                        <tr class="utilisateur">
                            <td>${acheteur.id}</td>
                            <td>${acheteur.pseudo}</td>
                            <td>${acheteur.nom}</td>
                            <td>${acheteur.prenom}</td>
                            <td>${acheteur.email}</td>
                            <td>${acheteur.telephone}</td>
                            <td>${acheteur.rue}</td>
                            <td>${acheteur.codePostal}</td>
                            <td>${acheteur.ville}</td>
                            <td>${acheteur.motDePasse}</td>
                            <td>${acheteur.credit}</td>
                            <td>${acheteur.administration}</td>
                            <td>${acheteur.compteActif}</td>
                            <td>
                                    <form method="post" action="admin">
                                        <input type="hidden" name="id" value="${acheteur.id}">
                                        <input type="submit" value="SUPPRIMER" class="delete">
                                    </form>
                            </td>
                            <td>
                                <form method="post" action="admin_desactive">
                                    <input type="hidden" name="id" value="${acheteur.id}">
                                    <input type="hidden" name="compteActif" value= "false">
                                    <input type="submit" value="DESACTIVER" class="desactiver">
                                </form>

                            </td>
                            <td>
                                <form method="post" action="admin_desactive">
                                    <input type="hidden" name="id" value="${acheteur.id}">
                                    <input type="hidden" name="compteActif" value= "true">
                                    <input type="submit" value="ACTIVER" class="desactiver">
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <p class="message-erreur">${message}</p>
                <p class="message-succes">${messageSucces}</p>
            </article>

        <article class="admin">
            <h3>Categorie</h3>
            <a href="#" onclick="document.getElementById('modalAdd').style.display='block'">+ Ajouter une nouvelle categorie</a>
            <!-- MODALE D AJOUT DE CATEGORIE -->
            <div id="modalAdd" class="modal-delete" style="display: none">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4>NOUVELLE CATEGORIE !</h4>
                        <button onclick="document.getElementById('modalAdd').style.display='none'" class="button-modal"><span>X</span></button>
                    </div>
                    <div class="modal-body">
                        <form method="post" action="admin_insert_categorie">
                            <label for="libelle">Libelle de votre nouvelle categorie :</label>
                            <input type="text" name="libelle" id="libelle">
                            <input type="submit" value="AJOUTER" onclick="document.getElementById('modalAdd').style.display='none'" />
                            <input type="button" value="ANNULER" onclick="window.location.href='<%=request.getContextPath()%>/admin';" />
                        </form>
                    </div>
                    <div class="modal-footer">

                    </div>
                </div>
            </div>
            <table  class="table-admin">
                <tr>
                    <th>ID</th>
                    <th>LIBELLE</th>
                </tr>
                <c:forEach items="${listCategorie}" var="categorie" >
                    <tr class="utilisateur">
                        <td>${categorie.id}</td>
                        <td>${categorie.libelle}</td>
                        <td>
                            <form method="post" action="admin_update_categorie">
                                <input type="hidden" name="id" value="${categorie.id}">
                                <input type="text" name="libelle" value="${categorie.libelle}">
                                <input type="submit" value="MODIFIER" class="desactiver" />
                            </form>
                        </td>
                        <td>
                            <form method="post" action="admin_delete_categorie">
                                    <input type="hidden" name="id" value="${categorie.id}">
                                   <input type="submit" value="SUPPRIMER" class="delete" />
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>

        </article>



    </main>


<%@include file="../Pages/footer.jsp" %>
</body>
</html>
