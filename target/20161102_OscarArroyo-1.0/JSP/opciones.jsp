<%-- 
    Document   : opciones
    Created on : 27-oct-2016, 11:25:20
    Author     : Oscar
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="es.albarregas.beans.Ave"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Opciones</title>
    </head>
    <body>
        <%ArrayList<Ave> aves = null;
            if (request.getAttribute("ave") != null) {
                aves = (ArrayList) request.getAttribute("ave");
            }
            if (aves.isEmpty()) {
        %>
        <form method="post" action="<%=request.getContextPath()%>">
            <h3> No hay ningun registro en la tabla, procede a insertarlo </h3>
            <input type="submit" name="index" value="Volver al index">
        </form>
        <% } else if (request.getAttribute("p").equals("visualizar")) {
            for (int i = 0; i < aves.size(); i++) {
        %>
        <h2> Ave n&uacute;mero <%=i + 1%> </h2>
        <h3> Anilla: <%= aves.get(i).getAnilla()%></h3>
        <h3> Especie: <%= aves.get(i).getEspecie()%></h3>
        <h3> Lugar: <%= aves.get(i).getLugar()%></h3>
        <h3> Fecha: <%= aves.get(i).getFecha()%></h3>
        </br>

        <%
            }
        %>
        <form method="post" action="ControladorUpdateODelete">
            <input type="submit" name="continuar" value="Volver al index"/>
        </form>
        <%
        } else if (request.getAttribute("p").equals("borrar")) {
        %>
        <form method="post" action="ControladorUpdateODelete">
            <%
                for (int i = 0; i < aves.size(); i++) {
            %>
            <input type="checkbox" name="ave<%=i%>" value="<%=aves.get(i).getAnilla()%>"/><%= aves.get(i).getEspecie()%>
            </br>
            <%
                }
            %>
            <input type="submit" name="borrar" value="Borrar"/>
            <input type="submit" name="cancelar" value="Cancelar"/>
        </form>
        <%
        } else if (request.getAttribute("p").equals("modificar")) {
        %>
        <form method="post" action="ControladorUpdateODelete">
            <%
                for (int i = 0; i < aves.size(); i++) {
            %><input type="radio" name="modificarave" value="<%=aves.get(i).getAnilla()%>"/><%= aves.get(i).getEspecie()%>
            </br>
            <%
                }
            %> 
            <input type="submit" name="modificar" value="Modificar"/>
            <input type="submit" name="cancelar" value="Cancelar"/>
        </form>
        <%
            }
        %>


    </body>
</html>
