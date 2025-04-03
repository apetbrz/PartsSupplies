import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class ApplicationFunctions {
    
    public static SQLFunction listPart = (conn, input) -> {
        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery(SQLQueries.selectAll("parts"));
        ResultSetMetaData resMeta = result.getMetaData();

        System.out.println(String.format("%-10s %-30s %-30s", resMeta.getColumnLabel(1), resMeta.getColumnLabel(2), resMeta.getColumnLabel(3)));
        while(result.next()){
            System.out.println(String.format("%-10s %-30s %-30s", result.getString(1), result.getString(2), result.getString(3)));
        }

        return result;
    };
    public static SQLFunction listSuppliers = (conn, input) -> {
        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery(SQLQueries.selectAll("suppliers"));
        ResultSetMetaData resMeta = result.getMetaData();

        System.out.println(String.format("%-10s %-30s %-15s", resMeta.getColumnLabel(1), resMeta.getColumnLabel(2), resMeta.getColumnLabel(3)));
        while(result.next()){
            System.out.println(String.format("%-10s %-30s %-15s", result.getString(1), result.getString(2), result.getString(3)));
        }

        return result;
    };
    public static SQLFunction listCatalogEntries = (conn, input) -> {
        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery(SQLQueries.selectAll("catalog"));
        ResultSetMetaData resMeta = result.getMetaData();

        System.out.println(String.format("%-10s %-10s %-10s", resMeta.getColumnLabel(1), resMeta.getColumnLabel(2), resMeta.getColumnLabel(3)));
        while(result.next()){
            System.out.println(String.format("%-10s %-10s %10.2f", result.getString(1), result.getString(2), result.getFloat(3)));
        }

        return result;
    };

    public static SQLFunction addPart = (conn, input) -> {

        Statement st = conn.createStatement();

        String pid = "";
        String pname = "";
        String pmanf = "";

        System.out.println(Lang.ADD_PART);

        while(true){
            UI.subprompt(Lang.ENTER_PID);
            pid = input.nextLine().toUpperCase();

            if(pid.equals("!")) return null;
            if(st.executeQuery(SQLQueries.selectFromWhereColEqVal("pid", "parts", "pid", pid)).next()){
                System.out.println(Lang.PID_EXISTS);
                continue;
            }
            if(pid.length() > 0) break;
        }
        while(true){
            UI.subprompt(Lang.ENTER_PNAME);
            pname = input.nextLine();

            if(pname.equals("!")) return null;
            if(pname.length() > 0) break;
        }
        while(true){
            UI.subprompt(Lang.ENTER_PMANF);
            pmanf = input.nextLine();

            if(pmanf.equals("!")) return null;
            if(pmanf.length() > 0) break;
        }

        int result = st.executeUpdate(SQLQueries.insertValuesInto("parts", new String[]{pid, pname, pmanf}));

        if(result < 1) System.out.println(Lang.INSERTION_FAILED);
        else System.out.println(Lang.INSERTION_SUCCESS);

        return null;
    };
    public static SQLFunction addSupplier = (conn, input) -> {
        
        Statement st = conn.createStatement();

        String sid = "";
        String sname = "";
        String sphone = "";
        
        System.out.println(Lang.ADD_SUPPLIER);

        while(true){
            UI.subprompt(Lang.ENTER_SID);
            sid = input.nextLine().toUpperCase();

            if(sid.equals("!")) return null;
            if(st.executeQuery(SQLQueries.selectFromWhereColEqVal("sid", "parts", "sid", sid)).next()){
                System.out.println(Lang.SID_EXISTS);
                continue;
            }
            if(sid.length() > 0) break;
        }
        while(true){
            UI.subprompt(Lang.ENTER_SNAME);
            sname = input.nextLine();

            if(sname.equals("!")) return null;
            if(sname.length() > 0) break;
        }
        while(true){
            UI.subprompt(Lang.ENTER_SPHONE);
            sphone = input.nextLine();

            if(sphone.equals("!")) return null;
            if(sphone.length() > 0) break;
        }

        int result = st.executeUpdate(SQLQueries.insertValuesInto("parts", new String[]{sid, sname, sphone}));

        if(result < 1) System.out.println(Lang.INSERTION_FAILED);
        else System.out.println(Lang.INSERTION_SUCCESS);

        return null;
    };
    public static SQLFunction addCatalogEntry = (conn, input) -> {

        Statement st = conn.createStatement();

        String sid = "";
        String pid = "";
        String pcost = "";
        
        System.out.println(Lang.ADD_CATALOG_ENTRY);

        while(true){
            UI.subprompt(Lang.ENTER_SID);
            sid = input.nextLine().toUpperCase();

            if(sid.equals("!")) return null;
            
            UI.subprompt(Lang.ENTER_PID);
            pid = input.nextLine().toUpperCase();

            if(pid.equals("!")) return null;

            if(st.executeQuery(SQLQueries.selectFromWhereColsEqVals("sid", "catalog", new String[]{"sid", "pid"}, new String[]{sid, pid})).next()){
                System.out.println(Lang.SID_PID_EXISTS);
                continue;
            }
            if(sid.length() > 0 && pid.length() > 0) break;
        }
        while(true){
            UI.subprompt(Lang.ENTER_PCOST);
            pcost = input.nextLine();

            if(pcost.equals("!")) return null;
            if(pcost.length() > 0) break;
        }

        int result = st.executeUpdate(SQLQueries.insertValuesInto("catalog", new String[]{sid, pid, pcost}));

        if(result < 1) System.out.println(Lang.INSERTION_FAILED);
        else System.out.println(Lang.INSERTION_SUCCESS);

        return null;
    };

    public static SQLFunction deletePart = (conn, input) -> {
        Statement st = conn.createStatement();

        System.out.println(Lang.DEL_PART);

        String pid = "";

        while(true){
            UI.subprompt(Lang.ENTER_PID);
            pid = input.nextLine().toUpperCase();
            if(pid.equals("!")) return null;
            if(!st.executeQuery(SQLQueries.selectFromWhereColEqVal("pid", "parts", "pid", pid)).next()){
                System.out.println(Lang.PID_NOT_EXISTS);
                continue;
            }
            if(pid.length() > 0) break;
        }
        
        System.out.println(Lang.CONFIRM_INPUT);
        if(input.nextLine().toLowerCase().charAt(0) != 'y') return null;

        int result = st.executeUpdate(SQLQueries.deleteFromWhereColEqVal("parts", "pid", pid));
        if(result < 1) System.out.println(Lang.DELETION_FAILED);
        else System.out.println(Lang.DELETION_SUCCESS);

        return null;
    };
    public static SQLFunction deleteSupplier = (conn, input) -> {
        Statement st = conn.createStatement();

        System.out.println(Lang.DEL_PART);

        String sid = "";

        while(true){
            UI.subprompt(Lang.ENTER_SID);
            sid = input.nextLine().toUpperCase();
            if(sid.equals("!")) return null;
            if(!st.executeQuery(SQLQueries.selectFromWhereColEqVal("sid", "suppliers", "sid", sid)).next()){
                System.out.println(Lang.SID_NOT_EXISTS);
                continue;
            }
            if(sid.length() > 0) break;
        }
        
        System.out.println(Lang.CONFIRM_INPUT);
        if(input.nextLine().toLowerCase().charAt(0) != 'y') return null;

        int result = st.executeUpdate(SQLQueries.deleteFromWhereColEqVal("suppliers", "sid", sid));
        if(result < 1) System.out.println(Lang.DELETION_FAILED);
        else System.out.println(Lang.DELETION_SUCCESS);

        return null;
    };
    public static SQLFunction deleteCatalogEntry = (conn, input) -> {
        Statement st = conn.createStatement();

        System.out.println(Lang.DEL_PART);

        String sid = "";
        String pid = "";

        while(true){
            UI.subprompt(Lang.ENTER_SID);
            sid = input.nextLine().toUpperCase();

            if(sid.equals("!")) return null;
            
            UI.subprompt(Lang.ENTER_PID);
            pid = input.nextLine().toUpperCase();

            if(pid.equals("!")) return null;
            
            if(!st.executeQuery(SQLQueries.selectFromWhereColsEqVals("sid", "catalog", new String[]{"sid", "pid"}, new String[]{sid, pid})).next()){
                System.out.println(Lang.SID_PID_NOT_EXISTS);
                continue;
            }
            if(sid.length() > 0 && pid.length() > 0) break;
        }
        
        System.out.println(Lang.CONFIRM_INPUT);
        if(input.nextLine().toLowerCase().charAt(0) != 'y') return null;

        int result = st.executeUpdate(SQLQueries.deleteFromWhereColsEqVals("catalog", new String[]{"sid", "pid"}, new String[]{sid, pid}));
        if(result < 1) System.out.println(Lang.DELETION_FAILED);
        else System.out.println(Lang.DELETION_SUCCESS);

        return null;
    };
}
