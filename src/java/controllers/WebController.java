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
import validation.ValidationForm;

/**
 * Esta clase gestiona los usuarios de mi web
 *@author Jose Gimenez
 *@version 2018/01/16
 */

@WebServlet(name = "WebController", urlPatterns = {"/WebController"})
public class WebController extends HttpServlet {

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
           
                  case "list_web":
                    listWeb(request, response,null);
                    break;
                case "add_web":
                    add_web(request, response);
                    break;
                    case "addWeb":
                    registerWeb(request, response);
                    break;
                    case "delete":
                    delete(request, response);
                    break;
                     case "update":
                        updateView(request, response);
                    break;
                    case "updateWeb":
                        update(request, response);
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

   

/**
     * Muestra todos los usuarios en una lista
     * @param request recoge los valores de mi formulario
     * @param response redirecciona hacia la página welcome en caso positivo
      * @param result confirmation delete
     * @throws ServletException
     * @throws IOException
     */
    private void listWeb(HttpServletRequest request, HttpServletResponse response, String result) throws ServletException, IOException, FileNotFoundException, ClassNotFoundException {
        String ruta = getServletContext().getRealPath("/WEB-INF/");

        WebDAO helper = new WebDAO(ruta);
        try {
            ArrayList<Web> web = (ArrayList<Web>) helper.getWeb();
            request.setAttribute("listaWeb", web);
            request.setAttribute("confirmations", result);

        } catch (SQLException e) {

        } catch (Exception e) {

        }
        HttpSession sesion = request.getSession();
        User u = (User) sesion.getAttribute("user");
        if (u == null) {
            RequestDispatcher d = request.getRequestDispatcher("index.jsp");
            d.forward(request, response);
        } else {
            RequestDispatcher d = request.getRequestDispatcher("WEBS.jsp");
            d.forward(request, response);
        }

    }
 /**
     * Muestra el formulario para añadir una Web
     * @param request recoge los valores de mi formulario
     * @param response redirecciona hacia la página welcome en caso positivo
     * @throws IOException
     * @throws ServletException
     */
    private void add_web(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<String> lista = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            lista.add("");
        }
        request.setAttribute("fields", lista);
        
