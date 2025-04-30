/* UI:
 * handles presentation for user input
 */

public class UI {
    public static void prompt(){
        System.out.println();
        System.out.print(Lang.PROMPT);
    }
    public static void prompt(String msg){
        System.out.println();
        System.out.println(msg);
        prompt();
    }
    public static void subprompt(){
        System.out.print(Lang.SUBPROMPT);
    }
    public static void subprompt(String msg){
        System.out.println(msg);
        subprompt();
    }
}
