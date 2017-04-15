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
public class ConsumerJDBCTemplate implements ConsumerDao {

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public List<Consumer> getListConsumers() {
        String SQL = "select * from webpos_consumer";
        List <Consumer> consumers = jdbcTemplateObject.query(SQL, new ConsumerMapper());
        return consumers;
    }

    public List<Consumer> getListConsumers(String customer_id) {
        String SQL = "select * from webpos_consumer where customer_id = ? ";
        List <Consumer> consumers = jdbcTemplateObject.query(SQL, new Object[]{customer_id}, new ConsumerMapper());
        return consumers;
    }

    public Consumer getConsumer(int id) {
        String SQL = "select * from webpos_consumer where id = ?";
        Consumer consumer = jdbcTemplateObject.queryForObject(SQL, new Object[]{id}, new ConsumerMapper());
        return consumer;
    }

    public int insertNewConsumer(String name, String phone, String personal_id, String comment, String wechat_id, int customer_id) {
        String SQL =  "INSERT INTO webpos_consumer (name, phone, personal_id, comment, wechat_id, customer_id) VALUES (?,?,?,?,?,?)";
        jdbcTemplateObject.update(SQL, name, phone, personal_id, comment, wechat_id, customer_id);
        int consumer_id = mmaxConsumerID();
        //String SQL_UPDATE =  "UPDATE webpos_consumer SET name = ? WHERE id = ?;";
        //name = name+"-"+wechat_id+"-"+customer_id;
        //jdbcTemplateObject.update(SQL_UPDATE, name, consumer_id);
        //updateCustomer(id, name+"||"+customer_id, phone, personal_id, comment, wechat_id, address);
        return consumer_id;
    }

    public int updateConsumer(int id, String name, String phone, String personal_id, String comment, String wechat_id, int customer_id) {
        String SQL =  "Update webpos_consumer" +
                " set" +
                " name=?," +
                " phone=?," +
                " personal_id=?," +
                " comment=?," +
                " wechat_id=?," +
                " customer_id=?" +
                " where id=?";
        int result = jdbcTemplateObject.update(SQL, name, phone, personal_id, comment, wechat_id, customer_id, id);
        return result;
    }

    public int mmaxConsumerID() {
        String CountSQL = "select max(id) as total from webpos_consumer";
        int count = jdbcTemplateObject.queryForObject(CountSQL, Integer.class);
        return count;
    }
}