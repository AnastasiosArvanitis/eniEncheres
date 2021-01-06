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

            <p>Pseudo : <%=utilisateur.getPseudo() %> </p>
            <p>Nom : <%=utilisateur.getNom() %></p>
            <p>Prenom : <%=utilisateur.getPrenom() %></p>
            <p>Email : <%=utilisateur.getEmail() %></p>
            <p>Téléphone : <%=utilisateur.getTelephone() %></p>
            <p>Rue : <%=utilisateur.getRue() %></p>
            <p>Code Postal : <%=utilisateur.getCodePostal() %></p>
            <p>Ville : <%=utilisateur.getVille() %></p>
            <p style="display: none"><%=utilisateur.getMotDePasse() %></p>
        </article>

            <a href="<%=request.getContextPath()%>/update_profile">Modifier</a>
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
                        <p>Etes vous bien sur de vouloir le supprimer?</p>
                    </div>
                    <div class="modal-footer">
                        <form method="post" action="<%=request.getContextPath()%>/delete_profil">
                            <input type="hidden" name="id" value="<%=utilisateur.getId() %>">
                            <input type="submit" value="SUPPRIMER">
                            <input type="reset" value="ANNULER" onclick="window.location.href='<%=request.getContextPath()%>/profile';" />
                        </form>
                    </div>
                </div>
            </div>

            <!-- MESSAGE EN CAS DE SUCCES LORS DE L UPDATE PROFIL -->
            <p style="color: green">
                <% String messageSuccesUpdateProfil = (String) request.getAttribute("message");
                    if (messageSuccesUpdateProfil != null){
                        out.println(messageSuccesUpdateProfil);
                    }else{
                        out.println("");
                    }
                %></p>

    </main>

<jsp:include page="./footer.jsp" />
</body>
</html>
