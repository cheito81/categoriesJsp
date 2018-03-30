/**Esta clase conecta con un arichivo
 * @name: DataBase.java
 * @author Jose Gimenez
 * @version 2018/01/16
 */

package model.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public final class DataBase {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String BD_URL = "jdbc:mysql://"+"localhost/categories";
    private static final String USUARI = "userCategories";
    private static final String PASSWORD = "userCategories";
    
    private static DataBase instance = null;
    
    private static Logger logger;

    public Logger getLogger() {
        return logger;
    }
    
    protected DataBase(String path) throws ClassNotFoundException {
        try{
            Class.forName(DRIVER);
            logger = Logger.getLogger(DataBase.class.getName());
            configLogger(path);
        }catch(ClassNotFoundException error){
            
        }
    }
    
    /**
     * <strong>getInstance()</strong>
     * gets an unique instance of DBConnect.
     * @param path file of SQL
     * @return an instance of DBConnect.
     * @throws ClassNotFoundException if database driver cannot be loaded.
     */
    public static DataBase getInstance(String path) throws ClassNotFoundException {
      if(instance == null) {
         instance = new DataBase(path);
      }
      return instance;
    }
    
    /**
     * <strong>getConnection()</strong>
     * establishes a connection to the database.
     * @return Connection to database.
     * @throws SQLException if connection error occurs.
     */
    public Connection getConnection() throws SQLException  {
            return DriverManager.getConnection(BD_URL, USUARI, PASSWORD);
    }
      
    /**
     * <strong>configLogger()</strong>
     * configures looger for database errors.
     * @param path file errors
     */
    public void configLogger(String path)  {
         
        try {
            // create log file manager.
            FileHandler fh = new FileHandler(
                    path +"/logs/app%g.log", //pattern
                    10485760, //limit
                    3, // count
                    true); //append
            fh.setLevel(Level.ALL); // level
            fh.setFormatter(new SimpleFormatter()); //formatter
            //fh.setFormatter(new XMLFormatter()); //formatter
            // add log file manager.
            logger.addHandler(fh);
            // console manager is added automatically.
            // change if necessary the level of reporting.
            //Logger.getGlobal().getHandlers()[0].setLevel(Level.SEVERE);
            //Logger.getGlobal().getHandlers()[0].setLevel(Level.OFF); //turn off logging to console.
            // set the logging level.
            logger.setUseParentHandlers(false);
            logger.setLevel(Level.INFO);
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
