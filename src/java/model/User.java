/**
 * Class to work with users.
 *
 * @name: User.java
 * @version: 1.0
 * @version: 28/01/18
 */
package model;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import validation.ValidationForm;

/**
 *
 * @author alumne
 */
public class User implements Entity {

    private String username;
    private String password;
    private String rol;
    private String address;
    private String mail;
    private String dni;

    //otros atributos para registrar
    public User() {

    }

    public User(String username) {
        this.username = username;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String rol) {
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    public User(String username, String password, String rol, String address, String mail, String dni) {
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.address = address;
        this.mail = mail;
        this.dni = dni;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

     /**
     * comprueba si los campos del registro son nulos
     * @param request servlet request
     * @param response servlet response
     * @return null si todos los campos no son nulos
     */
    public List<String> notNull(HttpServletRequest request, HttpServletResponse response) {
        
        List<String> nulls =  new ArrayList<String>();
        if (request.getParameter("username").isEmpty()) {
            nulls.add("Campo user vacio");
        }
        
        if (request.getParameter("passwd1").isEmpty()) {
            nulls.add("Campo password vacio");
        }
        if (request.getParameter("passwd2").isEmpty()) {
            nulls.add("Campo Repeat Password vacio");
        }

        //Validate DNI
        if (request.getParameter("dni").isEmpty()) {
            nulls.add("Campo dni vacio");
        }
        //Validate address
        if (request.getParameter("address").isEmpty()) {
            nulls.add("Campo address vacio");
        }
        //Validate email
        if (request.getParameter("mail").isEmpty()) {
            nulls.add("Campo email vacio");
        }

        return nulls;
    }

    /**
     * valida la contraseña del registro
     *
     * @param password1 Password
     * @param password2 Pasword to confirm
     * @return true si la contraseña es correcta
     */
    public boolean validPassword(String password1, String password2) {
        boolean ok = false;
        if (password1.equals(password2)) {
            ok = true;
        }
        return ok;  
    }
    /**
     * valida el mail del registro
     * @param mail to validate
     * @return true si el mail es correcto
     */
    public boolean validMail(String mail) {
       boolean ok = false;

        ok = ValidationForm.isEmail(mail);
        
        return ok;
    }

    /**
     * valida que el campo user no tenga espacios
     *
     * @param user String campo del formulario que no debe contener espacios.
     * @return true si no contiene espacios, false si contiene.
     */
    public boolean validUser(String user) {
        boolean ok = false;

        ok = ValidationForm.withoutSpaces(user);
        return ok;
    }

    public boolean validDni(String dni) {
        boolean ok = false;

        ok = ValidationForm.isDni(dni);// valido que sea un dni 

        return ok;
    }
}
