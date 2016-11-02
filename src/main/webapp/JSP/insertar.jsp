<%-- 
    Document   : insertar
    Created on : 27-oct-2016, 11:29:17
    Author     : Oscar
--%>

<%@page import="es.albarregas.beans.Ave"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pagina para insertar registros</title>
    </head>
    <body>
        <%Ave ave = null;
            String[] subCadena = null;
            String anio = null;
            String mes = null;
            String dia = null;
            if (request.getAttribute("ave") != null) {
                ave = (Ave) request.getAttribute("ave");
                subCadena = ave.getFecha().split("/");
                anio = subCadena[0];
                mes = subCadena[1];
                dia = subCadena[2];
            }
        %>
        <form method="post">
            <label> Anilla </label>
            <%if (ave == null) {%>
            <input type="text" name="anilla"/>
            <% } else {%>
            <input type="text" name="anilla" value="<%=ave.getAnilla()%>" />
            <% } %>
            <br/>
            <label> Especie </label>
            <%if (ave == null) {%>
            <input type="text" name="especie"/>
            <% } else {%>
            <input type="text" name="especie" value="<%=ave.getEspecie()%>" />
            <% } %>
            <br/>
            <label> Lugar </label>
            <%if (ave == null) {%>
            <input type="text" name="lugar"/>
            <% } else {%>
            <input type="text" name="lugar" value="<%=ave.getLugar()%>" />
            <% } %>
            <br/>
            <label> Fecha </label>
            <select name="dia" >
                <%if (dia != null) {%>
                <option selected><%=dia%></option>
                <%}%>
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
                <%if (mes != null) {%>
                <option selected><%=mes%></option>
                <%}%> 
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
                <%if (anio != null) {%>
                <option selected><%=anio%></option>
                <%}%>
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
            <br/>
            <input type="submit" name="enviar" value="Enviar" formaction="JSP/insertarIntermedia.jsp"/>
            <input type="reset" name="limpiar" value="Limpiar"/>
            <input type="submit" name="cancelar" value="Cancelar" formaction="<%=request.getContextPath()%>/ControladorInsert"/>
        </form>
    </body>
</html>
