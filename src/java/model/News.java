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
public class News implements Entity{

    private int newsCode;
    private String date;
    private String title;
    private String briefDescription;
    private String news;
    private String url;

    public News() {
    }
    public News(int newsCode) {
        this.newsCode = newsCode;
    }

    public News(int newsCode,String date, String title, String briefDescription, String news, String url) {
        this.newsCode = newsCode;
        this.date = date;
        this.title = title;
        this.briefDescription = briefDescription;
        this.news = news;
        this.url = url;
    }

    public int getNewsCode() {
        return newsCode;
    }

    public void setNewsCode(int newsCode) {
        this.newsCode = newsCode;
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

    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    /**
     * comprueba si los campos del registro son nulos
     * @param request servlet request
     * @param response servlet response
     * @return null si todos los campos no son nulos
     */
    public List<String> notNull(HttpServletRequest request, HttpServletResponse response) {
        
        List<String> nulls =  new ArrayList<String>();
        if (request.getParameter("newsCode").isEmpty()) {
            nulls.add("Campo news code vacio");
        }
        
        if (request.getParameter("date").isEmpty()) {
            nulls.add("Campo date vacio");
        }
        if (request.getParameter("title").isEmpty()) {
            nulls.add("Campo title vacio");
        }

        //Validate DNI
        if (request.getParameter("briefDescription").isEmpty()) {
            nulls.add("Campo brief description vacio");
        }
        //Validate address
        if (request.getParameter("news").isEmpty()) {
            nulls.add("Campo news vacio");
        }
        //Validate email
        if (request.getParameter("url").isEmpty()) {
            nulls.add("Campo url vacio");
        }

        return nulls;
    }
    
    
}
