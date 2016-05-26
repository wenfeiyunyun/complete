package hello;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Richard on 21/04/2016.
 */

public class ProductMapper implements RowMapper<Product> {
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setProduct_name(rs.getString("product_name"));
        product.setBrand_id(rs.getInt("brand_id"));
        product.setCategory_id(rs.getInt("category_id"));
        product.setTotal_inventory(rs.getInt("total_inventory"));
        product.setBarcode(rs.getString("barcode"));
        //product.setDeal_price(rs.get???("product_img"));
        return product;
    }
}








