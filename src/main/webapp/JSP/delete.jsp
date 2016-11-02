<%-- 
    Document   : delete
    Created on : 29-oct-2016, 12:29:08
    Author     : Oscar
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="es.albarregas.beans.Ave"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete</title>
    </head>
    <body>
        <% ArrayList<Ave> aves = (ArrayList) request.getAttribute("ave");

            if (aves.size() != 0) {
        %>
        <form method="post" action="ControladorConsultas">
            <h2> ¿Quieres borrar estos registros? </h2>
            <% for (int i = 0; i < aves.size(); i++) {
            %>
            <h3> Anilla: <%= aves.get(i).getAnilla()%></h3>
            <h3> Especie: <%= aves.get(i).getEspecie()%></h3>
            <h3> Lugar: <%= aves.get(i).getLugar()%></h3>
            <h3> Fecha: <%= aves.get(i).getFecha()%></h3>
            <input type="hidden" name="ave<%=i%>" value="<%=aves.get(i).getAnilla()%>"/>
            </br>
            <%
                }
            %>
            <input type="submit" name="aceptar" value="Aceptar">
            <input type="submit" name="cancelar" value="Cancelar">
        </form> 
        <%
        } else {
        %><form method="post" action="ControladorConsultas">
            <h2> No has seleccionado ningun registro para borrarlo </h2>
            <input type="submit" name="volver" value="Volver">
        </form>
        <%
            }
        %>
    </body>
</html>
