/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.controladores;

import es.albarregas.beans.Ave;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author Oscar
 */
@WebServlet(name = "ControladorOpciones", urlPatterns = {"/ControladorOpciones"})
public class ControladorOpciones extends HttpServlet {

    DataSource datasource;

    public void init(ServletConfig config) throws ServletException {
        try {
            Context initialContext = new InitialContext();
            datasource = (DataSource) initialContext.lookup("java:comp/env/jdbc/CRUD");
        } catch (NamingException ex) {
            System.out.println("Problemas en el acceso a la BD...");
            ex.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String parametro = request.getParameter("p");
        String url = null;
        ArrayList<Ave> aves = new ArrayList();
        Ave ave = new Ave();
        Connection conexion = null;
        Statement sentencia = null;
        ResultSet resultado = null;
        //Segun la opcion que pulse el usuario dividiremos el programa en 2 partes. La primera para visualizar,modificar y borrar registros y la segunda para insertar-
        switch (parametro) {
            case "visualizar":
            case "modificar":
            case "borrar":
                url = "opciones.jsp";
                try {
                    conexion = datasource.getConnection();
                    sentencia = conexion.createStatement();
                    //Hacemos una sentencia select para cargar en el jsp todas las aves.
                    resultado = sentencia.executeQuery("select * from aves");
                    while (resultado.next()) {
                        ave = new Ave();
                        ave.setAnilla(resultado.getString(1));
                        ave.setEspecie(resultado.getString(2));
                        ave.setLugar(resultado.getString(3));
                        ave.setFecha(resultado.getString(4));
                        aves.add(ave);
                    }
                    request.setAttribute("ave", aves);
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    //cerramos conexiones
                    try {
                        if (conexion != null) {
                            conexion.close();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        if (resultado != null) {
                            resultado.close();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        if (sentencia != null) {
                            sentencia.close();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

                break;
            //Nos vamos al camino de insertar
            case "insertar":
                url = "insertar.jsp";
                break;

        }
        //Segun el parametro "p" nos enviara a una pagina o a otra.
        request.setAttribute("p", parametro);
        request.getRequestDispatcher("JSP/" + url).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
