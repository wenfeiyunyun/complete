package hello;

/**
 * Created by Richard on 20/04/2016.
 */

public interface ProductDao {
    /**
     * This is the method to be used to list down
     * a record from the Student table corresponding
     * to a passed student id.
     */
    public Product getProduct(int id);

}