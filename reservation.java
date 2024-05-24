import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class reservation extends JFrame {
    private JTextField nameField, surnameField, emailField, checkInField, checkOutField, guestsField;
    private int id;
    @SuppressWarnings("unused")
    private String hotelName;

    public reservation(String hotelName) {
        this.hotelName = hotelName;
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
                    saveReservation(id, name, surname, email, checkInDate, checkOutDate, guests);
        
                    long days = (checkOutDate.getTime() - checkInDate.getTime()) / (1000 * 60 * 60 * 24);
                    long totalCost = 100 * days;
                    new PaymentForm(days, totalCost, name, surname, email, checkInDate, checkOutDate, guests, hotelName);
        
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

    public void saveReservation(int id, String name, String surname, String email, Date checkInDate, Date checkOutDate, double guests) {
        String sql = "INSERT INTO reservations (id, name, surname, email, check_in, check_out, guests) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourify", "root", "");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
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

    class PaymentForm extends JFrame {
        public PaymentForm(long days, long totalCost, String name, String surname, String email, Date checkInDate, Date checkOutDate, double guests, String hotelName) {
            setTitle("Πληρωμή");
            setSize(300, 200);
            setLayout(new FlowLayout());
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            add(new JLabel("Συνολικό Κόστος: " + totalCost + "€"));
            JButton payButton = new JButton("Πληρωμή με αντικαταβολή");
            add(payButton);
            payButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(this, "Η πληρωμή σας θα γίνει την ωρα του check-in! Ευχαριστούμε για την προτίμιση σας!");
                new ReservationDetails(name, surname, email, checkInDate, checkOutDate, guests, totalCost, hotelName);
                savePaymentDetails(name, surname, email, checkInDate, checkOutDate, guests, totalCost, hotelName); // Save payment details
                dispose();
            });

            setVisible(true);
        }
    }
    private void savePaymentDetails(String name, String surname, String email, Date checkInDate, Date checkOutDate, double guests, long totalCost, String hotelName) {
        String sql = "INSERT INTO payments (name, surname, email, check_in, check_out, guests, total_cost, hotel_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourify", "root", "");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, surname);
            pstmt.setString(3, email);
            pstmt.setDate(4, new java.sql.Date(checkInDate.getTime()));
            pstmt.setDate(5, new java.sql.Date(checkOutDate.getTime()));
            pstmt.setDouble(6, guests);
            pstmt.setLong(7, totalCost);
            pstmt.setString(8, hotelName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    class ReservationDetails extends JFrame {
        public ReservationDetails(String name, String surname, String email, Date checkInDate, Date checkOutDate, double guests, long totalCost, String hotelName) {
            setTitle("Οι λεπτομέρειες της κράτησης σας");
            setSize(400, 300);
            setLayout(new GridLayout(0, 1));
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            add(new JLabel("Hotel Name: " + hotelName));
            add(new JLabel("Name: " + name));
            add(new JLabel("Surname: " + surname));
            add(new JLabel("Email: " + email));
            add(new JLabel("Check-In Date: " + sdf.format(checkInDate)));
            add(new JLabel("Check-Out Date: " + sdf.format(checkOutDate)));
            add(new JLabel("Number of Guests: " + guests));
            add(new JLabel("Total Cost: " + totalCost + "€"));
            
            setVisible(true);
        }
    }

    public static void main(String[] args) {
        new reservation(null);
    }
}
