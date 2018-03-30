<%-- 
    Document   : index
    Created on : Dec 12, 2017, 7:05:14 PM
    Author     : alumne
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/index.css">
        <title>Login page</title>
    </head>
    <%
        List<String> lista=null;
         
        if( request.getAttribute("fields")!=null){
         lista = ( List<String> ) request.getAttribute("fields");
        }
    %>
    <header>
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header"> <a class="navbar-brand" href="index.jsp">Homepage</a> </div>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href='userController?action=LoginView'><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                </ul>
            </div>
        </nav>
    </header>
    <body>
        <aside>
            <div class="portfolio">

                <div class="panel panel-default">
                    <div class="panel panel-heading"> 
                        <h1 class="text-center">Add User</h1>
                    </div>
                    <div class='panel-body'>
                        <form method="post" action="userController">
                            <div class="form-group">
                                <label>User Name *:</label>
                                <input type="text" class="form-control"  placeholder="username" name="username" value="<%=lista.get(0)%>"/>
                            </div>
                            <div class="form-group">
                                <label>Password *:</label>
                                <input type="text" class="form-control"  placeholder="passwd1" name="passwd1" value="<%=lista.get(1)%>" />
                            </div>
                            <div class="form-group">
                                <label>Repeat Password *:</label>
                                <input type="text" class="form-control"  placeholder="passwd2" name="passwd2"  value="<%=lista.get(2)%>"/>
                            </div>
                            <div class="form-group">
                                <label>Mail *:</label>
                                <input type="text" class="form-control"  placeholder="mail" name="mail"  value="<%=lista.get(3)%>"/>
                            </div>
                            <div class="form-group">
                                <label>Address *:</label>
                                <input type="text" class="form-control"  placeholder="address" name="address"  value="<%=lista.get(4)%>"/>
                            </div>
                            <div class="form-group">
                                <label>Dni *:</label>
                                <input type="text" class="form-control"  placeholder="dni" name="dni"  value="<%=lista.get(5)%>"/>
                            </div>
                            <label>* Required fields</label>
                            <div class="form-group">
                            <input class='btn btn-success form-control' type="submit" name="action" value="register" />
                            </div>
                            <div class="form-group">
                                <button type="submit" class="btn btn-warning form-control" name="action" value="Form_Register">Reset</button>
                            </div>
                        </form>
                    </div>
                </div>
                             <%
                            if (request.getAttribute("errorRegister") != null) {

                                String error = (String)request.getAttribute("errorRegister"); %>

                        <div class="alert alert-danger text-center">
                            <%    out.println(error); %>
                        </div>
                        <%  }
                        %>
            </div>
        </aside>              
        <footer>
            <h5>©2018 BIOTEC-GIMENEZ, Theme created by José Giménez</h5> 
        </footer>      
    </body>
</html>