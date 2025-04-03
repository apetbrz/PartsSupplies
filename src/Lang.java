public class Lang {
    public static final String WELCOME = """
        \nPetroff Parts and Supplies
            Database Solution
            v.0.1.0

        enter 'help' for commands
        """;
    public static final String HELP_MSG = """
    .--------------------------------------------------------------------------.
    |                                                                          |
    |   Command:            Action:                                            |
    |                                                                          |
    |   list [option]       List all of [option]                               |
    |                           "parts" or "suppliers" or "catalog" (entries)  |
    |   add [option]        Create new [option]:                               |
    |                           "part" or "supplier" or "catalog" (entry)      |
    |   del [option]        Delete existing [option]:                          |
    |                           "part" or "supplier" or "catalog" (entry)      |
    |   update [option]     Update existing [option]:                          |
    |                           "part or "supplier" or "catalog" (entry)       |
    |   query [option]      Get info about [option]:                           |
    |                           "part" or "supplier"                           |
    |   help                Show this menu                                     |
    |   exit                Exit the program                                   |
    |                                                                          |
    `--------------------------------------------------------------------------`""";
    public static final String PROMPT = "> ";
    public static final String SUBPROMPT = "--> ";

    public static final String ADD_PART = "Adding new part... (cancel with '!')";
    public static final String ADD_SUPPLIER = "Adding new supplier... (cancel with '!')";
    public static final String ADD_CATALOG_ENTRY = "Adding new catalog entry... (cancel with '!')";
    public static final String DEL_PART = "Deleting part... (cancel with '!')";
    public static final String DEL_SUPPLIER = "Deleting supplier... (cancel with '!')";
    public static final String DEL_CATALOG_ENTRY = "Deleting catalog entry... (cancel with '!')";
    public static final String ENTER_PID = "Enter Part ID:";
    public static final String ENTER_PNAME = "Enter Part Name:";
    public static final String ENTER_PMANF = "Enter Part Manufacturer:";
    public static final String ENTER_PCOST = "Enter Part Cost:";
    public static final String ENTER_SID = "Enter Supplier ID:";
    public static final String ENTER_SNAME = "Enter Supplier Name:";
    public static final String ENTER_SPHONE = "Enter Supplier Phone Number:";

    public static final String CONFIRM_INPUT = "Are you sure you want to do this? (y/N)";
    
    public static final String INSERTION_SUCCESS = "Success!";
    public static final String DELETION_SUCCESS = "Success!";
    public static final String GOODBYE_MSG = "Goodbye!";
    
    public static final String INVALID_INPUT = "Invalid input! Type 'help' for help!";
    public static final String PID_EXISTS = "Part ID already exists!";
    public static final String PID_NOT_EXISTS = "Part ID doesn't exist!";
    public static final String SID_EXISTS = "Supplier ID already exists!";
    public static final String SID_NOT_EXISTS = "Supplier ID doesn't exist!";
    public static final String SID_PID_EXISTS = "This Supplier ID already supplies this Part ID!";
    public static final String SID_PID_NOT_EXISTS = "This Supplier ID doesn't supply this Part ID!";
    public static final String INSERTION_FAILED = "Insertion failed! No changes made.";
    public static final String DELETION_FAILED = "Deletion failed! No changes made.";

}