<%@ page import="fr.eni.eniEncheres.bo.Utilisateur" %>
<header>

    <div class="logo">
        <h2><a href="<%=request.getContextPath()%>/">Les Encheres du 44</a></h2>
    </div>
    <div class="nav">
        <ul>
            <%
                Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
                if ((utilisateur != null) && (utilisateur.getCompteActif())) {
                    out.println("<li><a href=\"" +request.getContextPath()+ "/encheres\">Encheres</a></li>");
                    out.println("<li><a href=\"" +request.getContextPath()+ "/ajout_vente\">Vendre un article</a></li>");
                    out.println("<li><a href=\"" +request.getContextPath()+ "/profile\">Mon profil</a></li>");
                    out.println("<li><a href=\"" +request.getContextPath()+ "/deconnexion\">Deconnexion</a></li>");
                } else {
                    out.println("<li><a href=\"" +request.getContextPath()+ "/connection\">S'inscrire - Se connecter</a></li>");
                }
            %>
        </ul>
    </div>
</header>