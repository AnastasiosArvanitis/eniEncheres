<%@ page import="fr.eni.eniEncheres.bo.Utilisateur" %>
<header>
    <div class="logo">
        <h2><a href="<%=request.getContextPath()%>/">Les Encheres du 44</a></h2>
    </div>
    <%
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if ((utilisateur != null) && (utilisateur.getCompteActif())) {
    %>
    <div class="credit">Credit :<%=utilisateur.getCredit()%></div>

</header>
    <nav>
        <ul>
            <%
                if(utilisateur.getAdministration() == true){
                    out.println("<li><a href=\"" +request.getContextPath()+ "/admin\">Admin</a></li>");
                }
                    out.println("<li><a href=\"" +request.getContextPath()+ "/encheres\">Encheres</a></li>");
                    out.println("<li><a href=\"" +request.getContextPath()+ "/ajout_vente\">Vendre un article</a></li>");
                    out.println("<li><a href=\"" +request.getContextPath()+ "/profile\">Mon profil</a></li>");
                    out.println("<li><a href=\"" +request.getContextPath()+ "/deconnexion\">Deconnexion</a></li>");
            %>

            <%
                }else if((utilisateur != null) && (!utilisateur.getCompteActif())) {
                    out.println("<li><a href=\"" +request.getContextPath()+ "/profile\">Mon profil</a></li>");
                    out.println("<li><a href=\"" +request.getContextPath()+ "/deconnexion\">Deconnexion</a></li>");
            %>
            <%
                } else {
                    out.println("</header><nav><ul><li><a href=\"" +request.getContextPath()+ "/connection\">S'inscrire - Se connecter</a></li>");
                }
            %>
        </ul>
    </nav>
<div class="menu-btn">
    <i id="menu_btn" class="fas fa-bars"></i>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
    $(document).ready(function(){

        $('#menu_btn').click(function(){
            if ($('nav ul').css('display') == 'none'){
                //$('nav ul').show('slow',function(){
                    $('nav').css({'display':'flex'});
                    $('nav ul').css({'display': 'flex','flex-direction':'column'});
                //});

            }else{
                $('nav ul').hide('slow');
            }
        });


    });
</script>












