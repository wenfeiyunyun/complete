package hello;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by Richard on 25/04/2016.
 */
public class Transaction {
    private int id;
    private int quantity;
    private String created_by;
    private Date created_time;
    private String modified_by;
    private int order_id;
    private int product_id;
    private Date modified_time;
    private double deal_price;
    private String product_name;

    public String getProduct_name(int id) {
        return product_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }


    public Date getModified_time() {
        return modified_time;
    }

    public void setModified_time(Date modified_time) {
        this.modified_time = modified_time;
    }

    public double getDeal_price() {
        return deal_price;
    }

    public void setDeal_price(double deal_price) {
        this.deal_price = deal_price;
    }
}

