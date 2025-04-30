import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/* ApplicationFunctions
 * has all the functions that handle SQL operations.
 * TODO: this could be more thoroughly commented
 */
public class ApplicationFunctions {
    
    /*TODO: replace all println() -> while(result.next()) println() blocks with 
     * printSqlColumnNames() and printSqlResults() with string format constants
    */

    //listPart(): get all parts from parts table
    public static SQLFunction listParts = (conn, input) -> {
        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery(SQLQueries.selectAll("parts"));
        ResultSetMetaData resMeta = result.getMetaData();

        System.out.println(String.format("%-10s %-30s %-30s", resMeta.getColumnLabel(1), resMeta.getColumnLabel(2), resMeta.getColumnLabel(3)));
        while(result.next()){
            System.out.println(String.format("%-10s %-30s %-30s", result.getString(1), result.getString(2), result.getString(3)));
        }
    };

    //listSuppliers(): get all suppliers from suppliers table
    public static SQLFunction listSuppliers = (conn, input) -> {
        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery(SQLQueries.selectAll("suppliers"));
        ResultSetMetaData resMeta = result.getMetaData();

        System.out.println(String.format("%-10s %-30s %-15s", resMeta.getColumnLabel(1), resMeta.getColumnLabel(2), resMeta.getColumnLabel(3)));
        while(result.next()){
            System.out.println(String.format("%-10s %-30s %-15s", result.getString(1), result.getString(2), result.getString(3)));
        }
    };

    //listCatalogEntries(): get all catalog entries from catalog table
    public static SQLFunction listCatalogEntries = (conn, input) -> {
        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery(SQLQueries.selectAll("catalog"));
        ResultSetMetaData resMeta = result.getMetaData();

        System.out.println(String.format("%-10s %-10s %-10s", resMeta.getColumnLabel(1), resMeta.getColumnLabel(2), resMeta.getColumnLabel(3)));
        while(result.next()){
            System.out.println(String.format("%-10s %-10s %10.2f", result.getString(1), result.getString(2), result.getFloat(3)));
        }
    };

    //addPart(): add a part to the parts table
    public static SQLFunction addPart = (conn, input) -> {

        Statement st = conn.createStatement();

        String pid = "";
        String pname = "";
        String pmanf = "";

        System.out.println(Lang.ADD_PART);

        while(true){
            UI.subprompt(Lang.ENTER_PID);
            pid = input.nextLine().toUpperCase();

            if(pid.equals("!")) return;
            if(st.executeQuery(SQLQueries.selectFromWhereColEqVal("pid", "parts", "pid", pid)).next()){
                System.out.println(Lang.PID_EXISTS);
                continue;
            }
            if(pid.length() > 0) break;
        }
        while(true){
            UI.subprompt(Lang.ENTER_PNAME);
            pname = input.nextLine();

            if(pname.equals("!")) return;
            if(pname.length() > 0) break;
        }
        while(true){
            UI.subprompt(Lang.ENTER_PMANF);
            pmanf = input.nextLine();

            if(pmanf.equals("!")) return;
            if(pmanf.length() > 0) break;
        }

        int result = st.executeUpdate(SQLQueries.insertValuesInto("parts", new String[]{pid, pname, pmanf}));

        if(result < 1) System.out.println(Lang.INSERTION_FAILED);
        else System.out.println(Lang.INSERTION_SUCCESS);
    };

    //addSupplier(): add a supplier to the suppliers table
    public static SQLFunction addSupplier = (conn, input) -> {
        
        Statement st = conn.createStatement();

        String sid = "";
        String sname = "";
        String sphone = "";
        
        System.out.println(Lang.ADD_SUPPLIER);

        while(true){
            UI.subprompt(Lang.ENTER_SID);
            sid = input.nextLine().toUpperCase();

            if(sid.equals("!")) return;
            if(st.executeQuery(SQLQueries.selectFromWhereColEqVal("sid", "suppliers", "sid", sid)).next()){
                System.out.println(Lang.SID_EXISTS);
                continue;
            }
            if(sid.length() > 0) break;
        }
        while(true){
            UI.subprompt(Lang.ENTER_SNAME);
            sname = input.nextLine();

            if(sname.equals("!")) return;
            if(sname.length() > 0) break;
        }
        while(true){
            UI.subprompt(Lang.ENTER_SPHONE);
            sphone = input.nextLine();

            if(sphone.equals("!")) return;
            if(sphone.length() > 0) break;
        }

        int result = st.executeUpdate(SQLQueries.insertValuesInto("suppliers", new String[]{sid, sname, sphone}));

        if(result < 1) System.out.println(Lang.INSERTION_FAILED);
        else System.out.println(Lang.INSERTION_SUCCESS);
    };

