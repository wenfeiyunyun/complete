package hello;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Richard on 21/04/2016.
 */

public class ConsumerMapper implements RowMapper<Consumer> {
    public Consumer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Consumer consumer = new Consumer();
        consumer.setId(rs.getInt("id"));
        consumer.setName(rs.getString("name"));
        consumer.setPhone(rs.getString("phone"));
        consumer.setPersonal_id(rs.getString("personal_id"));
        consumer.setComment(rs.getString("comment"));
        consumer.setWechat_id(rs.getString("wechat_id"));
        consumer.setCustomer_id(rs.getInt("customer_id"));
        return consumer;
    }
}