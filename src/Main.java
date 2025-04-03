public class Main {

    public static void main(String[] args) {
        
        //edit this:
        String url = "jdbc:mysql://localhost/partsandsuppliers";
        String username = "root";
        String password = "password";

        Application app = new Application(url, username, password);

        int status = app.run();

        System.exit(status);

    }
}
