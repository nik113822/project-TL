import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class RegisterController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField verifyField;

    @FXML
    private Button registerButton;

    private Connection connect() {
        // Διεύθυνση της βάσης δεδομένων, όνομα χρήστη, κωδικός
        String url = "jdbc:mysql://localhost:3306/Tourify";
        String user = "root";
        String password = "";
        
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (SQLException e) {
            System.out.println("Σφάλμα κατά τη σύνδεση: " + e.getMessage());
            return null;
        }
    }

    @FXML
    void btnR(ActionEvent event) {
        String sql = "INSERT INTO register (onoma, eponimo , tilefono, email, password, verify_password) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nameField.getText());
            pstmt.setString(2, surnameField.getText());
            pstmt.setString(3, phoneField.getText());
            pstmt.setString(4, emailField.getText());
            pstmt.setString(5, passwordField.getText());
            pstmt.setString(6, verifyField.getText());
            
            pstmt.executeUpdate();
            System.out.println("Επιτυχής εγγραφή χρήστη.");
        }
            catch (SQLException e) {
                System.out.println("Σφάλμα κατά την εισαγωγή δεδομένων: " + e.getMessage());
            }
        }
    }