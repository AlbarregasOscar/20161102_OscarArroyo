/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.controladores;

import es.albarregas.beans.Ave;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
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
@WebServlet(name = "ControladorUpdateODelete", urlPatterns = {"/ControladorUpdateODelete"})
public class ControladorUpdateODelete extends HttpServlet {

    DataSource datasource;

    public void init(ServletConfig config) throws ServletException {
        try {
            Context initialContext = new InitialContext();
            datasource
                    = (DataSource) initialContext.lookup("java:comp/env/jdbc/CRUD");
        } catch (NamingException ex) {
            System.out.println("Problemas en el acceso a la BD...");
            ex.printStackTrace();
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Ave> aves = new ArrayList();
        Ave ave = null;
        PreparedStatement preparada = null;
        Enumeration<String> parametros = request.getParameterNames();
        ResultSet resultado = null;
        Statement sentencia = null;
        Connection conexion = null;
        //Redireccionamos al index si el usuario cancela la operacion.
        if (request.getParameter("continuar") != null || request.getParameter("cancelar") != null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            //Aqui accedemos si el usuario ha marcado la opcion de borrar.
        } else if (request.getParameter("borrar") != null) {

            try {
                conexion = datasource.getConnection();

                while (parametros.hasMoreElements()) {
                    String elemento = parametros.nextElement();
                    if (elemento.startsWith("ave")) {
                        //Hacemos una select y mostramos tantos registros como el usuario haya marcado en el comboBox del jsp.
                        String sql = "select * from aves where anilla =?";
                        preparada = conexion.prepareStatement(sql);
                        preparada.setString(1, request.getParameter(elemento));
                        try {
                            resultado = preparada.executeQuery();
                            while (resultado.next()) {
                                ave = new Ave();
                                ave.setAnilla(resultado.getString("anilla"));
                                ave.setEspecie(resultado.getString("especie"));
                                ave.setLugar(resultado.getString("lugar"));
                                ave.setFecha(resultado.getString("fecha"));
                                aves.add(ave);
                            }

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
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
                    if (preparada != null) {
                        preparada.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            //Enviamos el ArrayList con el setAttribute y redirigimos la pagina
            request.setAttribute("ave", aves);
            request.getRequestDispatcher("JSP/delete.jsp").forward(request, response);
            //Aqui accedemos si el usuario ha pulsado la opcion de modificar
        } else if (request.getParameter("modificar") != null) {

            try {
                conexion = datasource.getConnection();

                try {
                    //Hacemos la select de la opcion que ha pulsado el usuario en los botones tipo radio
                    String sql = "select * from aves where anilla =" + request.getParameter("modificarave");
                    sentencia = conexion.createStatement();
                    resultado = sentencia.executeQuery(sql);
                    resultado.next();
                    ave = new Ave();
                    ave.setAnilla(resultado.getString("anilla"));
                    ave.setEspecie(resultado.getString("especie"));
                    ave.setLugar(resultado.getString("lugar"));
                    ave.setFecha(resultado.getString("fecha"));
                    request.setAttribute("ave", ave);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
            // Enviamos el Objeto tipo Ave con el setAttribute y redirigimos la pagina
            request.getRequestDispatcher("JSP/update.jsp").forward(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
