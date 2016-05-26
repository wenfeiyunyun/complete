package hello;

import java.util.List;

/**
 * Created by Richard on 20/04/2016.
 */

public interface TransactionDao {
    /**
     * This is the method to be used to list down
     * a record from the Student table corresponding
     * to a passed student id.
     */
    public List<Transaction> getListTransactions(int order_id);
}