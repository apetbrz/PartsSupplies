import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public interface SQLFunction {
    ResultSet doOperation(Connection conn, Scanner input) throws SQLException;
}