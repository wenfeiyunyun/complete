package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Date;

/**
 * Created by Richard on 22/04/2016.
 */

@Service
public class WechatRefundJDBCTemplate implements WechatRefundDao {

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public WechatRefund getWechatRefund(String refund_id) {
        String SQL = "select * from webpos_wechatRefund where refund_id = ?";
        WechatRefund wechatRefund = jdbcTemplateObject.queryForObject(SQL, new Object[]{refund_id}, new WechatRefundMapper());
        return  wechatRefund;
    }

    public List<Address> getListAddresses(String consumer_id) {
        String SQL = "select * from webpos_address where consumer_id = ?";
        List <Address> addresses = jdbcTemplateObject.query(SQL, new Object[]{consumer_id}, new AddressMapper());
        return addresses;
    }

    public int insertNewWechatRefund(int amount, int productID, int productQTY, String refund_id, String partner_refund_id, String modified_by, Date modified_time) {
        String SQL = "INSERT INTO webpos_wechatRefund (amount, productID, productQTY, refund_id, partner_refund_id, modified_by, modified_time) VALUES (?, ?, ?, ?, ?, ?, ?);";
        int result = jdbcTemplateObject.update(SQL, amount, productID, productQTY, refund_id, partner_refund_id, modified_by, modified_time);
        return result;
    }
}