package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

/**
 * Created by Richard on 22/04/2016.
 */

@Service
public class ShippmentJDBCTemplate implements ShippmentDao {

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public List<Shippment> getListShippments() {
        String SQL = "select * from webpos_shippment order by id DESC";
        List <Shippment> Shippments = jdbcTemplateObject.query(SQL, new ShippmentMapper());
        return Shippments;
    }

    public List<Shippment> getListShippments(String productType) {
        String SQL = "select * from webpos_shippment where productType = ? order by id DESC";
        List <Shippment> Shippments = jdbcTemplateObject.query(SQL, new Object[] {productType},
                new ShippmentMapper());
        return Shippments;
    }

    public List<Shippment> getListShippments(int order_id) {
        String SQL = "select * from webpos_shippment where order_id = ?";
        List <Shippment> Shippments = jdbcTemplateObject.query(SQL, new Object[] {order_id},
                new ShippmentMapper());
        return Shippments;
    }

    public List<Customer> getListCustomers() {
        String SQL = "select * from webpos_buyer";
        List <Customer> customers = jdbcTemplateObject.query(SQL, new CustomerMapper());
        return customers;
    }

    public Shippment getShippment(String shipNo) {
        String SQL = "select * from webpos_shippment where  shipNo = ?";
        Shippment shippment = jdbcTemplateObject.queryForObject(SQL, new Object[]{shipNo}, new ShippmentMapper());
        return shippment;
    }

    public int insertNewShippment(String shipNo, String weight, String products, String consumer_id, String customer_id, String address_id, String modified_by, Date modified_time, int enabled, int order_id, int printTimes, String productType, String comments, String carrierTaken) {
        int count = 1; // "1" is there is a result
        if(shipNo.isEmpty()) {
            while (count > 0) {
                Utilities util = new Utilities();
                shipNo = util.shippmentID(carrierTaken, shipNo, customer_id);
                String CountSQL = "select count(*) as total from webpos_shippment where shipNo = ?";
                count = jdbcTemplateObject.queryForObject(CountSQL, new Object[]{shipNo}, Integer.class);
            }
        }

        String SQL = "INSERT INTO webpos_shippment (shipNo, weight, products, consumer_id, customer_id, address_id, modified_by, modified_time, enabled, order_id, printTimes, productType, comments, carrierTaken) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        int result = jdbcTemplateObject.update(SQL, shipNo, weight, products, consumer_id, customer_id, address_id, modified_by, modified_time, enabled, order_id, printTimes, productType, comments, carrierTaken);
        return result;
    }

    public int updatePrintTimes(String shipNo) {
        String SQL =  "Update webpos_shippment" +
                " set" +
                " printTimes = printTimes + 1" +
                " where shipNo = ?";
        int result = jdbcTemplateObject.update(SQL, shipNo);
        return result;
    }
    public int deleteShippment(int ship_id) {
        String SQL = "delete from webpos_shippment where id = ?";
        int result = jdbcTemplateObject.update(SQL, ship_id);
        return result;
    }
}