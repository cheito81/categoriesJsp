<%-- 
    Document   : index
    Created on : Dec 12, 2017, 7:05:14 PM
    Author     : alumne
--%>

<%@page import="model.User"%>
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
        List<String> lista = null;

        if (request.getAttribute("fields") != null) {
            lista = (List<String>) request.getAttribute("fields");
        }
    %>
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
                <div class="panel panel-default">
                    <div class="panel panel-heading">
                         <%   if (request.getAttribute("update") != null) { %>
                               <h1 class="text-center">Update News</h1>
                            <%  }else{%>
                                <h1 class="text-center">Add News</h1>
                            <%}%>
                    </div>
                    <div class='panel-body'>
                        <form method="post" action="NewsController">
                            <div class="form-group">
                                <label>News Code *:</label>
                               <%   if (request.getAttribute("update") != null) { %>
                               <input type="text" class="form-control"  placeholder="News Code" name="newsCode" value="<%=lista.get(0)%>" readonly=""/>
                                <input class="form-control"  placeholder="News Code" name="newsCodeUpdate" value="<%=lista.get(0)%>" type="hidden"/>
                            <%  }else{%>
                                <input type="text" class="form-control"  placeholder="News Code" name="newsCode" value="<%=lista.get(0)%>"/>
                                <input class="form-control"  placeholder="News Code" name="newsCodeUpdate" value="<%=lista.get(0)%>" type="hidden"/>
                            <%}%>
                                
                            </div>
                            <div class="form-group">
                                <label>Publication Date *:</label>
                                <input type="date" class="form-control"  placeholder="Publication Date" name="date"  value="<%=lista.get(1)%>"/>
                            </div>
                            <div class="form-group">
                                <label>Title *:</label>
                                <input type="text" class="form-control"  placeholder="Title" name="title"  value="<%=lista.get(2)%>"/>
                            </div>
                            <div class="form-group">
                                <label>Brief Description *:</label>
                                <input type="text" class="form-control"  placeholder="Brief Description" name="briefDescription"  value="<%=lista.get(3)%>"/>
                            </div>
                            <div class="form-group">
                                <label>News *:</label>
                                <input type="text" class="form-control"  placeholder="News" name="news"  value="<%=lista.get(4)%>"/>
                            </div>
                            <div class="form-group">
                                <label>URL *:</label>
                                <input type="text" class="form-control"  placeholder="URL" name="url"  value="<%=lista.get(5)%>"/>
                            </div>
                            <label>* Required fields</label>
                            <br>
                            <%
                            if (request.getAttribute("update") != null) { %>
                                <button class='btn btn-success ' type="submit" name="action" value="updateNews"> Update News </button>
                            <%  }else{%>
                                <button class='btn btn-success ' type="submit" name="action" value="addNews"> Add News </button>
                                <button class='btn btn-warning ' type="submit" name="action" value="add_news"> Reset </button>
                            <%}%>
                        
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
        
    </aside>              
    <footer>
        <h5>©2018 BIOTEC-GIMENEZ, Theme created by José Giménez</h5> 
    </footer>      
</body>
</html>