package hello;


import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 * Created by Richard on 21/04/2016.
 */

public class OrderMapper implements RowMapper<Order> {
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setTotal_price(rs.getDouble("total_price"));
        order.setCreated_by(rs.getString("created_by"));
        order.setModified_by(rs.getString("modified_by"));
        order.setModified_time(rs.getDate("modified_time"));
        order.setComment(rs.getString("comment"));
        order.setCustomer_comment(rs.getString("customer_comment"));
        order.setCustomer_id(rs.getInt("customer_id"));
        order.setCreated_time(rs.getDate("created_time"));
        order.setStatus(rs.getString("status"));
        order.setPay_method(rs.getString("pay_method"));
        order.setIs_invoiced(rs.getBoolean("is_invoiced"));
        order.setCard_price(rs.getString("card_price"));
        return order;
    }
}

   /*
    private int id;
    private String ;
    private String ;
    private String ;
    private String ;
    private String ;
    private int ;
    private Date ;
    private String ;
    private float ;
    private String ;
    private boolean ;
    private String ;
   */