<%--
  Created by IntelliJ IDEA.
  User: Lolo formation
  Date: 11/01/2021
  Time: 11:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="div-encherir">
    <form method ="post" action="detailEnchere">
        <label for="montantEnchere">Ma Proposition : </label>
        <input type="number" id ="montantEnchere" name="montantEnchere"
               min =<c:choose>
                       <c:when test="${enchere.montantEnchere > 0}">"${enchere.montantEnchere +1}" value="${enchere.montantEnchere+1}" </c:when>
        <c:otherwise>"${enchere.article.prixInitial}" value="${enchere.article.prixInitial}"</c:otherwise>
        </c:choose>
        />
        <input type="hidden" value="${enchere.article.id}" name="idArticle"/>
        <input type="hidden" value="${utilisateur.id}" name="idUtilisateur"/>
        <input type="submit" value="Enchérir" />
    </form>
    <p class="message-erreur">${message}</p>
    <p class="message-succes">${message_succes}</p>
</div>