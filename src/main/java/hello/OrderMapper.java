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
        order.setId(rs.getInt("order_id"));
        order.setTotal(rs.getDouble("total"));
        order.setCreated_by(String.valueOf(rs.getInt("customer_id")));
        order.setModified_by(String.valueOf(rs.getInt("customer_id")));
        order.setFirstname(rs.getString("firstname"));
        order.setLastname(rs.getString("lastname"));
        order.setCustomer_name(rs.getString("firstname")+rs.getString("lastname"));
        order.setModified_time(rs.getDate("date_modified"));
        order.setComment(rs.getString("comment"));
        order.setCustomer_comment(rs.getString("comment"));
        order.setCustomer_id(rs.getInt("customer_id"));
        order.setCreated_time(rs.getDate("date_added"));
        order.setStatus(String.valueOf(rs.getInt("order_status_id")));
        order.setPay_method(rs.getString("payment_code"));
        order.setIs_invoiced(rs.getBoolean(1));
        //order.setCard_price(rs.getString("card_price"));
        try {
            order.setShipperName(rs.getString("firstname") + " " + rs.getString("lastname") );
            order.setWechat_id(rs.getString("wechat_id"));
        }catch(Exception  e) {

        }
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