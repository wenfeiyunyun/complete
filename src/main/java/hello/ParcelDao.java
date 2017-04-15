package hello;

        import java.util.List;
        import java.util.Date;

/**
 * Created by Richard on 20/04/2016.
 */

public interface ParcelDao {
    /**
     * This is the method to be used to list down
     * a record from the Student table corresponding
     * to a passed student id.
     */
    public Parcel getParcel(String shipNo);
    //public List<Parcel> getListParcels(int shipNo);
    //public int insertNewParcel(String shipNo, String weight, String carrierTaken, String comments);
}

