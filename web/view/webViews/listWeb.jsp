<script type="text/javascript">
    function submitForm(webCode) {
      
        if(!confirm("You're sure you want to delete the web with the code "+webCode+" ?")) {return false;}else{window.location.href='WebController?action=delete&webCode='+webCode;};

    }
</script>

<% 
    List<Web> lista = (List<Web>) request.getAttribute("listaWeb");
out.println("<table class='table table-striped'><thead><tr><th class='text-center'> Web Code </th><th class='text-center'> Date </th><th class='text-center'> Title </th><th class='text-center'> Link </th>");
    if (u.getRol().equals("admin")) {
                     out.println("<th class='text-center'><a href='WebController?action=add_web' class='btn btn-success'> add web </a></th>"); 
                  
             }

out.println("</tr> </thead><tbody>"); 
    
    for (Web web : lista) { 
        out.println("<tr class='text-center'>"); 
         out.println("<td>"); 
                 out.println(web.getWebCode());
         out.println("</td>"); 
           out.println("<td>"); 
                 out.println(web.getDate());
         out.println("</td>"); 
           out.println("<td>"); 
                 out.println(web.getTitle());
         out.println("</td>");
           out.println("<td>"); 
                  out.println("<a href='"+web.getLink()+"'> "+web.getTitle()+" </a>");
         out.println("</td>"); 
       
         if (u.getRol().equals("admin")) {
                     out.println("<td>"); 
                     out.print("<div class='btn-group-vertical'>");
                         out.println("<a href='WebController?action=update&webCode="+web.getWebCode()+"' class='btn btn-warning'> update </a>");
                         out.println("<button  onclick='submitForm("+web.getWebCode()+"); return false;' class='btn btn-danger'> delete </button>");
                         out.println("</div>");
                    out.println("</td>"); 
             }
        out.println("</tr>"); 
       } 
       out.println("</table></tbody>");
%>
            <%
                            if (request.getAttribute("confirmations") != null) {

                                String error = (String)request.getAttribute("confirmations"); %>

                        <div class="alert alert-danger text-center">
                            <%    out.println(error); %>
                        </div>
                        <%  }
                        %>
