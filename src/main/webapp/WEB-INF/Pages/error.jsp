<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profil</title>
    <style>
        <%@ include file="../../css/_global.css"%>
    </style>
    <%--<link rel="stylesheet" type="text/css" href="./css/_global.css">--%>
</head>
<body>
<jsp:include page="./header.jsp" />

<main>
    <%
        String errorValue = request.getParameter("error");
    %>
    <div class="error-container">
        <h3><%= errorValue %></h3>
        <a href="<%= request.getContextPath() %>">Retourner à l'accueil</a>
    </div>
</main>

<jsp:include page="./footer.jsp" />
</body>
</html>
