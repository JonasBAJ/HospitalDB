import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Couldn't find driver class!");
            System.out.println(ex.getMessage());
            System.exit(1);
        }
    }

    public static Connection getConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "bajor1nas123");
            //connection = DriverManager.getConnection("jdbc:postgresql://pgsql2.mif/studentu", "joba1111", "joba1111");
        } catch (SQLException ex) {
            System.out.println("Couldn't connect to database!");
            System.out.println(ex.getMessage());
            return null;
        }
        System.out.println("Successfully connected to Postgres Database");

        return connection;
    }

    public static void main(String[] args) {
        loadDriver();
        Connection connection = getConnection();
        DBController dbController = new DBController(connection);
        dbController.show();
    }


}
