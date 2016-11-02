<%-- 
    Document   : update
    Created on : 29-oct-2016, 14:05:46
    Author     : Oscar
--%>

<%@page import="es.albarregas.beans.Ave"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/estilos.css" />
        <title>Update</title>
    </head>
    <body>
        <% Ave ave = null;
            if (request.getAttribute("ave") != null) {
                ave = (Ave) request.getAttribute("ave");
            }
            if (ave == null) {
        %>
        <form method="post" action="ControladorConsultas">
            <fieldset>
            <h3> No has seleccionado ningun ave para modificarla </h3>
            <input type="submit" name="volver" value="Volver"/>
            </fieldset>
        </form>
        <%
        } else {
            String[] sub = ave.getFecha().split("/");
            String anio = sub[0];
            String mes = sub[1];
            String dia = sub[2];
        %>
        <form method="post" action="ControladorConsultas">
            <fieldset>
            <label> Anilla: </label>
            <input type="text" name="anilla" value="<%= ave.getAnilla()%>" readonly>
            </br>
            <label> Especie: </label>
            <input type="text" name="especie" value="<%= ave.getEspecie()%>">
            </br>
            <label> Lugar: </label>
            <input type="text" name="lugar" value="<%= ave.getLugar()%>">
            </br>
            <label> Fecha </label>
            <select name="dia" >
                <option selected><%=dia%></option>
                <option>1</option>
                <option>2</option>
                <option>3</option>
                <option>4</option>
                <option>5</option>
                <option>6</option>
                <option>7</option>
                <option>8</option>
                <option>9</option>
                <option>10</option>
                <option>11</option>
                <option>12</option>
                <option>13</option>
                <option>14</option>
                <option>15</option>
                <option>16</option>
                <option>17</option>
                <option>18</option>
                <option>19</option>
                <option>20</option>
                <option>21</option>
                <option>22</option>
                <option>23</option>
                <option>24</option>
                <option>25</option>
                <option>26</option>
                <option>27</option>
                <option>28</option>
                <option>29</option>
                <option>30</option>
                <option>31</option>
            </select>

            <select name="mes">
                <option selected><%=mes%></option>
                <option>1</option>
                <option>2</option>
                <option>3</option>
                <option>4</option>
                <option>5</option>
                <option>6</option>
                <option>7</option>
                <option>8</option>
                <option>9</option>
                <option>10</option>
                <option>11</option>
                <option>12</option>   
            </select>
            <select name="anio">
                <option selected=""><%=anio%></option>
                <option>2000</option>
                <option>2001</option>
                <option>2002</option>
                <option>2003</option>
                <option>2004</option>
                <option>2005</option>
                <option>2006</option>
                <option>2007</option>
                <option>2008</option>
                <option>2009</option>
                <option>2010</option>
                <option>2011</option>
                <option>2012</option>
                <option>2013</option>
                <option>2014</option>
                <option>2015</option>
                <option>2016</option>
            </select>
            </br>
            <input type="submit" name="continuarUpdate" value="Continuar"/>
            <input type="submit" name="cancelar" value="Cancelar"/>
            </fieldset>
        </form>
        <%
            }
        %>
    </body>
</html>
