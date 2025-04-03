import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Application {

    private java.sql.Connection conn;
    private Scanner userInput;

    public Application(String url, String username, String password){

        this.conn = initializeConnection(url, username, password);
        this.userInput = new Scanner(System.in);

    }

    /**
     * run() contains the main loop
     * exit statuses: 
     * 0 = successful exit
     * 1 = invalid state
     * 2 = 
     * @return application exit status code
     */
    public int run(){

        //SQL Connection required!!
        if(this.conn == null) {
            System.out.println("Application Failure: Database Connection Error");
            return 1;
        }

        printWelcome();
        ApplicationCommand command;

        ResultSet result = null;

        while(true){

            command = interpretUserInput();
            if(command == ApplicationCommand.EXIT_APPLICATION) return 0;

            try{
                result = command.func.doOperation(this.conn, this.userInput);
            }
            catch(SQLException e){
                System.out.println("SQL Failure " + e.getErrorCode() + ": " + e);
                System.out.println(e.getSQLState());
            }

            if(result != null){
            }

        }
    }

    private static void printWelcome(){
        System.out.println(Lang.WELCOME);
    }
    private static void printHelp(){
        System.out.println(Lang.HELP_MSG);
    }
    private ApplicationCommand interpretUserInput(){
        String[] input;
        while(true){
            System.out.print(Lang.PROMPT);
            input = userInput.nextLine().split(" ");
            switch(input[0]){
                case "help" -> {
                    printHelp();
                    continue;
                }
                case "list" -> {
                    if(input.length < 2) break;
                    switch(input[1]){
                        case "parts": return ApplicationCommand.LIST_PART;
                        case "suppliers": return ApplicationCommand.LIST_SUPPLIER;
                        case "catalog": return ApplicationCommand.LIST_CATALOG_ENTRY;
                        default:
                    }
                }
                case "add" -> {
                    if(input.length < 2) break;
                    switch(input[1]){
                        case "part": return ApplicationCommand.ADD_PART;
                        case "supplier": return ApplicationCommand.ADD_SUPPLIER;
                        case "catalog": return ApplicationCommand.ADD_CATALOG_ENTRY;
                        default:
                    }
                }
                case "del" -> {
                    if(input.length < 2) break;
                    switch(input[1]){
                        case "part": return ApplicationCommand.DEL_PART;
                        case "supplier": return ApplicationCommand.DEL_SUPPLIER;
                        case "catalog": return ApplicationCommand.DEL_CATALOG_ENTRY;
                        default:
                    }
                }
                case "update" -> {
                    if(input.length < 2) break;

                }
                case "query" -> {
                    if(input.length < 2) break;


                }
                case "exit" -> {
                    System.out.println(Lang.GOODBYE_MSG);
                    return ApplicationCommand.EXIT_APPLICATION;
                }
                case "" -> {
                    continue;
                }
                default -> {}
            }
            System.out.println(Lang.INVALID_INPUT);
        }
    }
    
    private static Connection initializeConnection(String url, String username, String password){

        try {
            return DriverManager.getConnection(url, username, password);
        }
        catch(SQLException e){
            System.out.println("Failed to connect to database!");
            return null;
        }

    }
}
