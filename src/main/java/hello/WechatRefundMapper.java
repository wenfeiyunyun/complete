package hello;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Richard on 21/04/2016.
 */

public class WechatRefundMapper implements RowMapper<WechatRefund> {
    public WechatRefund mapRow(ResultSet rs, int rowNum) throws SQLException {
        WechatRefund wechatRefund = new WechatRefund();
        wechatRefund.setAmount(rs.getInt("amount"));
        wechatRefund.setPartner_refund_id(rs.getString("setPartner_refund_id"));
        wechatRefund.setRefund_id(rs.getString("refund_id"));
        wechatRefund.setProductID(rs.getInt("productID"));
        wechatRefund.setProductQTY(rs.getInt("productQTY"));
        wechatRefund.setResult_code(rs.getString("result_code"));
        wechatRefund.setReturn_code(rs.getString("return_code"));
        wechatRefund.setModified_by(rs.getString("modified_by"));
        wechatRefund.setModified_time(rs.getDate("modified_time"));
        return wechatRefund;
    }
}