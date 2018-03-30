/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jose
 */
public class Web implements Entity{
    private int webCode;
    private String date;
    private String title;
    private String link;

    public Web() {
    }

    public Web(int webCode, String date, String title, String link) {
        this.webCode = webCode;
        this.date = date;
        this.title = title;
        this.link = link;
    }

    public Web(int webCode) {
        this.webCode = webCode;
    }

    public int getWebCode() {
        return webCode;
    }

    public void setWebCode(int webCode) {
        this.webCode = webCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
       /**
     * comprueba si los campos del registro son nulos
     * @param request servlet request
     * @param response servlet response
     * @return null si todos los campos no son nulos
     */
    public List<String> notNull(HttpServletRequest request, HttpServletResponse response) {
        
        List<String> nulls =  new ArrayList<String>();
        if (request.getParameter("webCode").isEmpty()) {
            nulls.add("Campo web code vacio");
        }
        
        if (request.getParameter("date").isEmpty()) {
            nulls.add("Campo date vacio");
        }
        if (request.getParameter("title").isEmpty()) {
            nulls.add("Campo title vacio");
        }

        //Validate email
        if (request.getParameter("link").isEmpty()) {
            nulls.add("Campo link vacio");
        }

        return nulls;
    } 
    
    
}