    //addCatalogEntry(): add a catalog entry to the catalog table
    public static SQLFunction addCatalogEntry = (conn, input) -> {

        Statement st = conn.createStatement();

        String sid = "";
        String pid = "";
        String pcost = "";
        
        System.out.println(Lang.ADD_CATALOG_ENTRY);

        while(true){
            UI.subprompt(Lang.ENTER_SID);
            sid = input.nextLine().toUpperCase();

            if(sid.equals("!")) return;
            
            UI.subprompt(Lang.ENTER_PID);
            pid = input.nextLine().toUpperCase();

            if(pid.equals("!")) return;

            if(st.executeQuery(SQLQueries.selectFromWhereColsEqVals("sid", "catalog", new String[]{"sid", "pid"}, new String[]{sid, pid})).next()){
                System.out.println(Lang.SID_PID_EXISTS);
                continue;
            }
            if(sid.length() > 0 && pid.length() > 0) break;
        }
        while(true){
            UI.subprompt(Lang.ENTER_PCOST);
            pcost = input.nextLine();

            if(pcost.equals("!")) return;
            if(pcost.length() > 0) break;
        }

        int result = st.executeUpdate(SQLQueries.insertValuesInto("catalog", new String[]{sid, pid, pcost}));

        if(result < 1) System.out.println(Lang.INSERTION_FAILED);
        else System.out.println(Lang.INSERTION_SUCCESS);
    };
    
    //deletePart(): delete an existing part from the parts table
    public static SQLFunction deletePart = (conn, input) -> {
        Statement st = conn.createStatement();

        System.out.println(Lang.DEL_PART);

        String pid = "";

        while(true){
            UI.subprompt(Lang.ENTER_PID);
            pid = input.nextLine().toUpperCase();
            if(pid.equals("!")) return;
            if(!st.executeQuery(SQLQueries.selectFromWhereColEqVal("pid", "parts", "pid", pid)).next()){
                System.out.println(Lang.PID_NOT_EXISTS);
                continue;
            }
            if(pid.length() > 0) break;
        }
        
        System.out.println(Lang.CONFIRM_INPUT);
        if(input.nextLine().toLowerCase().charAt(0) != 'y') return;

        int result = st.executeUpdate(SQLQueries.deleteFromWhereColEqVal("parts", "pid", pid));
        if(result < 1) System.out.println(Lang.DELETION_FAILED);
        else System.out.println(Lang.DELETION_SUCCESS);
    };
    
    //deleteSupplier(): delete an exising supplier from the suppliers table
    public static SQLFunction deleteSupplier = (conn, input) -> {
        Statement st = conn.createStatement();

        System.out.println(Lang.DEL_PART);

        String sid = "";

        while(true){
            UI.subprompt(Lang.ENTER_SID);
            sid = input.nextLine().toUpperCase();
            if(sid.equals("!")) return;
            if(!st.executeQuery(SQLQueries.selectFromWhereColEqVal("sid", "suppliers", "sid", sid)).next()){
                System.out.println(Lang.SID_NOT_EXISTS);
                continue;
            }
            if(sid.length() > 0) break;
        }
        
        System.out.println(Lang.CONFIRM_INPUT);
        if(input.nextLine().toLowerCase().charAt(0) != 'y') return;

        int result = st.executeUpdate(SQLQueries.deleteFromWhereColEqVal("suppliers", "sid", sid));
        if(result < 1) System.out.println(Lang.DELETION_FAILED);
        else System.out.println(Lang.DELETION_SUCCESS);
    };
    
    //deleteCatalogEntry(): delete an existing catalog entry from the catalog table
    public static SQLFunction deleteCatalogEntry = (conn, input) -> {
        Statement st = conn.createStatement();

        System.out.println(Lang.DEL_PART);

        String sid = "";
        String pid = "";

        while(true){
            UI.subprompt(Lang.ENTER_SID);
            sid = input.nextLine().toUpperCase();

            if(sid.equals("!")) return;
            
            UI.subprompt(Lang.ENTER_PID);
            pid = input.nextLine().toUpperCase();

            if(pid.equals("!")) return;
            
            if(!st.executeQuery(SQLQueries.selectFromWhereColsEqVals("sid", "catalog", new String[]{"sid", "pid"}, new String[]{sid, pid})).next()){
                System.out.println(Lang.SID_PID_NOT_EXISTS);
                continue;
            }
            if(sid.length() > 0 && pid.length() > 0) break;
        }
        
        System.out.println(Lang.CONFIRM_INPUT);
        if(input.nextLine().toLowerCase().charAt(0) != 'y') return;

        int result = st.executeUpdate(SQLQueries.deleteFromWhereColsEqVals("catalog", new String[]{"sid", "pid"}, new String[]{sid, pid}));
        if(result < 1) System.out.println(Lang.DELETION_FAILED);
        else System.out.println(Lang.DELETION_SUCCESS);
    };

