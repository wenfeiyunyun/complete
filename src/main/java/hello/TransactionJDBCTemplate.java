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
public class TransactionJDBCTemplate implements TransactionDao {

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public List<Transaction> getListTransactions(int order_id) {
        String SQL = "select * from webpos_transaction where order_id = ?";

        List <Transaction> transactions = jdbcTemplateObject.query(SQL, new Object[] {order_id},
                new TransactionMapper());
        return transactions;
    }
}