/**
 * @name: UserDAO.java
 * @author: Jose Gimenez
 * @version: 1.0
 * @description: Comunicates the User class with the DB.
 * @date: 12/03/18
 */
package model.persistence;

import static com.mysql.jdbc.Messages.getString;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.Level;
import model.Entity;

import model.User;

public class UserDAO implements EntityInterface {

    private Properties queries;
    private static String PROPS_FILE;
    private static DataBase dataSource;

    //Constructors
    public UserDAO(String path) throws IOException, ClassNotFoundException {
        queries = new Properties();
        PROPS_FILE = path + "/files/user_queries.properties";
        queries.load(new FileInputStream(PROPS_FILE));
        dataSource = DataBase.getInstance(path);
    }

    public String getQuery(String queryName) {
        return queries.getProperty(queryName);
    }

    public User fromResultSet(ResultSet res) {
        String username = null;
        String password = null;
        String rol = null;
        User c = null;
        try {
            while (res.next()) {
                username = res.getString("name");
                password = res.getString("password");
                rol = res.getString("rol");
            }
            c = new User(username, password, rol);
        } catch (SQLException ex) {
            dataSource.getLogger().log(Level.SEVERE, null, ex);

        }
        return c;
    }

    @Override
    public Collection<Entity> fromResultSetList(ResultSet result) {
        Collection<Entity> list = new ArrayList<>();
        try {
            while (result.next()) {
                Entity entity = fromResultSet(result);
                if (entity != null) {
                    list.add(entity);
                }
            }
        } catch (SQLException ex) {
            dataSource.getLogger().log(Level.SEVERE, null, ex);
        }
        return list;
    }

    /**
     * check if there is a user in the database
     *
     * @param entity user to search
     * @return count null if you can not find the user, user if you find it
     */
    @Override
    public Entity findOne(Entity entity) {
        User user = (User) entity;

        Entity count = null;

        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(getQuery("FIND_LOGIN"));
            st.setString(1, user.getUsername());
            st.setString(2, user.getPassword());
            ResultSet res = st.executeQuery();

            count = fromResultSet(res);

        } catch (SQLException ex) {
            dataSource.getLogger().log(Level.SEVERE, null, ex);

        }
        return count;
    }
/**
 * search dni in database
 * @param dni to search in database
 * @return 1 if find, 0 otherwise.
 */
    public int findDNI(String dni) {

        int count = 0;

        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(getQuery("FIND_DNI"));
            st.setString(1, dni);

            ResultSet res = st.executeQuery();

            while (res.next()) {
                count++;
            }

        } catch (SQLException ex) {
            dataSource.getLogger().log(Level.SEVERE, null, ex);

        }
        return count;
    }
/**
 * find user by username
 * @param userName name to search in database
 * @return count 1 if found, 0 otherwise
 */
    public int findUserName(String userName) {

        int count = 0;

        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(getQuery("FIND_USERNAME"));
            st.setString(1, userName);

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
        User user = (User) entity;
        int res = 0;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(getQuery("INSERT"));

            st.setString(1, user.getUsername());
            st.setString(2, user.getPassword());
            st.setString(3, user.getRol());
            st.setString(4, user.getAddress());
            st.setString(5, user.getMail());
            st.setString(6, user.getDni());

            res = st.executeUpdate();

        } catch (SQLException ex) {
            dataSource.getLogger().log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
        return res;
    }

    @Override
    public int update(Entity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(int newsCode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
