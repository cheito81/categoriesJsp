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
@WebServlet(name = "BookController", urlPatterns = {"/BookController"})
public class BookController extends HttpServlet {

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

                case "list_books":
                    listBook(request, response,null);
                    break;
                case "add_book":
                    add_book(request, response);
                    break;
                    case "addBook":
                    registerBook(request, response);
                    break;
                    case "delete":
                    delete(request, response);
                    break;
                     case "update":
                        updateView(request, response);
                    break;
                    case "updateBook":
                        updateBook(request, response);
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
    private void listBook(HttpServletRequest request, HttpServletResponse response, String result) throws ServletException, IOException, FileNotFoundException, ClassNotFoundException {
        String ruta = getServletContext().getRealPath("/WEB-INF/");

        BookDAO helper = new BookDAO(ruta);
        try {
            ArrayList<Book> book = (ArrayList<Book>) helper.getBook();
            request.setAttribute("listaBook", book);
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
            RequestDispatcher d = request.getRequestDispatcher("BOOKS.jsp");
            d.forward(request, response);
        }

    }
 /**
     * Muestra el formulario para añadir una Book
     * @param request recoge los valores de mi formulario
     * @param response redirecciona hacia la página welcome en caso positivo
     * @throws IOException
     * @throws ServletException
     */
    private void add_book(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<String> lista = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            lista.add("");
        }
        request.setAttribute("fields", lista);
        
        RequestDispatcher d = request.getRequestDispatcher("view/bookViews/bookRegister.jsp");
        d.forward(request, response);

    }
    
        /**
     * register a book in the database
     * @param request
     * @param response
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws ServletException
     */
    private void registerBook(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, ServletException {
        request.setCharacterEncoding("UTF-8");
        //recollim i anem validant
        List<String> fieldOk = null;
        String ruta = getServletContext().getRealPath("/WEB-INF/");
        String result = "";
        boolean okBookIsbn = false;
        int existBookName = 1;
        Book newBook = new Book();
        
        fieldOk = newBook.notNull(request, response);

        if (fieldOk.size() == 0) { // validete all fields don´t null
          
            okBookIsbn = ValidationForm.isInteger(request.getParameter("isbn")); // valida que el campo isbn sea un entero

            if (okBookIsbn) {
                BookDAO helper = new BookDAO(ruta);
                existBookName = helper.findBookIsbn(request.getParameter("isbn"));
            }

            if (existBookName == 0 ) {

                newBook = new Book(Integer.parseInt(request.getParameter("isbn")),request.getParameter("title") , request.getParameter("description"), request.getParameter("author"),request.getParameter("date")
                );

                BookDAO helper = new BookDAO(ruta);

                if (helper.insert(newBook) == 1) {

                    result = "<h3>Register was successfully inserted</h3>" + result;
                    this.redirectToRegister(request, response, result);

                } else {
                    result = "<h3>At this time we are updating our website. Try it later</h3>" + result;
                    this.redirectToRegister(request, response, result);
                }

            } else {
                
                 if (okBookIsbn) {
                    if (existBookName==1) {
                        result += "<p>Book isbn already exists.</p>";
                    }
                } else {
                     result += "<p>the isbn of the book has to be a integer.</p>";
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
        lista.add(request.getParameter("isbn"));
        lista.add(request.getParameter("title"));
        lista.add(request.getParameter("description"));
        lista.add(request.getParameter("author"));
        lista.add(request.getParameter("date"));

        request.setAttribute("fields", lista);
        request.setAttribute("errorRegister", result);
        RequestDispatcher d = request.getRequestDispatcher("view/booksViews/bookRegister.jsp");
        d.forward(request, response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, FileNotFoundException, ClassNotFoundException, ServletException {
        
        int isbn = Integer.parseInt(request.getParameter("isbn"));
        String result = "";
        String ruta = getServletContext().getRealPath("/WEB-INF/");
        BookDAO helper = new BookDAO(ruta);

                if (helper.delete(isbn) == 1) {
                    result = "Book "+isbn+" delete";
                   
                }else{
                    result = "Book "+isbn+" not delete";
                }
                 listBook(request, response, result);
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

        BookDAO helper = new BookDAO(ruta);
        Book book = null;
        int  isbn = Integer.parseInt(request.getParameter("isbn"));
        try {
            Book n = new Book(isbn);

            book = helper.findOne(n);
            
            List<String> lista = new ArrayList<>();
        
            lista.add(String.valueOf(book.getIsbn()));
            lista.add(book.getTitle());
            lista.add(book.getDescription());
            lista.add(book.getAuthor());
            lista.add(book.getDate());
       
        request.setAttribute("fields", lista);
        request.setAttribute("update", "ok"); // para mostrar el boton de update
        
        RequestDispatcher d = request.getRequestDispatcher("view/booksViews/bookRegister.jsp");
        d.forward(request, response);
        
        } catch (Exception e) {

        }
            HttpSession sesion = request.getSession();
            User u = (User) sesion.getAttribute("user");
        if (u == null) {
            RequestDispatcher d = request.getRequestDispatcher("index.jsp");
            d.forward(request, response);
        } else {
            RequestDispatcher d = request.getRequestDispatcher("BOOKS.jsp");
            d.forward(request, response);
        }
        
        
      
    }
    /**
     * modify Book
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ClassNotFoundException 
     */
    private void updateBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FileNotFoundException, ClassNotFoundException {
      
        request.setCharacterEncoding("UTF-8");
        List<String> fieldOk = null;
        String ruta = getServletContext().getRealPath("/WEB-INF/");
        String result = "";
        boolean isIntBookCode = false;
        int isbn=0;
        int oldBookCode=0;
        Book newBook = new Book();
        
        fieldOk = newBook.notNull(request, response);

        if (fieldOk.size() == 0) { // validete all fields don´t null
          
            isIntBookCode = ValidationForm.isInteger(request.getParameter("isbn")); // valida que el campo book no tenga espacios
            
            if (isIntBookCode) {
                isbn = Integer.parseInt(request.getParameter("isbn"));
                oldBookCode = Integer.parseInt(request.getParameter("isbnUpdate"));
            }

            if (isbn == oldBookCode) {

                newBook = new Book(Integer.parseInt(request.getParameter("isbn")), request.getParameter("title"), request.getParameter("description"), request.getParameter("author"), request.getParameter("date")
                );

                BookDAO helper = new BookDAO(ruta);

                if (helper.update(newBook) == 1) {

                    result = "<h3>It was modified successfully.</h3>" + result;
                    request.setAttribute("update", "ok");
                    redirectToRegister(request, response, result);

                } else {
                    result = "<h3>At this time we are updating our website. Try it later" + result;
                    request.setAttribute("update", "ok");
                    redirectToRegister(request, response, result);
                }

            } else {
                
                if (isbn != oldBookCode) {
                    
                    result += "<p>You can not change the book isbn.</p>";
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
