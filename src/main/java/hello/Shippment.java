package hello;

import java.util.Date;

public class Shippment {
  private int id;
  private String created_by;
  private String modified_by;
  private Date created_time;
  private Date modified_time;
  private int customer_id;
  private String consumer_id;
  private String address_id;

  private String shipNo;
  private float weight;
  private String products;
  private int enabled;
  private int order_id;

  private String customer_name;
  private String customer_phone;

  private String consumer_name;
  private String consumer_phone;

  private String consumer_identity;
  private String consumer_address;

  private int printTimes;
  private String productType;
  private String comments;
  private String CarrierTaken;

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

  public String getConsumer_id() {
    return consumer_id;
  }

  public void setConsumer_id(String consumer_id) {
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

  public String getAddress_id() {
    return address_id;
  }

  public void setAddress_id(String address_id) {
    this.address_id = address_id;
  }

  public String getCustomer_name() {
    return customer_name;
  }

  public void setCustomer_name(String customer_name) {
    this.customer_name = customer_name;
  }

  public String getCustomer_phone() {
    return customer_phone;
  }

  public void setCustomer_phone(String customer_phone) {
    this.customer_phone = customer_phone;
  }

  public String getConsumer_name() {
    return consumer_name;
  }

  public void setConsumer_name(String consumer_name) {
    this.consumer_name = consumer_name;
  }

  public String getConsumer_phone() {
    return consumer_phone;
  }

  public void setConsumer_phone(String consumer_phone) {
    this.consumer_phone = consumer_phone;
  }

  public String getConsumer_identity() {
    return consumer_identity;
  }

  public void setConsumer_identity(String consumer_identity) {
    this.consumer_identity = consumer_identity;
  }

  public String getConsumer_address() {
    return consumer_address;
  }

  public void setConsumer_address(String consumer_address) {
    this.consumer_address = consumer_address;
  }

  public int getPrintTimes() {
    return printTimes;
  }

  public void setPrintTimes(int printTimes) {
    this.printTimes = printTimes;
  }

  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public String getCarrierTaken() {
    return CarrierTaken;
  }

  public void setCarrierTaken(String carrierTaken) {
    CarrierTaken = carrierTaken;
  }
}