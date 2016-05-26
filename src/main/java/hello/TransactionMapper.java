package hello;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Richard on 21/04/2016.
 */

public class TransactionMapper implements RowMapper<Transaction> {
    public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
        Transaction trans = new Transaction();
        trans.setId(rs.getInt("id"));
        trans.setQuantity(rs.getInt("quantity"));
        trans.setOrder_id(rs.getInt("order_id"));
        trans.setProduct_id(rs.getInt("product_id"));
        trans.setCreated_by(rs.getString("created_by"));
        trans.setModified_by(rs.getString("modified_by"));
        trans.setModified_time(rs.getDate("modified_time"));
        trans.setCreated_time(rs.getDate("created_time"));
        trans.setDeal_price(rs.getDouble("deal_price"));
        return trans;
    }
}
