/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.controladores;

import es.albarregas.beans.Ave;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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
@WebServlet(name = "ControladorInsert", urlPatterns = {"/ControladorInsert"})
public class ControladorInsert extends HttpServlet {

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
        //Si el usuario cancela la operacion lo enviaremos al index
        if (request.getParameter("cancelar") != null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            //Si el usuario no confirma los datos introducidos tendremos que pasarle de nuevo el objeto para cargar los datos en el formulario de insercion.
        } else if (request.getParameter("atras") != null) {
            Ave ave = new Ave();
            ave.setAnilla(request.getParameter("anilla"));
            ave.setEspecie(request.getParameter("especie"));
            ave.setLugar(request.getParameter("lugar"));
            ave.setFecha(request.getParameter("anio") + "/" + request.getParameter("mes") + "/" + request.getParameter("dia"));
            request.setAttribute("ave", ave);
            request.getRequestDispatcher("JSP/insertar.jsp").forward(request, response);
            //Si el usuario confirma los datos introducidos
        } else if (request.getParameter("continuar") != null) {

            Statement sentencia = null;
            Connection conexion = null;

            String anilla = request.getParameter("anilla");
            String especie = request.getParameter("especie");
            String lugar = request.getParameter("lugar");

            int di = Integer.parseInt(request.getParameter("dia"));
            int me = Integer.parseInt(request.getParameter("mes"));
            int an = Integer.parseInt(request.getParameter("anio"));

            String fecha = an + "/" + me + "/" + di;
            String sql = "insert into aves (anilla,especie,lugar,fecha)" + "values('" + anilla + "','" + especie + "','" + lugar + "','" + fecha + "')";
            String url = null;

            //Control de errores
            boolean controlarFecha = false;
            boolean controlarEspecie = true;
            boolean controlarLugar = true;

            if (especie.isEmpty()) {
                controlarEspecie = false;
                url = "error.jsp";
                request.setAttribute("error", "El campo especie esta vacio");
            }
            if (lugar.isEmpty()) {
                controlarLugar = false;
                url = "error.jsp";
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
                url = "error.jsp";
                request.setAttribute("error", "Error,Fecha incorrecta");
            }
            //Si no existe ningun error
            if (controlarEspecie && controlarLugar && controlarFecha) {
                try {
                    conexion = datasource.getConnection();
                    sentencia = conexion.createStatement();
                    //Ejecutamos la sentencia insert
                    sentencia.executeUpdate(sql);
                    request.setAttribute("correcto", "Datos introducidos correctamente.");
                    url = "insertCorrecto.jsp";
                } catch (SQLException e) {
                    url = "error.jsp";
                    //Si se produce este error a la hora de ejecutar la sentencia es que el usuario ha introducido una anilla duplicada
                    if (e.getErrorCode() == 1062) {
                        request.setAttribute("error", "La anilla " + request.getParameter("anilla") + " esta duplicada. ");
                    } else {
                        request.setAttribute("error", "La anilla tiene 3 caracteres de longitud maxima");
                    }
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
                        if (sentencia != null) {
                            sentencia.close();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                }

            }   //Redirigimos a la pagina que contenga la variable url
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
