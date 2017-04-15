package hello;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Richard on 21/04/2016.
 */

public class ParcelMapper implements RowMapper<Parcel> {
    public Parcel mapRow(ResultSet rs, int rowNum) throws SQLException {
        Parcel parcel = new Parcel();
        parcel.setId(rs.getInt("id"));
        parcel.setShipNo(rs.getString("shipNo"));
        parcel.setWeight(rs.getFloat("weight"));
        parcel.setCarrierTaken(rs.getString("carrierTaken"));
        parcel.setComments(rs.getString("comments"));
        parcel.setModified_time(rs.getDate("modified_time"));
        parcel.setModified_by(rs.getString("modified_by"));
        parcel.setTotal(rs.getFloat("total"));
        //product.setDeal_price(rs.get???("product_img"));
        return parcel;
    }
}








