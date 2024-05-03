import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class RegistrationForm extends JFrame implements ActionListener {
    JTextField nameField, surnameField, phoneField, emailField;
    JPasswordField passwordField, verifyPasswordField;
    JButton registerButton;
    GridBagConstraints constraints;

    public RegistrationForm() {
    setTitle("Εγγραφείτε στο Tourify ");
    setLayout(new GridBagLayout());
    // Set preferred size
    //setPreferredSize(new Dimension(800, 1000));
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setPreferredSize(screenSize);
    
    // Disable resizing
    setResizable(false);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    constraints = new GridBagConstraints();
    constraints.gridwidth = GridBagConstraints.REMAINDER;
    constraints.fill = GridBagConstraints.NONE;
    constraints.weightx = 0;
    constraints.weighty = 0;
    constraints.insets = new Insets(10, 20, 10, 20);

    // Adding an image at the top of the form
    ImageIcon imageIcon = new ImageIcon("C:\\Users\\User\\Documents\\TL\\paradot3\\src\\tourify.jpg"); // Set path to your image file
    JLabel imageLabel = new JLabel(imageIcon);
    add(imageLabel, constraints);

    addLabelAndTextField("ΟΝΟΜΑ", nameField = new JTextField(20));
    addLabelAndTextField("ΕΠΙΘΕΤΟ", surnameField = new JTextField(20));
    addLabelAndTextField("ΤΗΛΕΦΩΝΟ", phoneField = new JTextField(20));
    addLabelAndTextField("E-MAIL", emailField = new JTextField(20));
    addLabelAndTextField("ΚΩΔΙΚΟΣ", passwordField = new JPasswordField(20));
    addLabelAndTextField("ΕΠΑΝΑΛΗΨΗ ΚΩΔΙΚΟΥ", verifyPasswordField = new JPasswordField(20));

    registerButton = new JButton("Εγγραφή");
    add(registerButton, constraints);
    registerButton.addActionListener(this);
    // Pack the frame
    pack();

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
