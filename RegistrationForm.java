import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RegistrationForm extends JFrame implements ActionListener {
    JTextField nameField, surnameField, phoneField, emailField;
    JPasswordField passwordField, verifyPasswordField;
    JButton registerButton;
    GridBagConstraints constraints;

    RegistrationForm() {
        setTitle("Tourify Registration");
        setLayout(new GridBagLayout());
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        constraints = new GridBagConstraints();
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.NONE;  // Καθορίζει ότι τα στοιχεία δεν θα αλλάζουν μέγεθος
        constraints.weightx = 0;  // Κρατάει το πλάτος των στοιχείων σταθερό
        constraints.weighty = 0;  // Κρατάει το ύψος των στοιχείων σταθερό
        constraints.insets = new Insets(10, 20, 10, 20);  // Προσθήκη περιθωρίων

        addLabelAndTextField("ΟΝΟΜΑ", nameField = new JTextField(20));
        addLabelAndTextField("ΕΠΙΘΕΤΟ", surnameField = new JTextField(20));
        addLabelAndTextField("ΤΗΛΕΦΩΝΟ", phoneField = new JTextField(20));
        addLabelAndTextField("E-MAIL", emailField = new JTextField(20));
        addLabelAndTextField("ΚΩΔΙΚΟΣ", passwordField = new JPasswordField(20));
        addLabelAndTextField("ΕΠΑΝΑΛΗΨΗ ΚΩΔΙΚΟΥ", verifyPasswordField = new JPasswordField(20));

        registerButton = new JButton("Εγγραφή");
        add(registerButton, constraints);
        registerButton.addActionListener(this);

        setVisible(true);
    }
    private void addLabelAndTextField(String label, JTextField field) {
        JLabel l = new JLabel(label);
        add(l, constraints);
        add(field, constraints);
    }

    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String verify_password = new String(verifyPasswordField.getPassword());

        if (password.equals(verify_password)) {
            saveUser(name, surname, phone, email, password, verify_password); 
        } else {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
        }
    }

    private void saveUser(String name, String surname, String phone, String email, String password, String verify_password) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourify", "root", "");
            String query = "INSERT INTO register (onoma, eponimo, tilefono, email, password, verify_password) VALUES (?, ?, ?, ?, ?,?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, surname);
            pst.setString(3, phone);
            pst.setString(4, email);
            pst.setString(5, password);
            pst.setString(6, verify_password);

            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "User registered successfully!");
            }
            pst.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving user: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new RegistrationForm();
    }
}



