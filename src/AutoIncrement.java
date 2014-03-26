import org.voltdb.VoltProcedure;
import org.voltdb.SQLStmt;
import org.voltdb.VoltType;
import org.voltdb.VoltTable;

/**
 * Created by saumilpatel on 2014/03/26.
 */
public class AutoIncrement extends VoltProcedure {
    private final SQLStmt autoIncrementSelect = new SQLStmt("select next_value from auto_increment where table_name=?");

    private final SQLStmt autoIncrementUpdate = new SQLStmt("update auto_increment set next_value = ? where table_name=?");

    private final SQLStmt autoIncrementInsert = new SQLStmt("insert into auto_increment values (?, ?)");

    public long run(String table){
        // Get next value for orders table, if null use 1
        voltQueueSQL(autoIncrementSelect, "orders");
        VoltTable[] result = voltExecuteSQL();
        Integer nextValueOrders = 1;
        if(result.length>0 && result[0].getRowCount()>0){
            nextValueOrders = (Integer) result[0].fetchRow(0).get(0, VoltType.INTEGER);
        }

        //update auto increment table
        if(nextValueOrders>1){
            voltQueueSQL(autoIncrementUpdate, nextValueOrders+1, "orders");
        }else{
            voltQueueSQL(autoIncrementInsert, "orders", nextValueOrders+1);
        }
        voltExecuteSQL();
        return nextValueOrders;
    }
}
