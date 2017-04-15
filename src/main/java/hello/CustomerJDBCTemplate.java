package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Richard on 22/04/2016.
 */

@Service
public class CustomerJDBCTemplate implements CustomerDao {

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public List<Customer> getListCustomers() {
        String SQL = "select * from webpos_buyer";
        List <Customer> customers = jdbcTemplateObject.query(SQL, new CustomerMapper());
        return customers;
    }

    public List<Customer> getListCustomers(String keyword) {
        String SQL = "select * from webpos_buyer where name like ?"; //`---------------------------
        keyword = "%"+keyword+"%";
        List <Customer> customers = jdbcTemplateObject.query(SQL, new Object[]{keyword}, new CustomerMapper());
        return customers;
    }

    public Customer getCustomer(int id) {
        String SQL = "select * from oc_customer where customer_id = ?";
        Customer customer = jdbcTemplateObject.queryForObject(SQL, new Object[]{id}, new CustomerMapper());
        return customer;
    }

    public int maxCustomerID() {
        String CountSQL = "select max(id) as total from webpos_buyer";
        int count = jdbcTemplateObject.queryForObject(CountSQL, Integer.class);
        return count;
    }

    public int insertNewCustomer(int id, String name, String phone, String personal_id, String comment, String wechat_id, String address) {
        String SQL_INSERT =  "INSERT INTO webpos_buyer (name, phone, personal_id, comment, wechat_id, address) VALUES (?,?,?,?,?,?)";
        jdbcTemplateObject.update(SQL_INSERT, name, phone, personal_id, comment, wechat_id, address);
        int customer_id = maxCustomerID();
        String SQL_UPDATE =  "UPDATE webpos_buyer SET name = ? WHERE id = ?;";
        //name = name+"-"+wechat_id+"-"+customer_id;
        name = name+"-"+phone+"-"+customer_id;
        jdbcTemplateObject.update(SQL_UPDATE, name, customer_id);
        //updateCustomer(id, name+"||"+customer_id, phone, personal_id, comment, wechat_id, address);
        return customer_id;
    }
    public int updateCustomer(int id, String name, String phone, String personal_id, String comment, String wechat_id, String address) {
        name = name+"-"+phone+"-"+id;
        String SQL =  "Update webpos_buyer" +
                " set" +
                " name=?," +
                " phone=?," +
                " personal_id=?," +
                " comment=?," +
                " wechat_id=?," +
                " address=?" +
                " where id=?";
        int result = jdbcTemplateObject.update(SQL, name, phone, personal_id, comment, wechat_id, address, id);
        return result;
    }
}