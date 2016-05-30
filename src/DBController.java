import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by jonas on 16.5.25.
 */
public class DBController {

    private Connection connection;

    private Visit visits;
    private Doctor doctors;
    private Patient patients;
    private Diagnose diagnoses;
    private Medication medications;

    public DBController(Connection connection) {
        this.connection = connection;
        this.visits = new Visit(this.connection);
        this.doctors = new Doctor(this.connection);
        this.patients = new Patient(this.connection);
        this.diagnoses = new Diagnose(this.connection);
        this.medications = new Medication(this.connection);
    }

    private void instructions() {
        System.out.println("+-------------------------+");
        System.out.println("|   Clinic data base      |");
        System.out.println("+-------------------------+");
        System.out.println("|1. Display all tables    |");
        System.out.println("|2. Display visits        |");
        System.out.println("|3. Display doctors       |");
        System.out.println("|4. Display patients      |");
        System.out.println("|5. Display diagnoses     |");
        System.out.println("|6. Display medications   |");
        System.out.println("+-------------------------+");
        System.out.println("|7. Create visit          |");
        System.out.println("|8. Create visit          |");
        System.out.println("|9. Update visit's date   |");
        System.out.println("|10. Find visit by ID     |");
        System.out.println("+-------------------------+");
        System.out.println("|11. Display appointments |");
        System.out.println("|12. Register new doctor  |");
        System.out.println("|13. Change salary        |");
        System.out.println("|14. Erase doctor         |");
        System.out.println("+-------------------------+");
        System.out.println("|15. Find patient         |");
        System.out.println("|16. Register new patient |");
        System.out.println("|17. Update address       |");
        System.out.println("|18. Erase patient        |");
        System.out.println("+-------------------------+");
        System.out.println("|19. Create diagnose      |");
        System.out.println("|20. Update diagnose      |");
        System.out.println("|21. Remove diagnose      |");
        System.out.println("+-------------------------+");
        System.out.println("|22. Create medication    |");
        System.out.println("|23. Remove medication    |");
        System.out.println("+-------------------------+");
        System.out.println("|0. Exit                  |");
        System.out.println("+-------------------------+");
    }

    public void show() {
        instructions();
        Integer choice = getChoice();
        while (true) {
            switch (choice) {
                case 1:
                    displayAll();
                    break;
                case 2:
                    displayTable(Table.VISIT);
                    break;
                case 3:
                    displayTable(Table.DOCTOR);
                    break;
                case 4:
                    displayTable(Table.PATIENT);
                    break;
                case 5:
                    displayTable(Table.DIAGNOSE);
                    break;
                case 6:
                    displayTable(Table.MEDICATION);
                    break;
                case 7:
                    if (!addVisit()) System.out.println("Bad input!");
                    break;
                case 8:
                    if (!updateVisitDate()) System.out.println("Bad input!");
                    break;
                case 9:
                    if (!deleteVisit()) System.out.println("Bad input!");
                    break;
                case 10:
                    if (!findVisit()) System.out.println("Bad input!");
                    break;
                case 11:
                    displayAppointments();
                    break;
                case 12:
                    if (!registerDoctor()) System.out.println("Bad input!");
                    break;
                case 13:
                    if (!changeSalary()) System.out.println("Bad input!");
                    break;
                case 14:
                    if (!eraseDoctor()) System.out.println("Bad input!");
                    break;
                case 15:
                    break;
                case 16:
                    if (!registerPatient()) System.out.println("Bad input!");
                    break;
                case 17:
                    if (!updateAddress()) System.out.println("Bad input!");
                    break;
                case 18:
                    if (!erasePatient()) System.out.println("Bad input!");
                    break;
                case 19:
                    if (!createDiagnosis()) System.out.println("Bad input!");
                    break;
                case 20:
                    if (!updateDiagnosis()) System.out.println("Bad input!");
                    break;
                case 21:
                    if (!eraseDiagnosis()) System.out.println("Bad input!");
                    break;
                case 22:
                    if (!createMedication()) System.out.println("Bad input!");
                    break;
                case 23:
                    if (!eraseMedication()) System.out.println("Bad input!");
                    break;
                case 0:
                    exit();
                    break;
                case 404:
                    System.out.println("Bad choice!");
                    break;
                default:
                    System.out.println("No such choice!");
                    break;
            }
            instructions();
            choice = getChoice();
        }
    }

