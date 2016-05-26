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
public class ProductJDBCTemplate implements ProductDao {

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public Product getProduct(int id) {
        String SQL = "select * from webpos_product where id = ?";

        Product product = jdbcTemplateObject.queryForObject(SQL,
                new Object[]{id}, new ProductMapper());

        return product;
    }
}