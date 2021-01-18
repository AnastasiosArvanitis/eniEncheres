<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <form method="post" action="validerTransaction">
        <input type="submit" value="objet récupéré">
        <input type="hidden" name="idArticle" value="${enchere.article.id}">
        <input type="hidden" name="idAcheteur" value="${acheteur}">
        <input type="hidden" name="idVendeur" value="${vendeur}">
    </form>
</div>