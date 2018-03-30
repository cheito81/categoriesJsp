
<%
       List<Category> lista = (List<Category>) request.getAttribute("listaCategory");
       
        out.println("<div class='row'>");
        for(Category catg : lista){
            if(session.getAttribute("user")!=null){
               out.println("<div class='col-sm-4'> <a href='"+catg.getCategoryName()+".jsp'> <img src='images/imgCategories/"+catg.getCategoryId()+".jpg' class='mx-auto rounded img-fluid' alt='"+catg.getCategoryName()+"'><p class='text-primary font-weight-bold'>"+catg.getCategoryName()+"<p/></div></a>");
            }else{
                      out.println("<div class='col-sm-4'><img src='images/imgCategories/"+catg.getCategoryId()+".jpg' class='mx-auto rounded img-fluid' alt='"+catg.getCategoryName()+"'><p class='text-primary font-weight-bold'>"+catg.getCategoryName()+"<p/></div>");
            }
      
        }
        out.println("</div>");
        
 
%>