package controllers;

import java.io.FileNotFoundException;
import model.persistence.*;
import java.io.IOException;
import java.sql.ResultSet;
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
 *
 * @author Jose Gimenez
 * @version 2018/01/16
 */
@WebServlet(name = "NewsController", urlPatterns = {"/NewsController"})
public class NewsController extends HttpServlet {

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

                case "list_news":
                    listNews(request, response,null);
                    break;
                case "add_news":
                    add_news(request, response);
                    break;
                    case "addNews":
                    registerNews(request, response);
                    break;
                    case "delete":
                    delete(request, response);
                    break;
                     case "update":
                        updateView(request, response);
                    break;
                    case "updateNews":
                        updateNews(request, response);
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
    private void listNews(HttpServletRequest request, HttpServletResponse response, String result) throws ServletException, IOException, FileNotFoundException, ClassNotFoundException {
        String ruta = getServletContext().getRealPath("/WEB-INF/");

        NewsDAO helper = new NewsDAO(ruta);
        try {
            ArrayList<News> news = (ArrayList<News>) helper.getNews();
            request.setAttribute("listaNews", news);
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
            RequestDispatcher d = request.getRequestDispatcher("NEWS.jsp");
            d.forward(request, response);
        }

    }
 /**
     * Muestra el formulario para añadir una News
     * @param request recoge los valores de mi formulario
     * @param response redirecciona hacia la página welcome en caso positivo
     * @throws IOException
     * @throws ServletException
     */
    private void add_news(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<String> lista = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            lista.add("");
        }
        request.setAttribute("fields", lista);
        
        RequestDispatcher d = request.getRequestDispatcher("view/newsViews/newsRegister.jsp");
        d.forward(request, response);

    }
    
        /**
     * register a news in the database
     * @param request
     * @param response
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws ServletException
     */
    private void registerNews(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, ServletException {
        request.setCharacterEncoding("UTF-8");
        //recollim i anem validant
        List<String> fieldOk = null;
        String ruta = getServletContext().getRealPath("/WEB-INF/");
        String result = "";
        boolean okNewsCode = false;
        boolean okLink = false;
        int existNewsName = 1;

        int valid = 0;
        News newNews = new News();
        fieldOk = newNews.notNull(request, response);

        if (fieldOk.size() == 0) { // validete all fields don´t null
          
            okNewsCode = ValidationForm.isInteger(request.getParameter("newsCode")); // valida que el campo news no tenga espacios

            okLink = ValidationForm.isLink(request.getParameter("url")); // valido link

            if (okNewsCode) {
                NewsDAO helper = new NewsDAO(ruta);
                existNewsName = helper.findNewsCode(request.getParameter("newsCode"));
            }

            if (existNewsName == 0 && okLink == true) {

                newNews = new News(Integer.parseInt(request.getParameter("newsCode")), request.getParameter("date"), request.getParameter("title"), request.getParameter("briefDescription"), request.getParameter("news"), request.getParameter("url")
                );

                NewsDAO helper = new NewsDAO(ruta);

                if (helper.insert(newNews) == 1) {

                    result = "<h3>El registre s'ha inserit exitosament</h3>" + result;
                    this.redirectToRegister(request, response, result);

                } else {
                    result = "<h3>En aquests moments estem actualitzant la nostra web. Intenta-ho més tard</h3>" + result;
                    this.redirectToRegister(request, response, result);
                }

            } else {
                
                if (!okLink) {
                    result += "<p>Link not valid</p>";
                }
               
                 if (okNewsCode) {
                    if (existNewsName==1) {
                        result += "<p>News Code already exists.</p>";
                    }
                } else {
                     result += "<p>News Code has to be a integer.</p>";
                }
              
                this.redirectToRegister(request, response, result);
            }

        } else {
            for (String string : fieldOk) {
                result += "<p>" + string + "</p>";
            }

            this.redirectToRegister(request, response, result);
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
        lista.add(request.getParameter("newsCode"));
        lista.add(request.getParameter("date"));
        lista.add(request.getParameter("title"));
        lista.add(request.getParameter("briefDescription"));
        lista.add(request.getParameter("news"));
        lista.add(request.getParameter("url"));

        request.setAttribute("fields", lista);
        request.setAttribute("errorRegister", result);
        RequestDispatcher d = request.getRequestDispatcher("view/newsViews/newsRegister.jsp");
        d.forward(request, response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, FileNotFoundException, ClassNotFoundException, ServletException {
        
        int newsCode = Integer.parseInt(request.getParameter("newsCode"));
        String result = "";
        String ruta = getServletContext().getRealPath("/WEB-INF/");
        NewsDAO helper = new NewsDAO(ruta);

                if (helper.delete(newsCode) == 1) {
                    result = "News "+newsCode+" delete";
                   
                }else{
                    result = "News "+newsCode+" not delete";
                }
                 listNews(request, response, result);
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

        NewsDAO helper = new NewsDAO(ruta);
        News news = null;
        int  newsCode = Integer.parseInt(request.getParameter("newsCode"));
        try {
            News n = new News(newsCode);

            news = helper.findOne(n);
            
            List<String> lista = new ArrayList<>();
        
            lista.add(String.valueOf(news.getNewsCode()));
            lista.add(news.getDate());
            lista.add(news.getTitle());
            lista.add(news.getBriefDescription());
            lista.add(news.getNews());
            lista.add(news.getUrl());
       
        request.setAttribute("fields", lista);
        request.setAttribute("update", "ok"); // para mostrar el boton de update
        
        RequestDispatcher d = request.getRequestDispatcher("view/newsViews/newsRegister.jsp");
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
     * modify News
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ClassNotFoundException 
     */
    private void updateNews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FileNotFoundException, ClassNotFoundException {
      
        request.setCharacterEncoding("UTF-8");
        List<String> fieldOk = null;
        String ruta = getServletContext().getRealPath("/WEB-INF/");
        String result = "";
        boolean isIntNewsCode = false;
        boolean okLink = false;
        int newsCode=0;
        int oldNewsCode=0;
        News newNews = new News();
        
        fieldOk = newNews.notNull(request, response);

        if (fieldOk.size() == 0) { // validete all fields don´t null
          
            isIntNewsCode = ValidationForm.isInteger(request.getParameter("newsCode")); // valida que el campo news no tenga espacios
            
            okLink = ValidationForm.isLink(request.getParameter("url")); // valido link

            if (isIntNewsCode) {
                newsCode = Integer.parseInt(request.getParameter("newsCode"));
                oldNewsCode = Integer.parseInt(request.getParameter("newsCodeUpdate"));
            }

            if (okLink == true && newsCode == oldNewsCode) {

                newNews = new News(Integer.parseInt(request.getParameter("newsCode")), request.getParameter("date"), request.getParameter("title"), request.getParameter("briefDescription"), request.getParameter("news"), request.getParameter("url")
                );

                NewsDAO helper = new NewsDAO(ruta);

                if (helper.update(newNews) == 1) {

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
                if (newsCode != oldNewsCode) {
                    
                    result += "<p>You can not change the news code.</p>";
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
