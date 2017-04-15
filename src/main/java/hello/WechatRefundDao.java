package hello;

import java.util.Date;
import java.util.List;

/**
 * Created by Richard on 20/04/2016.
 */

public interface WechatRefundDao {
    /**
     * This is the method to be used to list down
     * a record from the Student table corresponding
     * to a passed student id.
     */
    public WechatRefund getWechatRefund(String refund_id);
    public int insertNewWechatRefund(int amount, int productID, int productQTY, String refund_id, String partner_refund_id, String modified_by, Date modified_time);
}