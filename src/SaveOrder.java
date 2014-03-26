/**
 * Created by saumilpatel on 2014/03/26.
 */

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;

public class SaveOrder extends VoltProcedure {
    private final SQLStmt insert = new SQLStmt("insert into orders values (?, ?, ?, ?, ?, ?, ?, ?)");

    private final SQLStmt export = new SQLStmt("insert into orders_export values (?, ?, ?, ?, ?, ?, ?)");

    /**
     * VoltDB procedures are subclass of {@link VoltProcedure} and run implicitly in transaction.
     *
     * @param itemId
     * @param productId
     * @param productCategory
     * @param userId
     * @param userCountry
     * @param userCity
     * @param userAge
     * @return
     * @throws VoltAbortException
     */
    public long run(int orderId, int itemId, int productId, String productCategory, int userId, String userCountry,
                    String userCity, int userAge) throws VoltAbortException {
        //Insert data into orders table and exprt table.
        voltQueueSQL(insert, orderId, itemId, productId, productCategory, userId, userCountry, userCity, userAge);
        voltQueueSQL(export, orderId, productId, productCategory, userId, userCountry, userCity, userAge);
        voltExecuteSQL();

        //procedures must return long, Long, VoltTable or VoltTable[], so return a value
        return orderId;
    }
}
