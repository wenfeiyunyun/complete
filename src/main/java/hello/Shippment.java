package hello;

import java.util.Date;

public class Shippment {
  private int id;
  private String created_by;
  private String modified_by;
  private Date created_time;
  private Date modified_time;
  private int customer_id;
  private int consumer_id;

  private String shipNo;
  private float weight;
  private String products;
  private int enabled;
  private int order_id;

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

  public Date getModified_time() {
    return modified_time;
  }

  public void setModified_time(Date modified_time) {
    this.modified_time = modified_time;
  }

  public int getCustomer_id() {
    return customer_id;
  }

  public void setCustomer_id(int customer_id) {
    this.customer_id = customer_id;
  }

  public int getConsumer_id() {
    return consumer_id;
  }

  public void setConsumer_id(int consumer_id) {
    this.consumer_id = consumer_id;
  }

  public String getShipNo() {
    return shipNo;
  }

  public void setShipNo(String shipNo) {
    this.shipNo = shipNo;
  }

  public float getWeight() {
    return weight;
  }

  public void setWeight(float weight) {
    this.weight = weight;
  }

  public String getProducts() {
    return products;
  }

  public void setProducts(String products) {
    this.products = products;
  }

  public int getEnabled() {
    return enabled;
  }

  public void setEnabled(int enabled) {
    this.enabled = enabled;
  }

  public int getOrder_id() {
    return order_id;
  }

  public void setOrder_id(int order_id) {
    this.order_id = order_id;
  }
}