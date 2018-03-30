<script type="text/javascript">
    function submitForm(isbn) {
      
        if(!confirm("You're sure you want to delete the book with the code "+isbn+" ?")) {return false;}else{window.location.href='BookController?action=delete&isbn='+isbn;};

    }
</script>

<% 
    List<Book> lista = (List<Book>) request.getAttribute("listaBook");
out.println("<table class='table table-striped'><thead><tr><th class='text-center'> Isbn </th><th class='text-center'> Title </th><th class='text-center'> Description </th><th class='text-center'> Author </th><th class='text-center'> Date </th>");
    if (u.getRol().equals("admin")) {
                     out.println("<th class='text-center'><a href='BookController?action=add_book' class='btn btn-success'> add_book </a></th>"); 
                  
             }

out.println("</tr> </thead><tbody>"); 
    
    for (Book book : lista) { 
        out.println("<tr class='text-center'>"); 
         out.println("<td>"); 
                 out.println(book.getIsbn());
         out.println("</td>"); 
           out.println("<td>"); 
                 out.println(book.getTitle());
         out.println("</td>"); 
           out.println("<td>"); 
                 out.println(book.getDescription());
         out.println("</td>"); 
           out.println("<td>"); 
                 out.println(book.getAuthor());
         out.println("</td>"); 
           out.println("<td>"); 
                 out.println(book.getDate());
         out.println("</td>"); 
       
         if (u.getRol().equals("admin")) {
                     out.println("<td>"); 
                     out.print("<div class='btn-group-vertical'>");
                         out.println("<a href='BookController?action=update&isbn="+book.getIsbn()+"' class='btn btn-warning'> update </a>");
                         out.println("<button  onclick='submitForm("+book.getIsbn()+"); return false;' class='btn btn-danger'> delete </button>");
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
