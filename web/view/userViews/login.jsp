    
<div class="panel panel-default">
    <div class="panel panel-heading"> 
        <h1 class="text-center">Login</h1>
    </div>
    <div class='panel-body'>
        <form class="form-inline" method="post" action="userController">
            <div class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                <input type="text" class="form-control" name="username" placeholder="User name">
            </div>

            <div class="form-group">
                <label>Password</label>
                <input type="password" class="form-control" name="passwd"/>
            </div>

            <input type="submit" class="btn btn-info" name="action" value="Login"/>
            <button type="submit" class="btn btn-success form-control" name="action" value="Form_Register">Register</button>
        </form>

        <%
            if (request.getParameter("errorUser") != null) {

                String error = request.getParameter("errorUser"); %>
        <br>
        <div class="alert alert-danger text-center">
            <%    out.println(error); %>
        </div>


        <%  }
        %>
    </div>
</div>