    //updateCatalogEntry(): update an existing catalog entry in the catalog table (change cost of a part at a supplier)
    public static SQLFunction updateCatalogEntry = (conn, input) -> {
        
        Statement st = conn.createStatement();

        String sid = "";
        String pid = "";
        String pcost = "";
        
        System.out.println(Lang.UPD_CATALOG_ENTRY);

        while(true){
            UI.subprompt(Lang.ENTER_SID);
            sid = input.nextLine().toUpperCase();

            if(sid.equals("!")) return;
            
            UI.subprompt(Lang.ENTER_PID);
            pid = input.nextLine().toUpperCase();

            if(pid.equals("!")) return;

            if(!st.executeQuery(SQLQueries.selectFromWhereColsEqVals("sid", "catalog", new String[]{"sid", "pid"}, new String[]{sid, pid})).next()){
                System.out.println(Lang.SID_PID_NOT_EXISTS);
                continue;
            }
            if(sid.length() > 0 && pid.length() > 0) break;
        }
        while(true){
            UI.subprompt(Lang.ENTER_NEW_PCOST);
            pcost = input.nextLine();

            if(pcost.equals("!")) return;
            if(pcost.length() > 0) break;
        }

        int result = st.executeUpdate(SQLQueries.updateSetToValWhereColsEqVals("catalog", "cost", pcost, new String[]{"sid","pid"}, new String[]{sid, pid}));

        if(result < 1) System.out.println(Lang.INSERTION_FAILED);
        else System.out.println(Lang.INSERTION_SUCCESS);
    };
    
    //queryPartSuppliers(): get all suppliers of a given part
    public static SQLFunction queryPartSuppliers = (conn, input) -> {
        
        Statement st = conn.createStatement();

        String pid = "";

        while(true){
            UI.subprompt(Lang.ENTER_PID);
            pid = input.nextLine().toUpperCase();
            if(pid.equals("!")) return;
            
            if(!st.executeQuery(SQLQueries.selectFromWhereColEqVal("pid","parts","pid",pid)).next()){
                System.out.println(Lang.PID_NOT_EXISTS);
                continue;
            }

            if(pid.length() > 0) break;
        }

        //SELECT sid, sname, phone, cost FROM parts JOIN catalog USING (pid) JOIN suppliers USING (sid) WHERE pid='input'
        
        ResultSet result = st.executeQuery(SQLQueries.selectFromWhereColEqVal("sid, sname, phone, cost", SQLQueries.joinUsing(SQLQueries.joinUsing("parts", "catalog", "pid"), "suppliers", "sid"), "pid", pid));
        ResultSetMetaData resMeta = result.getMetaData();

        System.out.println(String.format("%-10s %-30s %-15s %-10s", resMeta.getColumnLabel(1), resMeta.getColumnLabel(2), resMeta.getColumnLabel(3), resMeta.getColumnLabel(4)));
        while(result.next()){
            System.out.println(String.format("%-10s %-30s %-15s %10.2f", result.getString(1), result.getString(2), result.getString(3), result.getFloat(4)));
        }
    };
    
    //queryCheapestPartSupplier(): get cost/supplier of part that is the cheapest for that part
    public static SQLFunction queryCheapestPartSupplier = (conn, input) -> {
        
        Statement st = conn.createStatement();

        String pid = "";

        while(true){
            UI.subprompt(Lang.ENTER_PID);
            pid = input.nextLine().toUpperCase();
            if(pid.equals("!")) return;
            
            if(!st.executeQuery(SQLQueries.selectFromWhereColEqVal("pid","parts","pid",pid)).next()){
                System.out.println(Lang.PID_NOT_EXISTS);
                continue;
            }

            if(pid.length() > 0) break;
        }

        ResultSet cheapestPrice = st.executeQuery(SQLQueries.selectFromWhereColEqVal("MIN(cost)","catalog","pid",pid));
        cheapestPrice.next();
        float cost = cheapestPrice.getFloat(1);
        ResultSet result = st.executeQuery(SQLQueries.selectFromWhereColsEqVals("sid, sname, phone, cost", SQLQueries.joinUsing(SQLQueries.joinUsing("parts", "catalog", "pid"), "suppliers", "sid"), new String[]{"pid", "cost"}, new String[]{pid, ""+cost}));
        ResultSetMetaData resMeta = result.getMetaData();

        System.out.println(String.format("%-10s %-30s %-15s %-10s", resMeta.getColumnLabel(1), resMeta.getColumnLabel(2), resMeta.getColumnLabel(3), resMeta.getColumnLabel(4)));
        while(result.next()){
            System.out.println(String.format("%-10s %-30s %-15s %10.2f", result.getString(1), result.getString(2), result.getString(3), result.getFloat(4)));
        }
    };
}
