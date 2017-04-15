package hello;

import java.util.Date;

/**
 * Created by Richard on 25/04/2016.
 */
public class Parcel {
    private int     id;
    private String  shipNo;
    private float   weight;
    private float   total;
    private String  carrierTaken;
    private String  comments;
    private String  modified_by;
    private Date    modified_time;

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCarrierTaken() {
        return carrierTaken;
    }

    public void setCarrierTaken(String carrierTaken) {
        this.carrierTaken = carrierTaken;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}

