import java.util.Arrays;
import java.util.stream.IntStream;

public class SQLQueries {

    public static String selectAll(String from){
        return "SELECT * FROM " + sanitizeInput(from);
    }

    public static String selectFromWhereColEqVal(String column, String table, String target, String value){
        return "SELECT " + column + " FROM " + table + " WHERE " + target + "='" + sanitizeInput(value) + "'";
    }

    public static String selectFromWhereColsEqVals(String column, String table, String[] targets, String[] values){
        if(targets.length != values.length) return "";

        String[] valuesArray = Arrays.stream(values).map(SQLQueries::sanitizeInput).toArray(String[]::new);

        String[] where = IntStream.range(0, targets.length).mapToObj((n) -> {
            return targets[n] + "='" + sanitizeInput(valuesArray[n]) + "'";
        }).toArray(String[]::new);
        
        return "SELECT " + column + " FROM " + table + " WHERE " + String.join(" AND ", where);
    }

    public static String insertValuesInto(String table, String[] values){
        String[] valuesArray = Arrays.stream(values).map(SQLQueries::sanitizeInput).toArray(String[]::new);
        
        String valuesString = "'" + String.join("', '", valuesArray) + "'";
        
        return "INSERT INTO " + table + " VALUES(" + valuesString + ")";
    }

    public static String deleteFromWhereColEqVal(String table, String target, String val){
        return "DELETE FROM " + table + " WHERE " + target + "='" + sanitizeInput(val) + "'";
    }

    public static String deleteFromWhereColsEqVals(String table, String[] targets, String[] values){
        if(targets.length != values.length) return "";

        String[] valuesArray = Arrays.stream(values).map(SQLQueries::sanitizeInput).toArray(String[]::new);

        String[] where = IntStream.range(0, targets.length).mapToObj((n) -> {
            return targets[n] + "='" + sanitizeInput(valuesArray[n]) + "'";
        }).toArray(String[]::new);
        
        return "DELETE FROM " + table + " WHERE " + String.join(" AND ", where);
    }

    //prevent SQL injection attacks by escaping single quotes
    private static String sanitizeInput(String str){
        return str.replace("'","\\'");
    }
}
