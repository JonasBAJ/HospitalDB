import java.sql.*;
import java.time.LocalDate;

/**
 * Created by jonas on 16.5.25.
 */
public class Patient extends DbUtility {

    public Patient(Connection connection) {
        this.connection = connection;
    }

    public void displayPatients() {
        System.out.println("\nClinic patients:");
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM joba1111.patient");
            DBTablePrinter.printResultSet(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateAddress(Integer patientID, String newAddress) {
        String sql = "UPDATE joba1111.patient SET address = ? WHERE patient_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, newAddress);
            statement.setInt(2, patientID);
            statement.execute();
            System.out.println("Patient's address has been updated!");
        } catch (SQLException ex) {
            System.out.println("Update error: " + ex.getMessage());
        }
    }

    public void createPatient(String name, String surname, String sex, LocalDate birth, String address) {
        // insert into Patient values(default, 'Name1', 'Surname1', 'male', '1990-01-01', 'Gatves g 1, Vilnius');
        String sql = "INSERT INTO joba1111.patient VALUES (default, ?, ?, ?, ?, ?)";
        setAutoCommit(false);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, sex);
            statement.setDate(4, Date.valueOf(birth));
            statement.setString(5, address);
            statement.execute();
            commit();
            System.out.println("New patient has been registered!");
        } catch (SQLException ex) {
            if (connection != null) rollback();
            System.out.println("Insertion error: " + ex.getMessage());
        } finally {
            setAutoCommit(true);
        }
    }

    public void removePatient(Integer patientID) {
        String sql = "DELETE FROM joba1111.visit WHERE patient_id = ?";
        String sql1 = "DELETE FROM joba1111.patient WHERE patient_id = ?";
        setAutoCommit(false);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, patientID);
            statement.execute();
            PreparedStatement statement1 = connection.prepareStatement(sql1);
            statement1.setInt(1, patientID);
            statement1.execute();
            commit();
            System.out.println("Patient has been removed!");
        } catch (SQLException ex) {
            if (connection != null) rollback();
            System.out.println("Delete error: " + ex.getMessage());
        } finally {
            setAutoCommit(true);
        }
    }
}