        RequestDispatcher d = request.getRequestDispatcher("view/webViews/webRegister.jsp");
        d.forward(request, response);

    }
    
        /**
     * register a web in the database
     * @param request
     * @param response
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws ServletException
     */
    private void registerWeb(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, ServletException {
        request.setCharacterEncoding("UTF-8");
        //recollim i anem validant
        List<String> fieldOk = null;
        String ruta = getServletContext().getRealPath("/WEB-INF/");
        String result = "";
        boolean okWebCode = false;
        boolean okLink = false;
        int existWebName = 1;

        int valid = 0;
        Web newWeb = new Web();
        fieldOk = newWeb.notNull(request, response);

        if (fieldOk.size() == 0) { // validete all fields don´t null
          
            okWebCode = ValidationForm.isInteger(request.getParameter("webCode")); // valida que el campo web no tenga espacios

            okLink = ValidationForm.isLink(request.getParameter("link")); // valido link

            if (okWebCode) {
                WebDAO helper = new WebDAO(ruta);
                existWebName = helper.findWebCode(request.getParameter("webCode"));
            }

            if (existWebName == 0 && okLink == true) {

                newWeb = new Web(Integer.parseInt(request.getParameter("webCode")), request.getParameter("date"), request.getParameter("title"), request.getParameter("link")
                );

                WebDAO helper = new WebDAO(ruta);

                if (helper.insert(newWeb) == 1) {

                    result = "<h3>Register was successfully inserted</h3>" + result;
                    redirectToRegister(request, response, result);

                } else {
                    result = "<h3>At this time we are updating our website. Try it later</h3>" + result;
                   redirectToRegister(request, response, result);
                }

            } else {
                
                if (!okLink) {
                    result += "<p>Link not valid</p>";
                }
               
                 if (okWebCode) {
                    if (existWebName==1) {
                        result += "<p>Web Code already exists.</p>";
                    }
                } else {
                     result += "<p>Web Code has to be a integer.</p>";
                }
              
                redirectToRegister(request, response, result);
            }

        } else {
            for (String string : fieldOk) {
                result += "<p>" + string + "</p>";
            }

            redirectToRegister(request, response, result);
        }

    }
    
    /**
 * redirects to the registration form with a message
 * @param request
 * @param response
 * @param result message for the form register
 * @throws ServletException
 * @throws IOException 
 */
    private void redirectToRegister(HttpServletRequest request, HttpServletResponse response, String result) throws ServletException, IOException {
        List<String> lista = new ArrayList<>();
        lista.add(request.getParameter("webCode"));
        lista.add(request.getParameter("date"));
        lista.add(request.getParameter("title"));
        lista.add(request.getParameter("link"));

        request.setAttribute("fields", lista);
        request.setAttribute("errorRegister", result);
        RequestDispatcher d = request.getRequestDispatcher("view/webViews/webRegister.jsp");
        d.forward(request, response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, FileNotFoundException, ClassNotFoundException, ServletException {
        
        int webCode = Integer.parseInt(request.getParameter("webCode"));
        String result = "";
        String ruta = getServletContext().getRealPath("/WEB-INF/");
        WebDAO helper = new WebDAO(ruta);

                if (helper.delete(webCode) == 1) {
                    result = "Web "+webCode+" delete";
                   
                }else{
                    result = "Web "+webCode+" not delete";
                }
                 listWeb(request, response, result);
    }
    /**
     * show form for modify
     * @param request
     * @param response
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     * @throws ServletException 
     */
    private void updateView(HttpServletRequest request, HttpServletResponse response) throws IOException, FileNotFoundException, ClassNotFoundException, ServletException {
      
        String ruta = getServletContext().getRealPath("/WEB-INF/");

        WebDAO helper = new WebDAO(ruta);
        Web web = null;
        int  webCode = Integer.parseInt(request.getParameter("webCode"));
        try {
            Web n = new Web(webCode);

            web = helper.findOne(n);
            
            List<String> lista = new ArrayList<>();
        
            lista.add(String.valueOf(web.getWebCode()));
            lista.add(web.getDate());
            lista.add(web.getTitle());
            lista.add(web.getLink());
       
        request.setAttribute("fields", lista);
        request.setAttribute("update", "ok"); // para mostrar el boton de update
        
        RequestDispatcher d = request.getRequestDispatcher("view/webViews/webRegister.jsp");
        d.forward(request, response);
        
        } catch (Exception e) {

        }
            HttpSession sesion = request.getSession();
        User u = (User) sesion.getAttribute("user");
        if (u == null) {
            RequestDispatcher d = request.getRequestDispatcher("index.jsp");
            d.forward(request, response);
        } else {
            RequestDispatcher d = request.getRequestDispatcher("NEWS.jsp");
            d.forward(request, response);
        }
        
        
      
    }
    /**
     * modify Web
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ClassNotFoundException 
     */
    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FileNotFoundException, ClassNotFoundException {
      
        request.setCharacterEncoding("UTF-8");
        List<String> fieldOk = null;
        String ruta = getServletContext().getRealPath("/WEB-INF/");
        String result = "";
        boolean isIntWebCode = false;
        boolean okLink = false;
        int webCode=0;
        int oldWebCode=0;
        Web newWeb = new Web();
        
        fieldOk = newWeb.notNull(request, response);

        if (fieldOk.size() == 0) { // validete all fields don´t null
          
            isIntWebCode = ValidationForm.isInteger(request.getParameter("webCode")); // valida que el campo web no tenga espacios
            
            okLink = ValidationForm.isLink(request.getParameter("link")); // valido link

            if (isIntWebCode) {
                webCode = Integer.parseInt(request.getParameter("webCode"));
                oldWebCode = Integer.parseInt(request.getParameter("webCodeUpdate"));
            }

            if (okLink == true && webCode == oldWebCode) {

                newWeb = new Web(Integer.parseInt(request.getParameter("webCode")), request.getParameter("date"), request.getParameter("title"), request.getParameter("link")
                );

                WebDAO helper = new WebDAO(ruta);

                if (helper.update(newWeb) == 1) {

                    result = "<h3>It was modified successfully.</h3>" + result;
                    request.setAttribute("update", "ok");
                    redirectToRegister(request, response, result);

                } else {
                    result = "<h3>At this time we are updating our website. Try it later" + result;
                    request.setAttribute("update", "ok");
                    redirectToRegister(request, response, result);
                }

            } else {
                
                if (!okLink) {
                    result += "<p>Link not valid</p>";
                }
                if (webCode != oldWebCode) {
                    
                    result += "<p>You can not change the web code.</p>";
                }
                request.setAttribute("update", "ok");
                redirectToRegister(request, response, result);
            }

        } else {
            for (String string : fieldOk) {
                result += "<p>" + string + "</p>";
            }
            request.setAttribute("update", "ok");
            this.redirectToRegister(request, response, result);
        }

        
    }
}
