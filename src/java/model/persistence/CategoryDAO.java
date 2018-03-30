
package model.persistence;

import static com.mysql.jdbc.Messages.getString;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;
import model.Category;

/**Esta clase conecta con un arichivo
 * @author Jose Gimenez
 * @version 2018/03/18
 */

public class CategoryDAO {

     private Properties queries;
   private static String PROPS_FILE;
   private static DataBase dataSource;

    public CategoryDAO(String path) throws FileNotFoundException, IOException, ClassNotFoundException {
        queries = new Properties();
        PROPS_FILE = path+"/files/category_queries.properties";
        queries.load(new FileInputStream(PROPS_FILE));
        dataSource = DataBase.getInstance(path);
    }
    
   
    public String getQuery(String queryName) {
        return queries.getProperty(queryName);
    }
    
   
    public Category fromResultSet(ResultSet res) {
        String id = null;
        String name = null;
        Category c=null;
        try {
            
                 id = res.getString("id");
                 name =  res.getString("name");
            
            c = new Category(id, name);
        } catch (SQLException ex) {
            dataSource.getLogger().log(Level.SEVERE, null, ex);
            
        }
        return c;
    }

    /**
 * Gets all the categories in the categories table
 * @return categories or null if not find categories
     * @throws java.sql.SQLException
 */
  public List<Category> getCategories() throws SQLException{
      
    List<Category> categories = new ArrayList();
   
    try( Connection conn = dataSource.getConnection();
         PreparedStatement st = conn.prepareStatement("select * from category;");
            ){
       
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
          categories.add(fromResultSet(rs));
        }
     
    }catch(Exception error2) {
            System.out.println(error2.getMessage());
      dataSource.getLogger().log(Level.SEVERE, null, error2);
        } 
    return categories;
  }

}
