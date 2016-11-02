<%-- 
    Document   : correctoInsertDelete
    Created on : 31-oct-2016, 12:01:29
    Author     : Oscar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Operacion correcta</title>
    </head>
    <body>
        <h1><%=request.getAttribute("correcto")%> </h1>
        <h3><a href="<%=request.getContextPath()%>"> Enlace al index </a></h3>
    </body>
</html>
