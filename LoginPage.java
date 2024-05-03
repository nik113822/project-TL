import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage extends JFrame implements ActionListener {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel imageLabel;  // Added variable for image label

    public LoginPage() {
        setTitle("Συνδεθείτε στο Tourify");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(10, 20, 10, 20);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(screenSize);
         // Adding an image at the top of the form
         ImageIcon imageIcon = new ImageIcon("C:\\Users\\30690\\OneDrive - University of Patras\\Έγγραφα\\project-tl\\paradoteo3\\src\\τλ.png"); 
         imageLabel = new JLabel(imageIcon);
         add(imageLabel, constraints);  
        
        // Disable resizing
        setResizable(false);

        // Προσθήκη πεδίων και κουμπιού
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Σύνδεση");
        loginButton.addActionListener(this);
       

        add(new JLabel("E-mail:"), constraints);
        add(emailField, constraints);
        add(new JLabel("Κωδικός:"), constraints);
        add(passwordField, constraints);
        add(loginButton, constraints);

        pack();
        setLocationRelativeTo(null); // Τοποθετεί το παράθυρο στο κέντρο της οθόνης
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        if (authenticateUser(email, password)) {
            JOptionPane.showMessageDialog(this, "Επιτυχής Σύνδεση!");
            dispose();
            
        } else {
            JOptionPane.showMessageDialog(this, "Λάθος e-mail ή κωδικός!", "Σφάλμα Σύνδεσης", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean authenticateUser(String email, String password) {
       
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourify", "root", "");
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM register WHERE email = ? AND password = ?");
            pst.setString(1, email);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            boolean isAuthenticated = rs.next();
            pst.close();
            conn.close();
            return isAuthenticated;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}