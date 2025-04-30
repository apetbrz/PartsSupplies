import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Application {

    private java.sql.Connection conn;
    private Scanner userInput;

    //constructor: handles creating database connection
    public Application(String url, String username, String password){

        this.conn = getConnection(url, username, password);
        this.userInput = new Scanner(System.in);

    }

    /* run() contains the main loop
     * exit statuses: 
     * 0 = successful exit
     * 1 = error
     */
    public int run(){

        //SQL Connection required!!
        if(this.conn == null) {
            System.out.println("Application Failure: Database Connection Error");
            return 1;
        }

        ApplicationCommand command;

        printWelcome();

        //loop until exit command
        while(true){

            //take appropriate command from input
            command = interpretUserInput();

            //exit command: graceful exit
            if(command == ApplicationCommand.EXIT_APPLICATION) return 0;

            //help command: print help message and skip rest of loop
            else if(command == ApplicationCommand.HELP_MESSAGE){
                printHelp();
                continue;
            }

            //try to run the command on the database connection
            //commands require more information, so pass in userInput as well
            try{
                command.func.doOperation(this.conn, this.userInput);
            }
            //should an SQL error occur, print an error message
            //TODO: descriptive error messages for end user
            catch(SQLException e){
                System.err.println("SQL Failure " + e.getErrorCode() + ": " + e);
                System.err.println(e.getSQLState());
            }
            //any other error? uh oh. uhhhhhh assume we cant continue. exit :P
            catch(Exception e){
                System.err.println(Lang.FATAL_ERROR);
                System.err.println(e.toString());
                return 1;
            }

        }//end main loop
    }

    //helper functions because im lazy
    private static void printWelcome(){
        System.out.println(Lang.WELCOME);
    }
    private static void printHelp(){
        System.out.println(Lang.HELP_MSG);
    }

    /* interpretUserInput():
     * function that handles parsing incoming lines
     * and returns the appropriate command,
     * properly handling invalid commands
     */
    private ApplicationCommand interpretUserInput(){

        //init
        String[] input;
        ApplicationCommand command = null;

        //loop will break once a valid command is interpreted
        while(true){

            //command indicator ("> " currently)
            UI.prompt();

            //get input and split by word
            input = userInput.nextLine().split(" ");

            //match the first word of the input
            command = switch(input[0]){

                //help message
                case "help" -> {
                    yield ApplicationCommand.HELP_MESSAGE;
                }
                //list items in table (requires second word)
                case "list" -> {
                    if(input.length < 2) yield null;
                    switch(input[1]){
                        case "parts": yield ApplicationCommand.LIST_PARTS;
                        case "suppliers": yield ApplicationCommand.LIST_SUPPLIERS;
                        case "catalog": yield ApplicationCommand.LIST_CATALOG_ENTRY;
                        default: yield null;
                    }
                }
                //add item to table (requires second word)
                case "add" -> {
                    if(input.length < 2) yield null;
                    switch(input[1]){
                        case "part": yield ApplicationCommand.ADD_PART;
                        case "supplier": yield ApplicationCommand.ADD_SUPPLIER;
                        case "catalog": yield ApplicationCommand.ADD_CATALOG_ENTRY;
                        default: yield null;
                    }
                }
                //delete item from table (requires second word)
                case "delete" -> {
                    if(input.length < 2) yield null;
                    switch(input[1]){
                        case "part": yield ApplicationCommand.DEL_PART;
                        case "supplier": yield ApplicationCommand.DEL_SUPPLIER;
                        case "catalog": yield ApplicationCommand.DEL_CATALOG_ENTRY;
                        default: yield null;
                    }
                }
                //update cost of an item in the Catalog table (requires second word)
                case "update" -> {
                    if(input.length < 2) yield null;
                    switch(input[1]){
                        case "cost": yield ApplicationCommand.UPD_CATALOG_ENTRY;
                        default: yield null;
                    }
                }
                //query information about a part (suppliers/lowest cost) (requires second word)
                case "query" -> {
                    if(input.length < 2) yield null;
                    switch(input[1]) {
                        case "part": yield ApplicationCommand.QUERY_PART;
                        case "cheapest": yield ApplicationCommand.QUERY_CHEAPEST;
                        default:  yield null;
                    }
                }
                //graceful exit
                case "exit" -> {
                    System.out.println(Lang.GOODBYE_MSG);
                    yield ApplicationCommand.EXIT_APPLICATION;
                }
                //default = invalid
                default -> {
                    yield null; 
                }
            };

            //if valid command, return it!
            if(command != null) return command;
            //otherwise, print an invalid input msg and keep looping
            else System.out.println(Lang.INVALID_INPUT);
        }
    }
    
    /* initializeConnection():
     * handle connecting to the database at the given url
     * with the given username and apssword
     */
    private static Connection getConnection(String url, String username, String password){

        //try to get the connection
        try {
            return DriverManager.getConnection(url, username, password);
        }
        //failure = return null (no connection found)
        catch(SQLException e){
            System.out.println("Failed to connect to database!");
            return null;
        }

    }
}
