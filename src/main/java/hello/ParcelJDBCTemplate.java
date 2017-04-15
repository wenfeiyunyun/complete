package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Richard on 22/04/2016.
 */

@Service
public class ParcelJDBCTemplate implements ParcelDao {

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public List<Parcel> getShippmentList() {
        String SQL = "select * from webpos_shippment order by id DESC";
        List <Parcel> parcels = jdbcTemplateObject.query(SQL, new ParcelMapper());
        return parcels;
    }

    public Parcel getParcel(String shipNo) {
        String SQL = "select * from webpos_parcel where  shipNo = ?";
        Parcel parcel = jdbcTemplateObject.queryForObject(SQL, new Object[]{shipNo}, new ParcelMapper());
        return parcel;
    }

    public int insertNewParcel(String shipNo, String weight, String carrierTaken, String comments, String customer_id) {
        int count = 1; // "1" is there is a result
        if(shipNo.isEmpty()) {
            while (count > 0) {
                Utilities util = new Utilities();
                shipNo = util.shippmentID(carrierTaken, shipNo, customer_id);
                String CountSQL = "select count(*) as total from webpos_parcel where shipNo = ?";
                count = jdbcTemplateObject.queryForObject(CountSQL, new Object[]{shipNo}, Integer.class);
            }
        }
        String modified_by = "daifa";
        Date modified_time = Calendar.getInstance().getTime();
        String SQL = "INSERT INTO webpos_parcel (shipNo, weight, carrierTaken, comments, modified_by, modified_time) VALUES (?,?,?,?,?,?,?)";
        int result = jdbcTemplateObject.update(SQL, shipNo, weight, carrierTaken, comments, modified_by, modified_time);
        return result;
    }


    public int deleteParcel(String shipNo) {
        String SQL = "delete from webpos_parcel where id = ?";
        int result = jdbcTemplateObject.update(SQL, shipNo);
        return result;
    }
}