<%-- 
    Document   : welcome
    Created on : Dec 13, 2017, 7:09:55 PM
    Author     : alumne
--%>


<%@page import="model.*"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/bootstrap.min.css" />
        <link rel="stylesheet" href="css/index.css">
        <title>Login page</title>
    </head>
    <%
        User u = (User) session.getAttribute("user");
        if (u == null) {
            response.sendRedirect("index.jsp");
        }
    %>
    <header>
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header"> <a class="navbar-brand" href="index.jsp">Homepage</a> </div>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="">Hello ${user.getUsername()}!</a></li>
                    <li><a href="userController?action=Logout"> Logout </a></li>
                </ul>
            </div>
        </nav>
    </header>
    <body>
        <aside>

                    <%if (request.getAttribute("listaCategory") != null) {

                    %>

                                        <%@include file="view/categoryViews/listCategory.jsp"%> 
                                        <%                                } else {
                                                response.sendRedirect("CategoriesController?action=list_category");
                                            }%> 
                    
            
        </aside>   
        <footer>
            <h5>©2018 BIOTEC-GIMENEZ, Theme created by José Giménez</h5> 
        </footer>   
    </body>
</html>

<!--end-->