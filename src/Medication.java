import java.sql.*;

/**
 * Created by jonas on 16.5.25.
 */
public class Medication extends DbUtility {

    public Medication(Connection connection) {
        this.connection = connection;
    }

    public void displayMedications() {
        System.out.println("\nMedications:");
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM joba1111.medication");
            DBTablePrinter.printResultSet(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void createMedication(String medication, Double minDose, Double maxDose) {
        // insert into Medication values(default, 'Paracetamol', 1, 2);
        String sql = "INSERT INTO joba1111.medication VALUES (DEFAULT , ? , ?, ?)";
        setAutoCommit(false);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, medication);
            statement.setDouble(2, minDose);
            statement.setDouble(3, maxDose);
            statement.execute();
            commit();
            System.out.println("New medication has been added!");
        } catch (SQLException ex) {
            if (connection != null) rollback();
            System.out.println("Insertion error: " + ex.getMessage());
        } finally {
            setAutoCommit(true);
        }
    }

    public void removeMedication(Integer medicationID) {
        String sql = "DELETE FROM joba1111.visit WHERE diagnosis_id = " +
                "(SELECT diagnosis_id FROM joba1111.diagnoses WHERE medication_id = ?)";
        String sql1 = "DELETE FROM joba1111.diagnoses WHERE medication_id = ?";
        String sql2 = "DELETE FROM joba1111.medication WHERE  medication_id = ?";
        setAutoCommit(false);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, medicationID);
            statement.execute();
            PreparedStatement statement1 = connection.prepareStatement(sql1);
            statement1.setInt(1, medicationID);
            statement1.execute();
            PreparedStatement statement2 = connection.prepareStatement(sql2);
            statement2.setInt(1, medicationID);
            statement2.execute();
            commit();
            System.out.println("Medication has been removed!");
        } catch (SQLException ex) {
            if (connection != null) rollback();
            System.out.println("Delete error: " + ex.getMessage());
        } finally {
            setAutoCommit(true);
        }
    }
}
