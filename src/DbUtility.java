import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by jonas on 16.5.25.
 */
abstract class DbUtility {

    protected Connection connection;

    public void setAutoCommit(Boolean value) {
        try {
            connection.setAutoCommit(value);
        } catch (SQLException ex) {
            System.out.println("Transaction Error: " + ex.getMessage());
        }
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException ex) {
            System.out.println("Transaction Error: " + ex.getMessage());
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            System.out.println("Transaction Error: " + ex.getMessage());
        }
    }


}
