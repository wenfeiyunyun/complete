package hello;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Richard on 22/04/2016.
 */

@Service
public class OrderJDBCTemplate implements OrderDao {

    @Autowired
    private JdbcTemplate jdbcTemplateObject;
    @Autowired
    private CustomerJDBCTemplate customerJDBCTemplate;

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public Order getOrder(int id) {
        String SQL = "select * from oc_order where order_id = ?";
        Order order = jdbcTemplateObject.queryForObject(SQL, new Object[]{id}, new OrderMapper());
        return order;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    public List<Order> getOrdersSearched(List searchCiteria) {
        String SQL_START = "select * from oc_order ";
        String SQL_END   = "order by order_id DESC Limit 5 "; //Limit 45
        String SQL_CONDITION = "";


        if(searchCiteria.size() >0) {
           for (int i = 0; i < searchCiteria.size(); i++) {
               if(i==0) {
                    if(searchCiteria.get(0).toString().split(":")[0].equals("older_id")) {
                        SQL_CONDITION = " order_id < " + searchCiteria.get(0).toString().split(":")[1] + " ";
                    }else
                        if(searchCiteria.get(0).toString().split(":")[0].equals("newer_id" )) {
                        SQL_CONDITION = " order_id > " + searchCiteria.get(0).toString().split(":")[1] + " ";
                        SQL_END   = " order by order_id ASC Limit 5 "; //Limit 45
                    }else SQL_CONDITION = searchCiteria.get(0).toString().split(":")[0] +" like '%" + searchCiteria.get(0).toString().split(":")[1] + "%' ";
                }
                else SQL_CONDITION = SQL_CONDITION + "and " + searchCiteria.get(i).toString().split(":")[0] +" like '%" + searchCiteria.get(i).toString().split(":")[1] + "%' ";
            }
            SQL_CONDITION = "where " +  SQL_CONDITION;
        }
        //String SQL = "select * from oc_order order by id DESC Limit 4"; //Limit 45
        String SQL = SQL_START + SQL_CONDITION + SQL_END;
        System.out.println("----Listing Orders from greetingsURL with Search Criteria----- SQL: " + SQL );
        List <Order> orders = jdbcTemplateObject.query(SQL, new OrderMapper());
        return orders;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<Order> getListOrders() {
        String SQL = "select * from oc_order order by order_id DESC Limit 20"; //Limit 45
        List <Order> orders = jdbcTemplateObject.query(SQL, new OrderMapper());
        return orders;
    }

    public List<Order> getNumOfOrders(String table, int id) {
        String SQL = "";
        String BaseSQL = "select * from "+table;
        if(id==0) {
            List <Order> orders = getListOrders();
            return orders;
        }else if(id>0) {
            SQL = "select * from("+ BaseSQL + " where order_id > ? order by order_id ASC Limit 25) as a order by order_id Desc Limit 25";
        }else if(id<0){
            id = Math.abs(id);
            SQL = BaseSQL + " where id < ? order by order_id DESC Limit 25";
        }
        List <Order> orders = jdbcTemplateObject.query(SQL, new Object[]{id}, new OrderMapper());
        return orders;
    }

    public void getTables() {
        log.info("Creating tables");
        jdbcTemplateObject.execute("SHOW TABLES IN webpos");
    }

    public Order getShipOrder(int order_id) {
        String SQL = "select * from oc_order where order_id = ?";
        Order order = jdbcTemplateObject.queryForObject(SQL, new Object[]{order_id}, new OrderMapper());
        return order;
    }
    public int insertNewShipOrder(Order order) {
        int order_id = order.getId();
        String created_by = order.getCreated_by();
        Date created_time = order.getCreated_time();
        String modified_by = order.getModified_by();
        String comment = order.getComment();
        String customer_comment = order.getCustomer_comment();

        int customer_id = order.getCustomer_id();
        //int customer_id = 41;
        String customer_name = order.getLastname() + order.getFirstname();
        //String customer_name = "ART--41";

        Date modified_time = order.getModified_time();
        String status = order.getStatus();
        double total = order.getTotal();
        String pay_method = order.getPay_method();
        boolean is_invoiced = order.getIs_invoiced();
        String card_price = order.getCard_price();
        String SQL =       "INSERT INTO webpos_shiporder (id, created_by, created_time, modified_by, comment, customer_comment, customer_id, customer_name, modified_time, status, total, pay_method, is_invoiced, card_price) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        int result = jdbcTemplateObject.update(SQL, order_id, created_by, created_time, modified_by, comment, customer_comment, customer_id, customer_name, modified_time, status, total, pay_method, is_invoiced, card_price);
        return result;
    }

    public boolean isShipOrderExisting(int order_id) {
        String CountSQL = "select count(*) as total from webpos_shiporder where order_id = ?";
        int count = jdbcTemplateObject.queryForObject(CountSQL, new Object[]{order_id}, Integer.class);
        if(count > 0) return true;
        else return false;
    }

    public int updtepOrderOwnerShip(int customer_id, String customer_name, int order_id) {
        //name = name+"-"+phone+"-"+id;
        //String customer_id = customer_name.split("-")[2];
        Customer customer = customerJDBCTemplate.getCustomer(customer_id);
        String SQL =  "Update oc_order" +
                " set" +
                " customer_id=?," +
                " firstname=?," +
                " lastname=?," +
                " email=?," +
                " telephone=?" +
                " where order_id=?";
        int result = jdbcTemplateObject.update(SQL, customer.getCustomer_id(), customer.getFirstname(), customer.getLastname(),
                                                    customer.getEmail(),       customer.getTelephone(), order_id);
        return result;
    }
}
