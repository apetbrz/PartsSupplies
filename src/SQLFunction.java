import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/* SQLFunction:
 * an interface which represents an operation performed on an SQL database,
 * which requires user input for more options
 */
public interface SQLFunction {
    void doOperation(Connection conn, Scanner input) throws SQLException;
}