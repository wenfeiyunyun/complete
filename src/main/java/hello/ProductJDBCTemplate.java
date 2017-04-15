package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.*;

/**
 * Created by Richard on 22/04/2016.
 */

@Service
public class ProductJDBCTemplate implements ProductDao {

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public Product getProduct(int id) {
        String SQL = "select * from oc_product where product_id = ?";

        Product product = jdbcTemplateObject.queryForObject(SQL,
                new Object[]{id}, new ProductMapper());

        return product;
    }

    public Product getProductWithBarcode(String barcode) {
        Product product = new Product();
        //barcode = "9300807287323";
        String SQL = "select * from webpos_product where barcode = ?"; //select * from webpos_product where barcode = "123123"
        product = getProduct(barcode, product, SQL);
        return product;
    }

    public Product getProductWithProductName(String product_name) {
        Product product = new Product();
        String SQL = "select * from webpos_product where product_name = ?";
        product = getProduct(product_name, product, SQL);
        return product;
    }

    public Product getProductWithInfo(String barcode, String product_name) {
        Product product = new Product();
        String info = new String();
        String SQL = new String();
        if(barcode != null && !barcode.isEmpty()) {
            SQL = "select * from webpos_product where barcode = ?";
            info = barcode;
        }
        else if(product_name != null && !product_name.isEmpty()) {
            SQL = "select * from webpos_product where product_name = ?";
            info = product_name;
        }
        product = getProduct(info, product, SQL);
        return product;
    }

    private Product getProduct(String product_info, Product product, String SQL) {
        try {
            List<Product> products = jdbcTemplateObject.query(SQL, new Object[]{product_info}, new ProductMapper());
            int numProduct = products.size();
            if(numProduct == 1) product = products.get(0);
                else if(numProduct>0)   System.out.println("----Product with product info:"+product_info+ " has duplicated entries!-----" );
                else if(numProduct==0)  System.out.println("----Product with product info:"+product_info+ " doesnt exist!-----" );
        } catch(Exception e){
            //error handling code
            System.out.println("----Something went wrong!!! Product with product info:"+product_info+ " doesnt exist!-----" );
        }
        return product;
    }

    public int updateProduct(Product product) {
        int id = product.getId();
        String product_name = product.getProduct_name();
        double selling_price = product.getSelling_price();
        int total_inventory = product.getTotal_inventory();

        String SQL =  "Update webpos_product" +
                " set" +
                " product_name=?," +
                " selling_price=?," +
                " total_inventory=?" +
                " where id=?";
        int result = jdbcTemplateObject.update(SQL, product_name, selling_price, total_inventory, id);
        return result;
    }
}