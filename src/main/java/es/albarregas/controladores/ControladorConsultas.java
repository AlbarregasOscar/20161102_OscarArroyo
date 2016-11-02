/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.controladores;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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
@WebServlet(name = "ControladorConsultas", urlPatterns = {"/ControladorConsultas"})
public class ControladorConsultas extends HttpServlet {

    DataSource datasource;

    public void init(ServletConfig config) throws ServletException {
        try {
            Context initialContext = new InitialContext();
            datasource
                    = (DataSource) initialContext.lookup("java:comp/env/jdbc/CRUD");
        } catch (NamingException ex) {
            System.out.println("Problemas en el acceso a la BD...");
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
        //Si el usuario pulsa el boton de cancelar o volver lo redirigimos al index
        if (request.getParameter("cancelar") != null || request.getParameter("volver") != null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            //Si el usuario confirma los registros que va a borrar
        } else if (request.getParameter("aceptar") != null) {

            PreparedStatement preparada = null;
            Connection conexion = null;
            Enumeration<String> parametros = request.getParameterNames();
            String sql = "delete from aves where anilla=?";
            try {
                conexion = datasource.getConnection();
                //Buscamos los parametros que comiencen por ave
                while (parametros.hasMoreElements()) {
                    String elemento = parametros.nextElement();
                    if (elemento.startsWith("ave")) {
                        preparada = conexion.prepareStatement(sql);
                        preparada.setString(1, request.getParameter(elemento));
                        try {
                            //Ejecutamos la query
                            preparada.executeUpdate();
                            request.setAttribute("correcto", "Operacion completada");
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
                    if (preparada != null) {
                        preparada.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
            //Redirigimos al jsp que nos muestra un mensaje afirmativo
            request.getRequestDispatcher("/JSP/correctoUpdateDelete.jsp").forward(request, response);
            //Si el usuario confirma los datos introducidos de la pagina actualizar
        } else if (request.getParameter("continuarUpdate") != null) {
            String anilla = request.getParameter("anilla");
            String especie = request.getParameter("especie");
            String lugar = request.getParameter("lugar");
            int di = Integer.parseInt(request.getParameter("dia"));
            int me = Integer.parseInt(request.getParameter("mes"));
            int an = Integer.parseInt(request.getParameter("anio"));
            Connection conexion = null;
            Statement sentencia = null;
            String sql
                    = "UPDATE aves SET especie='" + especie + "',lugar='" + lugar
                    + "',fecha='" + an + "/" + me + "/" + di + "'"
                    + " WHERE anilla='" + anilla + "'";
            String url = null;

            //Comprobacion de errores
            boolean controlarFecha = false;
            boolean controlarEspecie = true;
            boolean controlarLugar = true;

            if (especie.isEmpty()) {
                controlarEspecie = false;
                url = "errorUpdateDelete.jsp";
                request.setAttribute("error", "El campo especie esta vacio");
            }
            if (lugar.isEmpty()) {
                controlarLugar = false;
                url = "errorUpdateDelete.jsp";
                request.setAttribute("error", "El campo lugar esta vacio");
            }

            if (me == 1 || me == 3 || me == 5 || me == 7 || me == 8 || me == 10 || me == 12) {
                if (di <= 31) {
                    controlarFecha = true;
                }
            } else {
                if (me == 4 || me == 6 || me == 9 || me == 11) {
                    if (di <= 30) {
                        controlarFecha = true;
                    }
                }
                if (me == 2) {
                    if ((an % 4 == 0) && (an % 100 != 0) || (an % 400 == 0)) {
                        if (di <= 29) {
                            controlarFecha = true;
                        }
                    } else if (di <= 28) {
                        controlarFecha = true;
                    }
                }
            }
            if (!controlarFecha) {
                url = "errorUpdateDelete.jsp";
                request.setAttribute("error", "Fecha incorrecta");
            }
            //Si no hay ningun error
            if (controlarEspecie && controlarLugar && controlarFecha) {
                try {
                    conexion = datasource.getConnection();

                    try {
                        sentencia = conexion.createStatement();
                        //Ejecutamos la query
                        sentencia.executeUpdate(sql);
                        url = "correctoUpdateDelete.jsp";
                        request.setAttribute("correcto", "Operacion completada");

                    } catch (SQLException e) {
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    //Cerramos las conexiones
                    try {
                        if (conexion != null) {
                            conexion.close();
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
            }
            //Redirigimos al usuario segun el contenido de la variable url(error o correcto)
            request.getRequestDispatcher("JSP/" + url).forward(request, response);
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
