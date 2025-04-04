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

        while(true){

            command = interpretUserInput();

            if(command == ApplicationCommand.EXIT_APPLICATION) return 0;
            else if(command == ApplicationCommand.HELP_MESSAGE){
                printHelp();
                continue;
            }

            try{
                command.func.doOperation(this.conn, this.userInput);
            }
            catch(SQLException e){
                System.out.println("SQL Failure " + e.getErrorCode() + ": " + e);
                System.out.println(e.getSQLState());
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
        ApplicationCommand command = null;
        while(true){

            UI.prompt();

            input = userInput.nextLine().split(" ");

            command = switch(input[0]){

                case "help" -> {
                    printHelp();
                    yield ApplicationCommand.HELP_MESSAGE;
                }
                case "list" -> {
                    if(input.length < 2) yield null;
                    switch(input[1]){
                        case "parts": yield ApplicationCommand.LIST_PART;
                        case "suppliers": yield ApplicationCommand.LIST_SUPPLIER;
                        case "catalog": yield ApplicationCommand.LIST_CATALOG_ENTRY;
                        default: yield null;
                    }
                }
                case "add" -> {
                    if(input.length < 2) yield null;
                    switch(input[1]){
                        case "part": yield ApplicationCommand.ADD_PART;
                        case "supplier": yield ApplicationCommand.ADD_SUPPLIER;
                        case "catalog": yield ApplicationCommand.ADD_CATALOG_ENTRY;
                        default: yield null;
                    }
                }
                case "del" -> {
                    if(input.length < 2) yield null;
                    switch(input[1]){
                        case "part": yield ApplicationCommand.DEL_PART;
                        case "supplier": yield ApplicationCommand.DEL_SUPPLIER;
                        case "catalog": yield ApplicationCommand.DEL_CATALOG_ENTRY;
                        default: yield null;
                    }
                }
                case "update" -> {
                    if(input.length < 2) yield null;
                    switch(input[1]){
                        case "cost": yield ApplicationCommand.UPD_CATALOG_ENTRY;
                        default: yield null;
                    }
                }
                case "query" -> {
                    if(input.length < 2) yield null;
                    switch(input[1]) {
                        case "part": yield ApplicationCommand.QUERY_PART;
                        case "supplier":
                        case "cheapest": yield ApplicationCommand.QUERY_CHEAPEST;
                        default:  yield null;
                    }
                }
                case "exit" -> {
                    System.out.println(Lang.GOODBYE_MSG);
                    yield ApplicationCommand.EXIT_APPLICATION;
                }
                default -> {
                    yield null; 
                }
            };

            if(command != null) return command;
            else System.out.println(Lang.INVALID_INPUT);
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
