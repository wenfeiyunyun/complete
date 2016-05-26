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
public class ShippmentJDBCTemplate implements ShippmentDao {

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public List<Shippment> getListShippments(int order_id) {
        String SQL = "select * from webpos_shippment where order_id = ?";
        List <Shippment> Shippments = jdbcTemplateObject.query(SQL, new Object[] {order_id},
                new ShippmentMapper());
        return Shippments;
    }

    public Shippment getShippment(String shipNo) {
        String SQL = "select * from webpos_shippment where  shipNo = ?";
        Shippment shippment = jdbcTemplateObject.queryForObject(SQL, new Object[]{shipNo}, new ShippmentMapper());
        return shippment;
    }

    public int insertNewShippment(String shipNo, float weight, String products, int consumer_id, int customer_id, String modified_by, Date modified_time, int enabled, int order_id) {
        String CountSQL = "select count(*) as total from webpos_shippment where shipNo = ?";
        int count = jdbcTemplateObject.queryForObject(CountSQL, new Object[]{shipNo}, Integer.class);

        if(count > 0) return 0;

        String SQL = "INSERT INTO webpos_shippment (shipNo, weight, products, consumer_id, customer_id, modified_by, modified_time, enabled, order_id) VALUES (?,?,?,?,?,?,?,?,?)";
        int result = jdbcTemplateObject.update(SQL, shipNo, weight, products, consumer_id, customer_id, modified_by, modified_time, enabled, order_id);
        return result;
    }
}