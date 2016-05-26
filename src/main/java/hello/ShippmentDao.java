package hello;

import java.util.List;
import java.util.Date;

/**
 * Created by Richard on 20/04/2016.
 */

public interface ShippmentDao {
    /**
     * This is the method to be used to list down
     * a record from the Student table corresponding
     * to a passed student id.
     */
    public Shippment getShippment(String shipNo);
    public List<Shippment> getListShippments(int shippment_id);
    public int insertNewShippment(String shipNo, float weight, String products, int consumer_id, int customer_id, String modifiedBy, Date ModifiedTime, int enabled, int order_id);
}