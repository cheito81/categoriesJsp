/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import model.Entity;
import model.Web;

/**
 * This class connects with the table web of the database.
 * @author Jose Gimenez
 * @version 2018/03/18
 */
public class WebDAO implements EntityInterface{
    
     private Properties queries;
   private static String PROPS_FILE;
   private static DataBase dataSource;

    public WebDAO(String path) throws FileNotFoundException, IOException, ClassNotFoundException {
        queries = new Properties();
        PROPS_FILE = path+"/files/web_queries.properties";
        queries.load(new FileInputStream(PROPS_FILE));
        dataSource = DataBase.getInstance(path);
    }
    
   
    public String getQuery(String queryName) {
        return queries.getProperty(queryName);
    }
    
   
    public Web fromResultSet(ResultSet res) {
        Web c=null;
       
        try {
           
           int newCode =  res.getInt("webCode");
           String date =  res.getString("date");
           String title = res.getString("title");
           String link = res.getString("link");
           
            c = new Web(newCode,date , title ,link );
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
  public List<Web> getWeb() throws SQLException{
      
    List<Web> webArray = new ArrayList();
   
    try( Connection conn = dataSource.getConnection();
         PreparedStatement st = conn.prepareStatement(getQuery("SELECT_ALL"));
            ){
       
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
          webArray.add(fromResultSet(rs));
        }
     
    }catch(Exception error2) {
            System.out.println(error2.getMessage());
      dataSource.getLogger().log(Level.SEVERE, null, error2);
        } 
    return webArray;
  }
/**
 * search the database if it exists web code
 * @param webCode web code to search
 * @return 0 if not exist 1 in other case
 */
    public int findWebCode(String webCode) {
        
        int count = 0;

        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(getQuery("FIND_WEB_CODE"));
            st.setString(1, webCode);

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
        Web web = (Web) entity;
        int res = 0;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(getQuery("INSERT"));

            st.setInt(1, web.getWebCode());
            st.setString(2, web.getDate());
            st.setString(3, web.getTitle());
            st.setString(4, web.getLink());

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
    public Web findOne(Entity entity) {
          Web web = (Web) entity;
          Web count = null;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(getQuery("FIND_WEB_CODE"));
            st.setInt(1, web.getWebCode());
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
    public int delete(int webCode) {
        
        int count = 0;

        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(getQuery("DELETE"));
            st.setInt(1, webCode);

            count = st.executeUpdate();
            
        } catch (SQLException ex) {
            dataSource.getLogger().log(Level.SEVERE, null, ex);

        }
        return count;
    }

     @Override
    public int update(Entity entity) {
        Web web = (Web) entity;
        int res = 0;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(getQuery("UPDATE"));

            
            st.setString(1, web.getDate());
            st.setString(2, web.getTitle());
            st.setString(3, web.getLink());
            st.setInt(4, web.getWebCode());
            
            res = st.executeUpdate();

        } catch (SQLException ex) {
            dataSource.getLogger().log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
        return res;
    }
}
