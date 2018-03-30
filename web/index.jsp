<%-- 
    Document   : index
    Created on : Dec 12, 2017, 7:05:14 PM
    Author     : alumne
--%>

<%@page import="model.*"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/index.css">
        <title>Login page</title>
    </head>
    <header>
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header"> <a class="navbar-brand" href="#">BIOTEC-GIMENEZ</a> </div>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href='userController?action=LoginView'><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                    
                </ul>
            </div>
        </nav>
    </header>
    <body>
        <aside>
            <div class="portfolio">

                <div class="row">
                    <%   if (request.getParameter("errorUser") == null) {

                            if (request.getParameter("Login") == null) {
                               
                                if (request.getAttribute("listaCategory") != null) {

                    %>

                                        <%@include file="view/categoryViews/listCategory.jsp"%> 
                                        <%                                } else {
                                                response.sendRedirect("CategoriesController?action=list_category");
                                            }
                    } else {%>

                            <%@include file="view/userViews/login.jsp"%> 
                    <%                               }

                    } else {%>

                            <%@include file="view/userViews/login.jsp"%> 
                    <%                               }

                    %>

                </div>

            </div>
        </aside>              
        <footer>
            <h5>©2018 BIOTEC-GIMENEZ, Theme created by José Giménez</h5> 
        </footer>      
    </body>
</html>
