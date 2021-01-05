<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
    <style>
        <%@ include file="../../css/_global.css"%>
    </style>
    <%--<link rel="stylesheet" type="text/css" href="./css/_global.css">--%>
</head>
<body>
<main>
    <h1>Welcome Encheres application</h1>
    <p><a href="<%=request.getContextPath()%>/inscription">Inscription</a></p>
    <p><a href="<%=request.getContextPath()%>/connection">Connection</a></p>
</main>


</body>
</html>
