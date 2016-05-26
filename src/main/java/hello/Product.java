package hello;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by Richard on 25/04/2016.
 */
public class Product {
    private int     id;
    private String  product_name;
    private int     brand_id;
    private int     total_inventory;
    private String  barcode;
    private int     category_id;
    private double  selling_price;
    //???private image   product_img;???

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public int getTotal_inventory() {
        return total_inventory;
    }

    public void setTotal_inventory(int total_inventory) {
        this.total_inventory = total_inventory;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public double getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(double selling_price) {
        this.selling_price = selling_price;
    }
}

