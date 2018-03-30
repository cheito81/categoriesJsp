
package model.persistence;

import java.sql.ResultSet;
import java.util.Collection;
import model.Entity;

/**
 *
 * @author classDAWBIO
 */
public interface EntityInterface {
    
    public String getQuery(String queryName);
    /**
     * <strong>fromResultSet()</strong>
     * reads current row from resultset and returns as an object.
     * @param res resultset to convert
     * @return object entity from current position of cursor.
     */
    public Entity fromResultSet(ResultSet res);
    
    /**
     * <strong>fromResultSetList()</strong>
     * iterates through resultset and returns list of objects.
     * @param result resultset object to get data from.
     * @return list of entity objects with data contained in resultset.
     */
    public Collection<Entity> fromResultSetList(ResultSet result);
    
    /**
     * <strong>findOne()</strong>
     * looks up in database a object 
     * @param entity to search for Entity
     * @return EntityInterface object found or null if not found.
     */
    public Entity findOne(Entity entity);
    
    /**
     * <strong>insert()</strong>
     * insert in database the object 'entity'
     * @param entity to insert in database
     * @return 1 if inserted successfully, 0 otherwise
     */
    public int insert(Entity entity);
    
     /**
     * <strong>update()</strong>
     * update in database the object 'entity'
     * @param entity to insert in database
     * @return 1 if inserted successfully, 0 otherwise
     */
    public int update(Entity entity);
    
     /**
     * <strong>delete()</strong>
     * delete in database the object 'entity'
     * @param newsCode news code to delete in database
     * @return 1 if delete successfully, 0 otherwise
     */
    public int delete(int newsCode);


}
