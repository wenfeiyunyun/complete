package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Richard on 22/04/2016.
 */

@Service
public class AddressJDBCTemplate implements AddressDao {

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public List<Address> getListAddresses() {
        String SQL = "select * from webpos_address";
        List <Address> addresses = jdbcTemplateObject.query(SQL, new AddressMapper());
        return addresses;
    }

    public Address getAddress(String id) {
        String SQL = "select * from webpos_address where id = ?";
        Address address = jdbcTemplateObject.queryForObject(SQL, new Object[]{id}, new AddressMapper());
        return  address;
    }

    public List<Address> getListAddresses(String consumer_id) {
        String SQL = "select * from webpos_address where consumer_id = ?";
        List <Address> addresses = jdbcTemplateObject.query(SQL, new Object[]{consumer_id}, new AddressMapper());
        return addresses;
    }

    public int insertNewAddress(String address, int consumer_id) {
        String SQL = "INSERT INTO webpos_address (address, consumer_id) VALUES (?,?)";
        jdbcTemplateObject.update(SQL, address, consumer_id);
        int address_id = mmaxAddressID();
        return address_id;
    }

    public int updateAddress(int id, String address, int consumer_id) {
        String SQL =  "Update webpos_address" +
                " set" +
                " address=?," +
                " consumer_id=?," +
                " where id=?";
        int result = jdbcTemplateObject.update(SQL, address, consumer_id, id);
        return result;
    }

    public int mmaxAddressID() {
        String CountSQL = "select max(id) as total from webpos_address";
        int count = jdbcTemplateObject.queryForObject(CountSQL, Integer.class);
        return count;
    }

}