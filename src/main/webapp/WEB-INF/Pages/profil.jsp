<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.eniEncheres.bo.Utilisateur" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profil</title>
    <style>
        <%@ include file="../../css/_global.css"%>
        <%@ include file="../../css/profil.css" %>
    </style>
    <%--<link rel="stylesheet" type="text/css" href="./css/_global.css">--%>
</head>
<body>
<jsp:include page="./header.jsp" />
<%
    Utilisateur utilisateur =null;
    if (session.getAttribute("utilisateur") == null) {
        response.sendRedirect("/encheres/error?error=userNotExist");
    } else {
        utilisateur = (Utilisateur) session.getAttribute("utilisateur");
    }
%>
    <main>
        <h2>Mon Profil</h2>
        <article class="monProfil">

            <p><span>Pseudo : </span><span> ${utilisateur.pseudo}</span></p>
            <p><span>Nom :</span><span>${utilisateur.nom}</span></p>
            <p><span>Prenom :</span><span>${utilisateur.prenom}</span></p>
            <p><span>Email :</span><span>${utilisateur.email}</span></p>
            <p><span>Téléphone :</span><span>${utilisateur.telephone}<span></p>
            <p><span>Rue :</span><span>${utilisateur.rue}<span/></p>
            <p><span>Code Postal :</span><span>${utilisateur.codePostal}<span/></p>
            <p><span>Ville :</span><span>${utilisateur.ville}</span></p>
            <p style="display: none">${utilisateur.motDePasse}</p>
            <p><span>Credit :</span><span>${utilisateur.credit}</span></p>
            <p><span class="span"><a href="#" onclick="document.getElementById('modalDelete').style.display='block'">Acheter des credits</a></span></p>
            <!-- MODALE DE CONFIRMATION AVANT SUPPRESSION PROFIL -->
            <div id="modalDelete" class="modal-delete" style="display: none">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4>ATTENTION !</h4>
                        <button onclick="document.getElementById('modalDelete').style.display='none'" class="button-modal"><span>X</span></button>
                    </div>
                    <div class="modal-body">
                        <p>Vous avez : ${utilisateur.credit} credits</p>
                        <p>Combien souhaitez vous en acheter ?</p>
                        <form method="post" action="<%=request.getContextPath()%>/profil_add_credit">
                            <input type="hidden" name="id" value="${utilisateur.id}">
                            <input type="number" name="montant">
                            <p></p>
                            <input type="submit" value="ACHETER">
                            <input type="button" value="ANNULER" onclick="window.location.href='<%=request.getContextPath()%>/profile';" />
                        </form>
                    </div>
                    <div class="modal-footer">
                    </div>
                </div>
            </div>
        </article>
        <p class="btn-update-profile">
            <a href="<%=request.getContextPath()%>/update_profile">Modifier</a>
        </p>
            <!-- MESSAGE EN CAS DE SUCCES LORS DE L UPDATE PROFIL -->
            <p style="color: green">${message}</p>
    </main>

<jsp:include page="./footer.jsp" />
</body>
</html>
