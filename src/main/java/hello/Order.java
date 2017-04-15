package hello;

import java.util.*;

public class Order {

  private int id;
  private String created_by;
  private String modified_by;
  private Date created_time;
  private String comment;
  private String customer_comment;
  private int customer_id;
  private Date modified_time;
  private String status;
  private double total;
  private String pay_method;
  private boolean is_invoiced;
  private String card_price;
  private int numberOfCategories;
  private String firstname;
  private String lastname;
  private String customer_name;
  private String shipperName;
  private String wechat_id;

  public String getWechat_id() {
    return wechat_id;
  }

  public void setWechat_id(String wechat_id) {
    this.wechat_id = wechat_id;
  }

  public int getNumberOfCategories() {
    return numberOfCategories;
  }

  public void setNumberOfCategories(int numberOfCategories) {
   this.numberOfCategories = numberOfCategories;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCreated_by() {
    return created_by;
  }

  public void setCreated_by(String created_by) {
    this.created_by = created_by;
  }

  public String getModified_by() {
    return modified_by;
  }

  public void setModified_by(String modified_by) {
    this.modified_by = modified_by;
  }

  public Date getCreated_time() {
    return created_time;
  }

  public void setCreated_time(Date created_time) {
    this.created_time = created_time;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getCustomer_comment() {
    return customer_comment;
  }

  public void setCustomer_comment(String customer_comment) {
    this.customer_comment = customer_comment;
  }

  public int getCustomer_id() {
    return customer_id;
  }

  public void setCustomer_id(int customer_id) {
    this.customer_id = customer_id;
  }

  public Date getModified_time() {
    return modified_time;
  }

  public void setModified_time(Date modified_time) {
    this.modified_time = modified_time;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }

  public String getPay_method() {
    return pay_method;
  }

  public void setPay_method(String pay_method) {
    this.pay_method = pay_method;
  }

  public boolean is_invoiced() {
    return is_invoiced;
  }
  public boolean getIs_invoiced() {
    return is_invoiced;
  }

  public void setIs_invoiced(boolean is_invoiced) {
    this.is_invoiced = is_invoiced;
  }

  public String getCard_price() {
    return card_price;
  }

  public void setCard_price(String card_price) {
    this.card_price = card_price;
  }

  public String getShipperName() {
    return shipperName;
  }

  public void setShipperName(String shipperName) {
    this.shipperName = shipperName;
  }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }
}