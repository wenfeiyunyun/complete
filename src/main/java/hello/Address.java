package hello;

/**
 * Created by Richard on 3/06/2016.
 */
public class Address {
    private int    id;
    private String address;
    private int    consumer_id;
    private String consumer_name;

    public String getConsumer_name() {
        return consumer_name;
    }

    public void setConsumer_name(String consumer_name) {
        this.consumer_name = consumer_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConsumer_id() {
        return consumer_id;
    }

    public void setConsumer_id(int consumer_id) {
        this.consumer_id = consumer_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
