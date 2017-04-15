package hello;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Richard on 21/04/2016.
 */

public class CustomerMapper implements RowMapper<Customer> {
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomer_id(rs.getInt("customer_id"));
        customer.setFirstname(rs.getString("firstname"));
        customer.setLastname(rs.getString("lastname"));
        customer.setCustomer_name(rs.getString("lastname") + rs.getString("firstname"));
        customer.setEmail(rs.getString("email"));
        customer.setTelephone(rs.getString("telephone"));
        //customer.setPersonal_id(rs.getString("personal_id"));
        //customer.setComment(rs.getString("comment"));
        //customer.setComment(rs.getString("wechat_id"));
        //customer.setAddress(rs.getString("address"));

        return customer;
    }
}