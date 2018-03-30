
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
 * This class connects with the table news of the database.
 * @author Jose Gimenez
 * @version 2018/03/18
 */

public class NewsDAO implements EntityInterface{

     private Properties queries;
   private static String PROPS_FILE;
   private static DataBase dataSource;

    public NewsDAO(String path) throws FileNotFoundException, IOException, ClassNotFoundException {
        queries = new Properties();
        PROPS_FILE = path+"/files/news_queries.properties";
        queries.load(new FileInputStream(PROPS_FILE));
        dataSource = DataBase.getInstance(path);
    }
    
   
    public String getQuery(String queryName) {
        return queries.getProperty(queryName);
    }
    
   
    public News fromResultSet(ResultSet res) {
        News c=null;
       
        try {
           
           int newCode =  res.getInt("newsCode");
           String date =  res.getString("date");
           String title = res.getString("title");
           String briefDescription = res.getString("briefDescription");
           String news = res.getString("news");
           String url = res.getString("url");
           
            c = new News(newCode,date , title,briefDescription ,news ,url );
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
  public List<News> getNews() throws SQLException{
      
    List<News> newsArray = new ArrayList();
   
    try( Connection conn = dataSource.getConnection();
         PreparedStatement st = conn.prepareStatement(getQuery("SELECT_ALL"));
            ){
       
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
          newsArray.add(fromResultSet(rs));
        }
     
    }catch(Exception error2) {
            System.out.println(error2.getMessage());
      dataSource.getLogger().log(Level.SEVERE, null, error2);
        } 
    return newsArray;
  }
/**
 * search the database if it exists news code
 * @param newsCode news code to search
 * @return 0 if not exist 1 in other case
 */
    public int findNewsCode(String newsCode) {
        
        int count = 0;

        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(getQuery("FIND_NEWS_CODE"));
            st.setString(1, newsCode);

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
        News news = (News) entity;
        int res = 0;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(getQuery("INSERT"));

            st.setInt(1, news.getNewsCode());
            st.setString(2, news.getDate());
            st.setString(3, news.getTitle());
            st.setString(4, news.getBriefDescription());
            st.setString(5, news.getNews());
            st.setString(6, news.getUrl());

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
    public News findOne(Entity entity) {
          News news = (News) entity;
          News count = null;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(getQuery("FIND_NEWS_CODE"));
            st.setInt(1, news.getNewsCode());
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
    public int delete(int newsCode) {
        
        int count = 0;

        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(getQuery("DELETE"));
            st.setInt(1, newsCode);

            count = st.executeUpdate();
            
        } catch (SQLException ex) {
            dataSource.getLogger().log(Level.SEVERE, null, ex);

        }
        return count;
    }

     @Override
    public int update(Entity entity) {
        News news = (News) entity;
        int res = 0;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(getQuery("UPDATE"));

            
            st.setString(1, news.getDate());
            st.setString(2, news.getTitle());
            st.setString(3, news.getBriefDescription());
            st.setString(4, news.getNews());
            st.setString(5, news.getUrl());
            st.setInt(6, news.getNewsCode());
            
            res = st.executeUpdate();

        } catch (SQLException ex) {
            dataSource.getLogger().log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
        return res;
    }

}
