package hello;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

public class WechatRefund extends WechatPayment{
    private int amount;
    private int productID;
    private int productQTY;
    private String refund_id;
    private String partner_refund_id;
    private String modified_by;
    private Date modified_time;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getProductQTY() {
        return productQTY;
    }

    public void setProductQTY(int productQTY) {
        this.productQTY = productQTY;
    }

    public String getRefund_id() {
        return refund_id;
    }

    public void setRefund_id(String refund_id) {
        this.refund_id = refund_id;
    }

    public String getPartner_refund_id() {
        return partner_refund_id;
    }

    public void setPartner_refund_id(String partner_refund_id) {
        this.partner_refund_id = partner_refund_id;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public Date getModified_time() {
        return modified_time;
    }

    public void setModified_time(Date modified_time) {
        this.modified_time = modified_time;
    }
}
