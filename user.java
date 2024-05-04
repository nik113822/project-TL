import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class user extends JFrame {
    // Components for LoginPage
    private JTextField loginEmailField;
    private JPasswordField loginPasswordField;
    private JButton loginButton, toRegisterButton; 

    // Components for RegistrationForm
    private JTextField nameField, surnameField, phoneField, registerEmailField;
    private JPasswordField registerPasswordField, verifyPasswordField;
    private JButton registerButton;

    public user() {
        // Create and set up the window
        setTitle("User Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());

        // Prevent the window from being resized
        setResizable(false);

        ImageIcon imageIcon = new ImageIcon("C:\\\\Users\\\\User\\\\Documents\\\\TL\\\\paradot3\\\\src\\\\tourify.jpg"); // Replace with the path to your image file
        JLabel imageLabel = new JLabel(imageIcon);
       
        // Login Panel
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 20, 10, 20);

        loginPanel.add(imageLabel, constraints);

        loginEmailField = new JTextField(20);
        loginPasswordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        toRegisterButton = new JButton("Register New Account");

        loginButton.addActionListener(e -> performLogin());
        toRegisterButton.addActionListener(e -> switchToRegistration());

        loginPanel.add(new JLabel("Login Email:"), constraints);
        loginPanel.add(loginEmailField, constraints);
        loginPanel.add(new JLabel("Password:"), constraints);
        loginPanel.add(loginPasswordField, constraints);
        loginPanel.add(loginButton, constraints);
        loginPanel.add(toRegisterButton, constraints);

        // Registration Panel
        JPanel registerPanel = new JPanel(new GridBagLayout());
        registerPanel.add(new JLabel(imageIcon), constraints); // Add image at the top of the Registration Panel

        nameField = new JTextField(20);
        surnameField = new JTextField(20);
        phoneField = new JTextField(20);
        registerEmailField = new JTextField(20);
        registerPasswordField = new JPasswordField(20);
        verifyPasswordField = new JPasswordField(20);
        registerButton = new JButton("Register");
        

        registerButton.addActionListener(e -> performRegistration());

        registerPanel.add(new JLabel("Name:"), constraints);
        registerPanel.add(nameField, constraints);
        registerPanel.add(new JLabel("Surname:"), constraints);
        registerPanel.add(surnameField, constraints);
        registerPanel.add(new JLabel("Phone:"), constraints);
        registerPanel.add(phoneField, constraints);
        registerPanel.add(new JLabel("Register Email:"), constraints);
        registerPanel.add(registerEmailField, constraints);
        registerPanel.add(new JLabel("Password:"), constraints);
        registerPanel.add(registerPasswordField, constraints);
        registerPanel.add(new JLabel("Verify Password:"), constraints);
        registerPanel.add(verifyPasswordField, constraints);
        registerPanel.add(registerButton, constraints);

        // Add panels to the frame
        add(loginPanel, "Login");
        add(registerPanel, "Register");

        // Display the window.
         // Set the window to full screen

        pack();
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private void performLogin() {
    String email = loginEmailField.getText();
    String password = new String(loginPasswordField.getPassword());

    try {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourify", "root", "");
        String query = "SELECT * FROM register WHERE email = ? AND password = ?";
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, email);
        pst.setString(2, password);

        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            this.setVisible(false); // Hide or dispose the login window
            Main main = new Main(); // Assuming Main is another JFrame
            main.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials!");
        }
        pst.close();
        rs.close();
        conn.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error during login: " + ex.getMessage());
    }
}


    private void performRegistration() {
    String name = nameField.getText();
    String surname = surnameField.getText();
    String phone = phoneField.getText();
    String email = registerEmailField.getText();
    String password = new String(registerPasswordField.getPassword());
    String verifyPassword = new String(verifyPasswordField.getPassword());
    if (password.equals(verifyPassword)) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourify", "root", "");
            String query = "INSERT INTO register (onoma, eponimo, tilefono, email, password, verify_password) VALUES (?, ?, ?, ?, ?,?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, surname);
            pst.setString(3, phone);
            pst.setString(4, email);
            pst.setString(5, password);
            pst.setString(6, verifyPassword);
            
            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "User registered successfully!");
                this.setVisible(false); // Hide or dispose the login window
                Main main = new Main(); // Assuming Main is another JFrame
                main.setVisible(true);
            }
            pst.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving user: " + ex.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(this, "Passwords do not match!");
    }
}


    private void switchToRegistration() {
        CardLayout cl = (CardLayout)(getContentPane().getLayout());
        cl.show(getContentPane(), "Register");
    }

    public static void main(String[] args) {
        new user();
    }
}