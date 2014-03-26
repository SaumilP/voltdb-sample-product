/**
 * Created by saumilpatel on 2014/03/26.
 */

import java.io.IOException;

import org.voltdb.VoltTable;
import org.voltdb.VoltTableRow;
import org.voltdb.VoltType;
import org.voltdb.client.Client;
import org.voltdb.client.ClientFactory;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.NoConnectionsException;
import org.voltdb.client.ProcCallException;

public class Application {
    public static void main(String[] args) throws Exception{

        org.voltdb.client.Client client = ClientFactory.createClient();
        client.createConnection("localhost");

        //TODO modify AutoIncrement procedure to accept int arg to set next value, to avoid
        //calling this get-and-increment every time in bulk load.

        int orderId = getNextValueForTable(client);
        client.callProcedure("SaveOrder", orderId, 1, 101, "CE", 1, "IN", "Mumbai", 25);

        orderId = getNextValueForTable(client);
        client.callProcedure("SaveOrder", orderId, 2, 101, "CE", 2, "IN", "Mumbai", 25);

        orderId = getNextValueForTable(client);
        client.callProcedure("SaveOrder", orderId, 3, 101, "CE", 3, "US", "New York", 34);

        orderId = getNextValueForTable(client);
        client.callProcedure("SaveOrder", orderId, 4, 107, "APP", 4, "IN", "Mumbai", 25);

        orderId = getNextValueForTable(client);
        client.callProcedure("SaveOrder", orderId, 5, 101, "CE", 5, "GB", "London", 23);

        orderId = getNextValueForTable(client);
        client.callProcedure("SaveOrder", orderId, 6, 101, "CE", 6, "IN", "Mumbai", 25);

        orderId = getNextValueForTable(client);
        client.callProcedure("SaveOrder", orderId, 7, 101, "CE", 7, "IN", "Mumbai", 25);

        orderId = getNextValueForTable(client);
        client.callProcedure("SaveOrder", orderId, 8, 103, "APP", 8, "IN", "Mumbai", 25);

        orderId = getNextValueForTable(client);
        client.callProcedure("SaveOrder", orderId, 9, 101, "CE", 9, "IN", "Mumbai", 25);

        orderId = getNextValueForTable(client);
        client.callProcedure("SaveOrder", orderId, 10, 102, "CE", 10, "IN", "Mumbai", 25);

        client.drain();
        client.close();
    }

    private static int getNextValueForTable(Client client) throws NoConnectionsException, IOException, ProcCallException{
        ClientResponse response = client.callProcedure("AutoIncrement", "orders");

        if(response.getStatus()!=ClientResponse.SUCCESS){
            System.out.println("Failed t retrive words");
            System.exit(-1);
        }

        VoltTable[] results = response.getResults();
        if(results.length>0){
            VoltTable result = results[0];
            if(result.getRowCount()>0){
                VoltTableRow row = result.fetchRow(0);
                return ((Integer)row.get(0, VoltType.INTEGER)).intValue();
            }
        }

        return 1;
    }
}
