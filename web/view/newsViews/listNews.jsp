<script type="text/javascript">
    function submitForm(newsCode) {
      
        if(!confirm("You're sure you want to delete the news with the code "+newsCode+" ?")) {return false;}else{window.location.href='NewsController?action=delete&newsCode='+newsCode;};

    }
</script>

<% 
    List<News> lista = (List<News>) request.getAttribute("listaNews");
out.println("<table class='table table-striped'><thead><tr><th class='text-center'> News Code </th><th class='text-center'> Date </th><th class='text-center'> Title </th><th class='text-center'> Brief Description </th><th class='text-center'> News </th><th class='text-center'> Url </th>");
    if (u.getRol().equals("admin")) {
                     out.println("<th class='text-center'><a href='NewsController?action=add_news' class='btn btn-success'> add_news </a></th>"); 
                  
             }

out.println("</tr> </thead><tbody>"); 
    
    for (News news : lista) { 
        out.println("<tr>"); 
         out.println("<td>"); 
                 out.println(news.getNewsCode());
         out.println("</td>"); 
           out.println("<td>"); 
                 out.println(news.getDate());
         out.println("</td>"); 
           out.println("<td>"); 
                 out.println(news.getTitle());
         out.println("</td>"); 
           out.println("<td>"); 
                 out.println(news.getBriefDescription());
         out.println("</td>"); 
           out.println("<td>"); 
                 out.println(news.getNews());
         out.println("</td>"); 
           out.println("<td>"); 
                 out.println(news.getUrl());
         out.println("</td>"); 
       
         if (u.getRol().equals("admin")) {
                     out.println("<td>"); 
                     out.print("<div class='btn-group-vertical'>");
                         out.println("<a href='NewsController?action=update&newsCode="+news.getNewsCode()+"' class='btn btn-warning'> update </a>");
                         out.println("<button  onclick='submitForm("+news.getNewsCode()+"); return false;' class='btn btn-danger'> delete </button>");
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
