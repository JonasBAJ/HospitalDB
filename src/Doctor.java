import java.sql.*;

/**
 * Created by jonas on 16.5.25.
 */
public class Doctor extends DbUtility {

    public Doctor(Connection connection) {
        this.connection = connection;
    }

    public void displayDoctors() {
        System.out.println("\nClinic doctors:");
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM joba1111.doctor");
            DBTablePrinter.printResultSet(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void displayAppointments() {
        System.out.println("\nView appointments:");
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM joba1111.appointments");
            DBTablePrinter.printResultSet(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void changeSalary(Integer doctorID, Double newSalary) {
        String sql = "UPDATE joba1111.doctor SET salary = ? WHERE doctor_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, newSalary);
            statement.setInt(2, doctorID);
            statement.execute();
            System.out.println("Doctor salary has been changed!");
        } catch (SQLException ex) {
            System.out.println("Update error: " + ex.getMessage());
        }
    }

    public void createDoctor(String name, String surname, String speciality, Double salary, String phone) {
        // insert into Doctor values(default, 'docName3', 'docSurname3', 'chiropractor', 950.0, '+37060000002');
        String sql = "INSERT INTO joba1111.doctor VALUES (default, ?, ?, ?, ?, ?)";
        setAutoCommit(false);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, speciality);
            statement.setDouble(4, salary);
            statement.setString(5, phone);
            statement.execute();
            commit();
            System.out.println("New doctor has been registered!");
        } catch (SQLException ex) {
            if (connection != null) rollback();
            System.out.println("Insertion error: " + ex.getMessage());
        } finally {
            setAutoCommit(true);
        }
    }

    public void removeDoctor(Integer doctorID) {
        String sql = "DELETE FROM joba1111.visit WHERE doctor_id = ?";
        String sql1 = "DELETE FROM joba1111.doctor WHERE doctor_id = ?";
        setAutoCommit(false);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, doctorID);
            statement.execute();
            PreparedStatement statement1 = connection.prepareStatement(sql1);
            statement1.setInt(1, doctorID);
            statement1.execute();
            commit();
            System.out.println("Doctor has been removed!");
        } catch (SQLException ex) {
            if (connection != null) rollback();
            System.out.println("Delete error: " + ex.getMessage());
        } finally {
            setAutoCommit(true);
        }
    }
}