    private Boolean eraseMedication() {
        try {
            medications.displayMedications();
            System.out.println("Enter medication ID: ");
            Integer medicationID = new Scanner(System.in).nextInt();
            medications.removeMedication(medicationID);
            return true;
        } catch (InputMismatchException ex) { return false; }
    }

    private Boolean createMedication() {
        try {
            System.out.println("Enter medication's name: ");
            String medication = new Scanner(System.in).nextLine();
            System.out.println("Enter min dose: ");
            Double minDose = new Scanner(System.in).nextDouble();
            System.out.println("Enter max dose: ");
            Double maxDose = new Scanner(System.in).nextDouble();
            medications.createMedication(medication, minDose, maxDose);
            return true;
        } catch (InputMismatchException ex) { return false; }
    }

    private Boolean eraseDiagnosis() {
        try {
            diagnoses.displayDiagnoses();
            System.out.println("Enter diagnose ID:");
            Integer diagnoseID = new Scanner(System.in).nextInt();
            diagnoses.removeDiagnose(diagnoseID);
            return true;
        } catch (InputMismatchException ex) { return false; }
    }

    private Boolean updateDiagnosis() {
        try {
            diagnoses.displayDiagnoses();
            System.out.println("Enter diagnose ID:");
            Integer diagnoseID = new Scanner(System.in).nextInt();

            System.out.println("Enter diagnosis: ");
            String diagnosis = new Scanner(System.in).nextLine();
            diagnoses.updateDiagnose(diagnoseID, diagnosis);
            return true;
        } catch (InputMismatchException ex) { return false; }
    }

    private Boolean createDiagnosis() {
        try {
            System.out.println("Enter diagnosis: ");
            String diagnosis = new Scanner(System.in).nextLine();
            medications.displayMedications();
            System.out.println("Enter medication ID: ");
            Integer medicationID = new Scanner(System.in).nextInt();
            diagnoses.createDiagnose(medicationID, diagnosis);
            return true;
        } catch (InputMismatchException ex) { return false; }
    }

    private Boolean erasePatient() {
        try {
            patients.displayPatients();
            System.out.println("Enter patient ID: ");
            Integer patientID = new Scanner(System.in).nextInt();
            patients.removePatient(patientID);
            return true;
        } catch (InputMismatchException ex) { return false; }
    }

    private Boolean updateAddress() {
        try {
            patients.displayPatients();
            System.out.println("Enter patient ID: ");
            Integer patientID = new Scanner(System.in).nextInt();

            System.out.println("Enter patient's address (street): ");
            String street = new Scanner(System.in).nextLine();
            System.out.println("Enter patient's address (house no): ");
            String houseNo = new Scanner(System.in).nextLine();
            System.out.println("Enter patient's address (city): ");
            String city = new Scanner(System.in).nextLine();
            String address = street + " g " + houseNo + " " + city;

            patients.updateAddress(patientID, address);
            return true;
        } catch (InputMismatchException ex) { return false; }
    }

    private Boolean registerPatient() {
        try {
            System.out.println("Enter patient's name: ");
            String name = new Scanner(System.in).nextLine();

            System.out.println("Enter patient's surname: ");
            String surname = new Scanner(System.in).nextLine();

            System.out.println("Enter patient's sex (male|female|other): ");
            String sex = new Scanner(System.in).nextLine();

            System.out.println("Enter patent's birth date: ");
            System.out.println("Enter year:");
            Integer year = new Scanner(System.in).nextInt();
            System.out.println("Enter month number:");
            Integer month = new Scanner(System.in).nextInt();
            System.out.println("Enter month day:");
            Integer day = new Scanner(System.in).nextInt();
            LocalDate birth = LocalDate.of(year, month, day);

            System.out.println("Enter patient's address (street): ");
            String street = new Scanner(System.in).nextLine();
            System.out.println("Enter patient's address (house no): ");
            String houseNo = new Scanner(System.in).nextLine();
            System.out.println("Enter patient's address (city): ");
            String city = new Scanner(System.in).nextLine();
            String address = street + " g " + houseNo + " " + city;

            patients.createPatient(name, surname, sex, birth, address);
            return true;
        } catch (InputMismatchException ex) { return false; }
    }

    private Boolean eraseDoctor() {
        try {
            doctors.displayDoctors();
            System.out.println("Enter doctor ID: ");
            Integer doctorID = new Scanner(System.in).nextInt();
            doctors.removeDoctor(doctorID);
            return true;
        } catch (InputMismatchException ex) { return false; }
    }

