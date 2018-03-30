/**
 * Class to work with users.
 * @name: Category.java
 * @version: 1.0
 * @version: 28/01/18
 */
package model;

/**
 *
 * @author alumne
 */
public class Category {

    private String id;
    private String name;
    
    //otros atributos para registrar

    public Category(String id) {
        this.id = id;
    }

    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getCategoryId() {
        return id;
    }

    public void setCategoryId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return name;
    }

    public void setCategoryName(String name) {
        this.name = name;
    }

//end   
}
