<%-- 
    Document   : insertCorrecto
    Created on : 27-oct-2016, 14:58:46
    Author     : Oscar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/estilos.css" />
        <title>Inserccion correcta</title>
    </head>
    <body>
        <h1><%=request.getAttribute("correcto")%> </h1>
        <h3 id="correcto"><a href="<%=request.getContextPath()%>"> Enlace al index </a></h3>
    </body>
</html>
