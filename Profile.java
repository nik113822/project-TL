import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Profile extends JDialog {
    private JTextField textField; // Field for the issue description
    private JTextField emailField; // Field for the email
    private JButton submitButton; // Button to submit the form
    private JLabel phoneLabel;

    static final String DB_URL = "jdbc:mysql://localhost:3306/tourify";
    static final String USER = "root";
    static final String PASS = "";

    public Profile(JFrame parent) {
        super(parent, "Γράψτε μας το πρόβλημα σας", true);
        setSize(1200, 700);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(10, 1)); // Panel for inputs
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Panel for the button
        JPanel faqPanel = new JPanel(new GridLayout(10, 1)); // Panel for FAQ
        
        faqPanel.setBorder(BorderFactory.createTitledBorder("Δείτε τις πιο συχνές ερωτήσεις των πελατών μας (FAQ) και τις απαντήσεις τους"));
        
        // Label and field for the email
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        emailField.setToolTipText("Εισάγετε το email σας");
        inputPanel.add(emailLabel);
        inputPanel.add(emailField);

        // Label and field for the issue description
        JLabel issueLabel = new JLabel("Περιγράψτε το πρόβλημά σας:");
        textField = new JTextField();
        textField.setToolTipText("Περιγράψτε το πρόβλημά σας");
        inputPanel.add(issueLabel);
        inputPanel.add(textField);

        // Adding the input panel to the center of the BorderLayout
        add(inputPanel, BorderLayout.CENTER);

        // Submit button setup and add it to the button panel
        submitButton = new JButton("Υποβολή");
        buttonPanel.add(submitButton);
        add(buttonPanel, BorderLayout.SOUTH); // Adding button panel to the bottom

         // Phone number label
         phoneLabel = new JLabel("Τηλέφωνο ομάδας διαχείρισης: 210-1234567"); 
         buttonPanel.add(phoneLabel);
 
         add(buttonPanel, BorderLayout.SOUTH); // Adding button panel to the bottom

        String[] faqs = {
            "Ερώτηση 1:  Πώς μπορώ να κάνω κράτηση για ξενοδοχείο;",
            "Απάντηση 1: Απλά επιλέξτε το ξενοδοχείο και τις ημερομηνίες και ακολουθήστε τις οδηγίες.",
            "Ερώτηση 2:  Υπάρχουν ειδικές προσφορές για ομαδικές κρατήσεις;",
            "Απάντηση 2: Ναι, παρέχουμε εκπτώσεις για ομαδικές κρατήσεις άνω των 10 ατόμων.",
            "Ερώτηση 3:  Πώς μπορώ να ακυρώσω ή να τροποποιήσω την κράτησή μου;",
            "Απάντηση 3: Επικοινωνήστε με την υποστήριξη ή χρησιμοποιήστε τον λογαριασμό σας για τροποποιήσεις.",
            "Ερώτηση 4:  Ποιες μεταφορικές επιλογές είναι διαθέσιμες;",
            "Απάντηση 4: Προσφέρουμε εισιτήρια για πτήσεις, τρένα και ενοικίαση αυτοκινήτου.",
            "Ερώτηση 5:  Μπορώ να κάνω κράτηση δραστηριοτήτων μέσω της πλατφόρμας σας;",
            "Απάντηση 5: Ναι, προσφέρουμε ποικιλία δραστηριοτήτων όπως πεζοπορία, καταδύσεις και περιηγήσεις."
        };
        for (int i = 0; i < faqs.length; i++) {
            faqPanel.add(new JLabel("<html><p>" + faqs[i] + "</p></html>"));
        }
        add(faqPanel, BorderLayout.EAST); // Adding FAQ panel to the bottom


              submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveIssueToDatabase(emailField.getText(), textField.getText());
            }
        });

        setLocationRelativeTo(parent);
    }

    private void saveIssueToDatabase(String email, String issue) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "INSERT INTO issues (email, issue_description) VALUES (?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, issue);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Το πρόβλημά σας έχει καταχωρηθεί", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Close the window after submission
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        JFrame parentFrame = new JFrame();
        parentFrame.setSize(300, 300);
        parentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        parentFrame.setVisible(true);

        // Open the dialog
        Profile contactForm = new Profile(parentFrame);
        contactForm.setVisible(true);
    }
}