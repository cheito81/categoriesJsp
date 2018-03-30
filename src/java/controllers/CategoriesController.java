package controllers;

import java.io.FileNotFoundException;
import model.persistence.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.*;

/**
 * Esta clase gestiona los usuarios de mi web
 *@author Jose Gimenez
 *@version 2018/01/16
 */

@WebServlet(name = "CategoriesController", urlPatterns = {"/CategoriesController"})
public class CategoriesController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.io.FileNotFoundException
     * @throws java.lang.ClassNotFoundException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileNotFoundException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
         request.setCharacterEncoding("UTF-8");
        if (request.getParameter("action") != null) {
            String choice = request.getParameter("action");
            switch (choice) { 
           
                case "list_category":
                   listCategory(request,response);
                   break;
                   
            }

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
            throws ServletException, IOException, FileNotFoundException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CategoriesController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            throws ServletException, IOException, FileNotFoundException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CategoriesController.class.getName()).log(Level.SEVERE, null, ex);
        }
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

   

/**Muestra todos los usuarios en una lista
 * @param request recoge los valores de mi formulario
 * @param response redirecciona hacia la página welcome en caso positivo
 * @throws ServletException
 * @throws IOException 
 */
    private void listCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FileNotFoundException, ClassNotFoundException {
        String ruta = getServletContext().getRealPath("/WEB-INF/");
            
        CategoryDAO helper = new CategoryDAO(ruta);
        try {
            ArrayList<Category> categories= (ArrayList<Category>) helper.getCategories();
            request.setAttribute("listaCategory", categories);
            
        } catch (SQLException e) {
            
        } catch (Exception e) {
            
        }
        HttpSession sesion = request.getSession();
        User u = (User) sesion.getAttribute("user");
        if (u==null) {
            RequestDispatcher d=request.getRequestDispatcher("index.jsp");
       d.forward(request, response);
        } else {
            RequestDispatcher d=request.getRequestDispatcher("welcome.jsp");
       d.forward(request, response);
        }
        
     }
}
