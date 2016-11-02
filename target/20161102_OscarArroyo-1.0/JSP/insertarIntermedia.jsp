<%-- 
    Document   : insertarIntermedia
    Created on : 27-oct-2016, 12:02:00
    Author     : Oscar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/estilos.css" />
        <title>Confirmacion de datos</title>
    </head>
    <body>
        <h1>Has introducido los siguientes datos: </h1>
        <h3>Anilla : <%=request.getParameter("anilla")%></h3>
        <h3>Especie : <%=request.getParameter("especie")%></h3>
        <h3>Lugar : <%=request.getParameter("lugar")%></h3>
        <h3>Fecha : <%=request.getParameter("anio")%>/<%=request.getParameter("mes")%>/<%=request.getParameter("dia")%> </h3>
        <p>¿Son correctos estos datos? </p>
        <form method="post" action="../ControladorInsert">
            <input type="hidden" name="anilla" value="<%=request.getParameter("anilla")%>"/>
            <input type="hidden" name="especie" value="<%=request.getParameter("especie")%>"/>
            <input type="hidden" name="lugar" value="<%=request.getParameter("lugar")%>"/>
            <input type="hidden" name="anio" value="<%=request.getParameter("anio")%>"/>
            <input type="hidden" name="mes" value="<%=request.getParameter("mes")%>"/>
            <input type="hidden" name="dia" value="<%=request.getParameter("dia")%>"/>
            <input type="submit" name="continuar" value="Continuar"/>
            <input type="submit" name="atras" value="Volver atr&aacute;s"/>
        </form>
    </body>
</html>
