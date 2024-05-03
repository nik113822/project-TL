import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RegistrationForm extends JFrame implements ActionListener {
    JTextField nameField, surnameField, phoneField, emailField, passwordField, verifyPasswordField;
    JButton registerButton;

    RegistrationForm() {
        setTitle("Tourify Registration");
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new JLabel("Name"));
        nameField = new JTextField(20);
        add(nameField);

        add(new JLabel("Surname"));
        surnameField = new JTextField(20);
        add(surnameField);

        add(new JLabel("Phone"));
        phoneField = new JTextField(20);
        add(phoneField);

        add(new JLabel("Email"));
        emailField = new JTextField(20);
        add(emailField);

        add(new JLabel("Password"));
        passwordField = new JPasswordField(20);
        add(passwordField);

        add(new JLabel("Confirm Password"));
        verifyPasswordField = new JPasswordField(20);
        add(verifyPasswordField);

        registerButton = new JButton("Εγγραφή");
        add(registerButton);
        registerButton.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getText());
        String verify_password = new String(verifyPasswordField.getText());

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