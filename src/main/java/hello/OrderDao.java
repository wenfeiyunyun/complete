package hello;

import java.util.List;
import javax.sql.DataSource;

/**
 * Created by Richard on 20/04/2016.
 */

public interface OrderDao {
    /**
     * This is the method to be used to initialize
     * database resources ie. connection.
     */
    //public void setDataSource(DataSource ds);
    /**
     * This is the method to be used to create
     * a record in the Student table.
     */
   // public void create(String name, int age);
    /**
     * This is the method to be used to list down
     * a record from the Student table corresponding
     * to a passed student id.
     */
    public Order getOrder(int id);
    /**
     * This is the method to be used to list down
     * all the records from the Student table.
     */
    //public List<Order> listOrders();
    /**
     * This is the method to be used to delete
     * a record from the Student table corresponding
     * to a passed student id.
     */
    //public void delete(int id);
    /**
     * This is the method to be used to update
     * a record into the Student table.
     */
    //public void update(int id, int age);
}