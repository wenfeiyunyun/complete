package hello;

import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by Richard on 22/04/2016.
 */

@Service
public class OrderJDBCTemplate implements OrderDao {

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public Order getOrder(int id) {
        String SQL = "select * from webpos_order where id = ?";
        Order order = jdbcTemplateObject.queryForObject(SQL, new Object[]{id}, new OrderMapper());
        return order;
    }

    public List<Order> getListOrders() {
        String SQL = "select * from webpos_order  where id < 29 order by id DESC";
        List <Order> orders = jdbcTemplateObject.query(SQL, new OrderMapper());
        return orders;
    }

    public void getTables() {
        log.info("Creating tables");
        jdbcTemplateObject.execute("SHOW TABLES IN webpos");
    }

    /*
    public List<Student> listStudents() {
        String SQL = "select * from Student";
        List <Student> students = jdbcTemplateObject.query(SQL,
                new StudentMapper());
        return students;
    }

    public void delete(Integer id){
        String SQL = "delete from Student where id = ?";
        jdbcTemplateObject.update(SQL, id);
        System.out.println("Deleted Record with ID = " + id );
        return;
    }

    public void update(Integer id, Integer age){
        String SQL = "update Student set age = ? where id = ?";
        jdbcTemplateObject.update(SQL, age, id);
        System.out.println("Updated Record with ID = " + id );
        return;
    }
    */
}