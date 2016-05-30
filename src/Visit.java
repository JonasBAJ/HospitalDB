import java.sql.*;
import java.time.LocalDate;

/**
 * Created by jonas on 16.5.25.
 */
public class Visit extends DbUtility{

    public Visit(Connection connection) {
        this.connection = connection;
    }

    public void displayVisits() {
        System.out.println("\nClinic visits:");
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM joba1111.visit");
            DBTablePrinter.printResultSet(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void createVisit(Integer patientID, Integer doctorID, Integer diagnosisID, LocalDate date) {
        // insert into Visit values(default, 1, 2, 3, '2016-06-22');
        String sql = "INSERT INTO joba1111.Visit VALUES (default, ?, ?, ?, ?)";
        setAutoCommit(false);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, patientID);
            statement.setInt(2, doctorID);
            statement.setInt(3, diagnosisID);
            statement.setDate(4, Date.valueOf(date));
            statement.execute();
            commit();
            System.out.println("New visit has been created!");
        } catch (SQLException ex) {
            if (connection != null) rollback();
            System.out.println("Insertion error: " + ex.getMessage());
        } finally {
            setAutoCommit(true);
        }
    }

    public void findVisitById(Integer visitID) {
        try {
            String sql = "SELECT * FROM joba1111.visit WHERE visit_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, visitID);
            ResultSet result = statement.executeQuery();
            DBTablePrinter.printResultSet(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateVisitDate(Integer visitID, LocalDate date) {
        String sql = "UPDATE joba1111.visit SET visit_date = ? WHERE visit_id = ?";
        setAutoCommit(false);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDate(1, Date.valueOf(date));
            statement.setInt(2, visitID);
            statement.execute();
            commit();
            System.out.println("Visit has been updated!");
        } catch (SQLException ex) {
            if (connection != null) rollback();
            System.out.println("Update error: " + ex.getMessage());
        } finally {
            setAutoCommit(true);
        }
    }

    public void deleteVisit(Integer visitID) {
        String sql = "DELETE FROM joba1111.visit WHERE visit_id = ?";
        setAutoCommit(false);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, visitID);
            statement.execute();
            System.out.println("Visit has been deleted!");
        } catch (SQLException ex) {
            if (connection != null) rollback();
            System.out.println("Delete error: " + ex.getMessage());
        } finally {
            setAutoCommit(true);
        }
    }
}