    private Boolean changeSalary() {
        try {
            doctors.displayDoctors();
            System.out.println("Enter doctor ID: ");
            Integer doctorID = new Scanner(System.in).nextInt();

            System.out.println("Enter new salary: ");
            Double salary = new Scanner(System.in).nextDouble();

            doctors.changeSalary(doctorID, salary);
            return true;
        } catch (InputMismatchException ex) { return false; }
    }

    private Boolean registerDoctor() {
        try {
            System.out.println("Enter doctor name: ");
            String name = new Scanner(System.in).nextLine();

            System.out.println("Enter doctor surname: ");
            String surname = new Scanner(System.in).nextLine();

            System.out.println("Enter doctor speciality: ");
            String speciality = new Scanner(System.in).nextLine();

            System.out.println("Enter doctor salary: ");
            Double salary = new Scanner(System.in).nextDouble();

            System.out.println("Enter doctor phone (+[11 numbers]): ");
            String phone = new Scanner(System.in).next(Pattern.compile("\\+\\d{11}"));
            doctors.createDoctor(name, surname, speciality, salary, phone);
            return true;
        } catch (InputMismatchException ex) { return false; }
    }

    private void displayAppointments() {
        doctors.displayAppointments();
    }

    private Boolean findVisit() {
        try {
            visits.displayVisits();
            System.out.println("Enter visit ID: ");
            Integer visitID = new Scanner(System.in).nextInt();
            visits.findVisitById(visitID);
            return true;
        } catch (InputMismatchException ex) { return false; }
    }

    private Boolean deleteVisit() {
        try {
            visits.displayVisits();
            System.out.println("Enter visit ID: ");
            Integer visitID = new Scanner(System.in).nextInt();
            visits.deleteVisit(visitID);
            return true;
        } catch (InputMismatchException ex) { return false; }
    }

    private Boolean updateVisitDate() {
        try {
            visits.displayVisits();
            System.out.println("Enter visit ID: ");
            Integer visitID = new Scanner(System.in).nextInt();

            System.out.println("Enter year:");
            Integer year = new Scanner(System.in).nextInt();
            System.out.println("Enter month number:");
            Integer month = new Scanner(System.in).nextInt();
            System.out.println("Enter month day:");
            Integer day = new Scanner(System.in).nextInt();
            LocalDate date = LocalDate.of(year, month, day);

            visits.updateVisitDate(visitID, date);
            return true;
        } catch (InputMismatchException ex) { return false; }
    }

    private Boolean addVisit() {
        try {
            patients.displayPatients();
            System.out.println("Enter patient ID: ");
            Integer patientID = new Scanner(System.in).nextInt();

            doctors.displayDoctors();
            System.out.println("Enter doctor ID: ");
            Integer doctorID = new Scanner(System.in).nextInt();

            diagnoses.displayDiagnoses();
            System.out.println("Enter diagnose ID: ");
            Integer diagnoseID = new Scanner(System.in).nextInt();

            System.out.println("Enter year:");
            Integer year = new Scanner(System.in).nextInt();
            System.out.println("Enter month number:");
            Integer month = new Scanner(System.in).nextInt();
            System.out.println("Enter month day:");
            Integer day = new Scanner(System.in).nextInt();
            LocalDate date = LocalDate.of(year, month, day);

            visits.createVisit(patientID, doctorID, diagnoseID, date);
            return true;
        }
        catch (InputMismatchException ex) { return false; }
    }

    private void displayAll() {
        visits.displayVisits();
        doctors.displayDoctors();
        patients.displayPatients();
        diagnoses.displayDiagnoses();
        medications.displayMedications();
    }

    private void displayTable(Table tableToDisplay) {
        switch (tableToDisplay) {
            case VISIT:
                visits.displayVisits();
                break;
            case DOCTOR:
                doctors.displayDoctors();
                break;
            case PATIENT:
                patients.displayPatients();
                break;
            case DIAGNOSE:
                diagnoses.displayDiagnoses();
                break;
            case MEDICATION:
                medications.displayMedications();
                break;
        }
    }

    private Integer getChoice() {
        try {
            System.out.println("Enter choice: ");
            return new Scanner(System.in).nextInt();
        }
        catch (InputMismatchException ex) { return 404; }
    }

    private void exit() {
        if (connection != null) {
            try {
                connection.close();
                System.exit(0);
            } catch (SQLException ex) {
                System.out.println("Error while closing connection: " + ex.getMessage());
                System.exit(1);
            }
        }
    }

    private enum Table{
        VISIT,
        DOCTOR,
        PATIENT,
        DIAGNOSE,
        MEDICATION
    }

}
