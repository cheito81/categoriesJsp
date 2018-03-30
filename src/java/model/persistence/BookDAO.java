
package model.persistence;

import static com.mysql.jdbc.Messages.getString;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;

/**
 * This class connects with the table book of the database.
 * @author Jose Gimenez
 * @version 2018/03/18
 */

public class BookDAO implements EntityInterface{

     private Properties queries;
   private static String PROPS_FILE;
   private static DataBase dataSource;

    public BookDAO(String path) throws FileNotFoundException, IOException, ClassNotFoundException {
        queries = new Properties();
        PROPS_FILE = path+"/files/book_queries.properties";
        queries.load(new FileInputStream(PROPS_FILE));
        dataSource = DataBase.getInstance(path);
    }
    
   
    public String getQuery(String queryName) {
        return queries.getProperty(queryName);
    }
    
   
    public Book fromResultSet(ResultSet res) {
        Book c=null;
       
        try {
           
           int isbn =  Integer.parseInt(res.getString("isbn"));
           String date =  res.getString("publicationDate");
           String title = res.getString("title");
           String description = res.getString("description");
           String author = res.getString("author");
           
           
            c = new Book(isbn,title,description ,author ,date );
        } catch (SQLException ex) {
            dataSource.getLogger().log(Level.SEVERE, null, ex);
            
        }
        return c;
    }
    
    //Devuele un java.util.Date desde un String en formato dd-MM-yyyy
    //@param La fecha a convertir a formato date
    //@return Retorna la fecha en formato Date
    public static synchronized java.util.Date deStringToDate(String fecha) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-mm-yyyy");
        java.util.Date fechaEnviar = null;
        try {
            fechaEnviar = formatoDelTexto.parse(fecha);
            return fechaEnviar;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
        
    }

    /**
 * Gets all the categories in the categories table
 * @return categories or null if not find categories
     * @throws java.sql.SQLException
 */
  public List<Book> getBook() throws SQLException{
      
    List<Book> bookArray = new ArrayList();
   
    try( Connection conn = dataSource.getConnection();
         PreparedStatement st = conn.prepareStatement(getQuery("SELECT_ALL"));
            ){
       
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
          bookArray.add(fromResultSet(rs));
        }
     
    }catch(Exception error2) {
            System.out.println(error2.getMessage());
      dataSource.getLogger().log(Level.SEVERE, null, error2);
        } 
    return bookArray;
  }
/**
 * search the database if it exists book code
 * @param isbn book code to search
 * @return 0 if not exist 1 in other case
 */
    public int findBookIsbn(String isbn) {
        
        int count = 0;

        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(getQuery("FIND_BOOK_ISBN"));
            st.setString(1, isbn);

            ResultSet res = st.executeQuery();

            while (res.next()) {
                count++;
            }

        } catch (SQLException ex) {
            dataSource.getLogger().log(Level.SEVERE, null, ex);

        }
        return count;
    }
    
    @Override
    public int insert(Entity entity) {
        Book book = (Book) entity;
        int res = 0;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(getQuery("INSERT"));

            st.setInt(1, book.getIsbn());
            st.setString(2, book.getTitle());
            st.setString(3, book.getDescription());
            st.setString(4, book.getAuthor());
            st.setString(5, book.getDate());

            res = st.executeUpdate();

        } catch (SQLException ex) {
            dataSource.getLogger().log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
        return res;
    }

    @Override
    public Collection<Entity> fromResultSetList(ResultSet result) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Book findOne(Entity entity) {
          Book book = (Book) entity;
          Book count = null;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(getQuery("FIND_BOOK_ISBN"));
            st.setInt(1, book.getIsbn());
            ResultSet res = st.executeQuery();
            while(res.next()){
             count = fromResultSet(res);
            }

        } catch (SQLException ex) {
            dataSource.getLogger().log(Level.SEVERE, null, ex);
        }
        return count;
    }
    
    
     @Override
    public int delete(int isbn) {
        
        int count = 0;

        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(getQuery("DELETE"));
            st.setInt(1, isbn);

            count = st.executeUpdate();
            
        } catch (SQLException ex) {
            dataSource.getLogger().log(Level.SEVERE, null, ex);

        }
        return count;
    }

     @Override
    public int update(Entity entity) {
        Book book = (Book) entity;
        int res = 0;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(getQuery("UPDATE"));

            
            
            st.setString(1, book.getTitle());
            st.setString(2, book.getDescription());
            st.setString(3, book.getAuthor());
            st.setString(4, book.getDate());
            st.setInt(5, book.getIsbn());
            res = st.executeUpdate();

        } catch (SQLException ex) {
            dataSource.getLogger().log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
        return res;
    }

}
