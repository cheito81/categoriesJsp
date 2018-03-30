package controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
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
import model.persistence.UserDAO;

/**
 * This class manages the users of my web
 *
 * @author Jose Gimenez
 * @version 2018/01/16
 */
@WebServlet(name = "userController", urlPatterns = {"/userController"})
public class userController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.lang.ClassNotFoundException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
         request.setCharacterEncoding("UTF-8");
        if (request.getParameter("action") != null) {
            String choice = request.getParameter("action");
            switch (choice) {
                case "LoginView":
                    loginView(request, response);
                    break;

                case "Login":
                    loginUser(request, response);
                    break;

                case "Logout":
                    logoutUser(request, response);
                    break;
                case "Form_Register":
                    addUser(request, response);
                    break;
                case "register":
                    register(request, response);
                    break;
                case "delete":
                    // delete(request, response);
                    break;
                case "find":
                    findView(request, response);
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
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(userController.class.getName()).log(Level.SEVERE, null, ex);
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
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(userController.class.getName()).log(Level.SEVERE, null, ex);
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
     * This method logs me against a database
     *
     * @param request pick up the values of my form
     * @param response Redirect to the welcome page if positive
     * @throws IOException
     * @throws ServletException
     */
    private void loginUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ClassNotFoundException {

        String error = null;

        if (!request.getParameter("username").isEmpty() && !request.getParameter("passwd").isEmpty()) {

            String ruta = getServletContext().getRealPath("/WEB-INF/");

            User u = new User(request.getParameter("username"), request.getParameter("passwd"));

            UserDAO userDAO = new UserDAO(ruta);

            User usu = (User) userDAO.findOne(u);


            if (usu.getUsername() != null) {

                HttpSession sesion = request.getSession();
                sesion.setAttribute("user", usu);
                //redirijo a página con información de login exitoso
                response.sendRedirect("welcome.jsp");
            } else {

                error = "<h3>Usuari i/o contrasenya no vàlids</h3>";
                response.sendRedirect("index.jsp?Login=ok&errorUser="+error);
            }
        } else {

            error = "<h3>Has d'omplir tots dos camps</h3>";
            response.sendRedirect("index.jsp?Login=ok&errorUser="+error);
        }

    }

    /**
     * Este método cierra sesion de un usuario
     *
     * @param request recoge los valores de mi formulario
     * @param response redirecciona hacia la página index en caso positivo
     * @throws IOException
     */
    private void logoutUser(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        session.invalidate();
        //una vez cerrada la sesión, me voy a una página
        response.sendRedirect("index.jsp");
        //con una session tú puedes crearla setAttribute, leerla con getAttribute
        //puedes destruirla invalidate, puedes un tiempo de vida, un tipo máximo
        //de inactividad

    }

    /**
     * Muestra el formulario para añadir usuario
     *
     * @param request recoge los valores de mi formulario
     * @param response redirecciona hacia la página welcome en caso positivo
     * @throws IOException
     * @throws ServletException
     */
    private void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<String> lista = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            lista.add("");
        }
        String formAddUser = "ok";
        request.setAttribute("fields", lista);
        RequestDispatcher d = request.getRequestDispatcher("register.jsp");
        d.forward(request, response);

    }

    /**
     * Muestra el formulario para loguearte
     *
     * @param request recoge los valores de mi formulario
     * @param response redirecciona hacia la página welcome en caso positivo
     * @throws IOException
     * @throws ServletException
     */
    private void loginView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String ok = "ok";

        response.sendRedirect("index.jsp?Login=" + ok);

    }

    /**
     * Muestra el formulario para loguearte
     *
     * @param request recoge los valores de mi formulario
     * @param response redirecciona hacia la página welcome en caso positivo
     * @throws IOException
     * @throws ServletException
     */
    private void findView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String ok = "ok";

        response.sendRedirect("welcome.jsp?find=" + ok);

    }



    /**
     * register a user in the database
     * @param request
     * @param response
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws ServletException
     */
    private void register(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, ServletException {
        request.setCharacterEncoding("UTF-8");
        //recollim i anem validant
        List<String> fieldOk = null;
        List<String> lista = new ArrayList<>();

        String ruta = getServletContext().getRealPath("/WEB-INF/");
        String result = "";
        boolean okPassword = false;
        boolean okUser = false;
        boolean okDni = false;
        boolean okMail = false;
        int existDni = 1;
        int existUserName = 1;

        int valid = 0;
        User newUser = new User();
        fieldOk = newUser.notNull(request, response);

        if (fieldOk.size() == 0) { // validete all fields don´t null
            okPassword = newUser.validPassword(request.getParameter("passwd1"), request.getParameter("passwd2")); // valido password

            okUser = newUser.validUser(request.getParameter("username")); // valida que el campo user no tenga espacios

            okDni = newUser.validDni(request.getParameter("dni")); // valido dni

            okMail = newUser.validMail(request.getParameter("mail"));

            if (okDni) {
                UserDAO helper = new UserDAO(ruta);
                existDni = helper.findDNI(request.getParameter("dni"));
            }
            if (okUser) {
                UserDAO helper = new UserDAO(ruta);
                existUserName = helper.findUserName(request.getParameter("username"));
            }

            if (okPassword == true && okUser == true && existDni == 0 && existUserName == 0 && okMail == true) {

                newUser = new User(request.getParameter("username"), request.getParameter("passwd1"), "basic", request.getParameter("mail"), request.getParameter("address"), request.getParameter("dni")
                );

                UserDAO helper = new UserDAO(ruta);

                if (helper.insert(newUser) == 1) {

                    result = "<h3>El registre s'ha inserit exitosament</h3>" + result;
                    this.redirectToRegister(request, response, result);

                } else {
                    result = "<h3>En aquests moments estem actualitzant la nostra web. Intenta-ho més tard</h3>" + result;
                    this.redirectToRegister(request, response, result);
                }

            } else {
                if (!okPassword) {
                    result += "<p>The password must be the same </p>";
                }
             
                if (!okMail) {
                    result += "<p>mail not valid</p>";
                }
               
                 if (okUser) {
                    if (existUserName==1) {
                        result += "<p>user name already exists</p>";
                    }
                } else {
                     result += "<p>User must not contain spaces</p>";
                }
                 
                if (okDni) {
                    if (existDni == 1) {
                        result += "<p>Dni already exists</p>";
                    }
                } else {
                    result += "<p>Dni not valid</p>";
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
        lista.add(request.getParameter("username"));
        lista.add(request.getParameter("passwd1"));
        lista.add(request.getParameter("passwd2"));
        lista.add(request.getParameter("mail"));
        lista.add(request.getParameter("address"));
        lista.add(request.getParameter("dni"));

        request.setAttribute("fields", lista);
        request.setAttribute("errorRegister", result);
        RequestDispatcher d = request.getRequestDispatcher("register.jsp");
        d.forward(request, response);
    }
}
