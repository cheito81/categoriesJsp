/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jose
 */
public class Book implements Entity{
    private int isbn;
    private String title;
    private String description;
    private String author;
    private String date;

    public Book(int isbn, String title, String description, String author, String date) {
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.author = author;
        this.date = date;
    }

    public Book() {
    }

    public Book(int isbn) {
        this.isbn = isbn;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
           /**
     * comprueba si los campos del registro son nulos
     * @param request servlet request
     * @param response servlet response
     * @return null si todos los campos no son nulos
     */
    public List<String> notNull(HttpServletRequest request, HttpServletResponse response) {
        
        List<String> nulls =  new ArrayList<String>();
        if (request.getParameter("isbn").isEmpty()) {
            nulls.add("Field isbn empty");
        }
        
        if (request.getParameter("date").isEmpty()) {
            nulls.add("Field date empty");
        }
        if (request.getParameter("title").isEmpty()) {
            nulls.add("Field title empty");
        }

        if (request.getParameter("author").isEmpty()) {
            nulls.add("Field author empty");
        }
        if (request.getParameter("description").isEmpty()) {
            nulls.add("Field description empty");
        }

        return nulls;
    } 

  
   
}
