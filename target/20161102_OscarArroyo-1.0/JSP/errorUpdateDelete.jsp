<%-- 
    Document   : errorInsertDelete
    Created on : 31-oct-2016, 12:03:12
    Author     : Oscar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/estilos.css" />
        <title>Error en la operacion</title>
    </head>
    <body>
        <h1><%=request.getAttribute("error")%> </h1>
        <h3 id="error"><a href="<%=request.getContextPath()%>"> Enlace al index </a></h3>
    </body>
</html>
