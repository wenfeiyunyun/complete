package hello;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Richard on 21/04/2016.
 */

public class AddressMapper implements RowMapper<Address> {
    public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
        Address address = new Address();
        address.setId(rs.getInt("id"));
        address.setAddress(rs.getString("address"));
        address.setConsumer_id(rs.getInt("consumer_id"));
        return address;
    }
}