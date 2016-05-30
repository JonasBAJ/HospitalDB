import java.sql.*;

/**
 * Created by jonas on 16.5.25.
 */
public class Diagnose extends DbUtility {

    public Diagnose(Connection connection) {
        this.connection = connection;
    }

    public void displayDiagnoses() {
        System.out.println("\nDiagnoses:");
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM joba1111.diagnoses");
            DBTablePrinter.printResultSet(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateDiagnose(Integer diagnosisID, String diagnose) {
        String sql = "UPDATE joba1111.diagnoses SET diagnosis = ? WHERE diagnosis_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, diagnose);
            statement.setInt(2, diagnosisID);
            statement.execute();
            System.out.println("Diagnose has been updated!");
        } catch (SQLException ex) {
            System.out.println("Update error: " + ex.getMessage());
        }
    }

    public void createDiagnose(Integer medicationID, String diagnose) {
        // insert into Diagnoses values(default, default, 'Fever');
        String sql = "INSERT INTO joba1111.diagnoses VALUES (DEFAULT , ? , ?)";
        setAutoCommit(false);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, medicationID);
            statement.setString(2, diagnose);
            statement.execute();
            commit();
            System.out.println("New diagnose has been created!");
        } catch (SQLException ex) {
            if (connection != null) rollback();
            System.out.println("Insertion error: " + ex.getMessage());
        } finally {
            setAutoCommit(true);
        }
    }

    public void removeDiagnose(Integer diagnosisID) {
        String sql = "DELETE FROM joba1111.visit WHERE diagnosis_id = ?";
        String sql1 = "DELETE FROM joba1111.diagnoses WHERE diagnosis_id = ?";
        setAutoCommit(false);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, diagnosisID);
            statement.execute();
            PreparedStatement statement1 = connection.prepareStatement(sql1);
            statement1.setInt(1, diagnosisID);
            statement1.execute();
            commit();
            System.out.println("Diagnosis has been removed!");
        } catch (SQLException ex) {
            if (connection != null) rollback();
            System.out.println("Delete error: " + ex.getMessage());
        } finally {
            setAutoCommit(true);
        }
    }
}
