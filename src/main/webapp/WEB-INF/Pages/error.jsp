<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profil</title>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=East+Sea+Dokdo&family=Open+Sans:wght@300;600&family=Orbitron:wght@500&display=swap" rel="stylesheet">
    <style>
        <%@ include file="../../css/_global.css"%>
    </style>
    <%--<link rel="stylesheet" type="text/css" href="./css/_global.css">--%>
    <script src="https://kit.fontawesome.com/0cf40fbd40.js" crossorigin="anonymous"></script>
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
