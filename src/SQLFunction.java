import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public interface SQLFunction {
    void doOperation(Connection conn, Scanner input) throws SQLException;
}