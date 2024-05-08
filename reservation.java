import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class reservation extends JFrame {
    private JTextField nameField, surnameField, emailField, checkInField, checkOutField, guestsField;
     double id;
    public reservation(String hotelName) {
        setTitle("Φόρμα Κράτησης - " + hotelName);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
        setLocationRelativeTo(null);
        setLayout(new GridLayout(20, 1, 1, 1));  // Χρήση GridLayout

        add(new JLabel("Όνομα:"));
        nameField = new JTextField();
        add(nameField);
        add(new JLabel("Επώνυμο:"));
        surnameField = new JTextField();
        add(surnameField);

        add(new JLabel("E-mail:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Ημερομηνία Check-In:"));
        checkInField = new JTextField();
        add(checkInField);

        add(new JLabel("Ημερομηνία Check-Out:"));
        checkOutField = new JTextField();
        add(checkOutField);

        add(new JLabel("Αριθμός Ατόμων:"));
        guestsField = new JTextField();
        add(guestsField);

        // Κουμπί υποβολής
        JButton submitButton = new JButton("Υποβολή");
        add(submitButton);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    String surname = surnameField.getText();
                    String email = emailField.getText();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    java.util.Date checkInDate = sdf.parse(checkInField.getText());
                    java.util.Date checkOutDate = sdf.parse(checkOutField.getText());
                    double guests = Double.parseDouble(guestsField.getText());
        
                    // Καλούμε την μέθοδο saveReservation με τα κατάλληλα ορίσματα.
                    saveReservation(id,name, surname, email, checkInDate, checkOutDate, guests);
        
                    long days = (checkOutDate.getTime() - checkInDate.getTime()) / (1000 * 60 * 60 * 24);
                    long totalCost = 100 * days;
                    new PaymentForm(days, totalCost);
        
                    dispose();  // Κλείσιμο της φόρμας κράτησης
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Σφάλμα κατά την επεξεργασία: " + ex.getMessage());
                }
            }
});

        pack();
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public void saveReservation(double id, String name, String surname, String email, java.util.Date checkInDate, java.util.Date checkOutDate, double guests) {
    String sql = "INSERT INTO reservations (id, name, surname, email, check_in, check_out, guests) VALUES (?, ?, ?, ?, ?,?,?)";
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourify", "root", "");
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, id); 
            pstmt.setString(2, name);
            pstmt.setString(3, surname);
            pstmt.setString(4, email);
            pstmt.setDate(5, new java.sql.Date(checkInDate.getTime()));
            pstmt.setDate(6, new java.sql.Date(checkOutDate.getTime()));
            pstmt.setDouble(7, guests);
        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    public static void main(String[] args) {
        new reservation(null);
    }
}
