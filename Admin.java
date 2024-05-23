import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class Admin extends JFrame {
    private JTextField destinationNameField, deleteDestinationField, hotelNameField, hotelAddressField, destinationForHotelField, deleteHotelField;
    private JTextArea destinationDescriptionArea;
    private JButton addDestinationButton, deleteDestinationButton, viewPaymentsButton, addHotelButton, deleteHotelButton;
    private JTable paymentsTable;
    private JScrollPane scrollPane;  // εμφάνιση JTable
    

    public Admin() {
        setTitle("Administrator Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, Admin!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 24));
        add(welcomeLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(20, 1));
        setupFormPanel(formPanel);
        add(formPanel, BorderLayout.WEST);

        // Αρχικοποίηση του πίνακα πληρωμών και του scroll pane
        paymentsTable = new JTable();
        scrollPane = new JScrollPane(paymentsTable);
        add(scrollPane, BorderLayout.CENTER); // Το βάζουμε στο κέντρο 

        // Logout button
        JButton logoutButton = new JButton("Αποσύνδεση");
        logoutButton.addActionListener(e -> {
            this.dispose();
            new user().setVisible(true);
        });

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(logoutButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // window properties
        setSize(800, 400);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private void setupFormPanel(JPanel panel) {
        panel.add(new JLabel("Όνομα προορισμού:"));
        destinationNameField = new JTextField(20);
        panel.add(destinationNameField);

        panel.add(new JLabel("Περιγραφή προορισμού:"));
        destinationDescriptionArea = new JTextArea(5, 20);
        destinationDescriptionArea.setLineWrap(true);
        panel.add(new JScrollPane(destinationDescriptionArea));

        addDestinationButton = new JButton("Προσθήκη προορισμού");
        addDestinationButton.addActionListener(e -> add_destination());
        panel.add(addDestinationButton);

        panel.add(new JLabel("Διαγραφή προορισμού με βάση το όνομα:"));
        deleteDestinationField = new JTextField(20);
        panel.add(deleteDestinationField);

        deleteDestinationButton = new JButton("Διαγραφή προορισμού");
        deleteDestinationButton.addActionListener(e -> delete_destination());
        panel.add(deleteDestinationButton);

        panel.add(new JLabel("Όνομα ξενοδοχείου:"));
        hotelNameField = new JTextField(20);
        panel.add(hotelNameField);

        panel.add(new JLabel("Διεύθυνση ξενοδοχείου:"));
        hotelAddressField = new JTextField(20);
        panel.add(hotelAddressField);

        panel.add(new JLabel("Τοποθεσία του ξενοδοχείου:"));
        destinationForHotelField = new JTextField(20);
        panel.add(destinationForHotelField);

        addHotelButton = new JButton("Προσθήκη ξενοδοχείου");
        addHotelButton.addActionListener(e -> add_hotel());
        panel.add(addHotelButton);

        panel.add(new JLabel("Διαγραφή ξενοδοχείου με βάση το όνομα:"));
        deleteHotelField = new JTextField(20);
        panel.add(deleteHotelField);

        deleteHotelButton = new JButton("Διαγραφή ξενοδοχείου");
        deleteHotelButton.addActionListener(e -> delete_hotel());
        panel.add(deleteHotelButton);

        viewPaymentsButton = new JButton("Προβολή κρατήσεων και πληρωμών");
        viewPaymentsButton.addActionListener(e -> view_reservations_and_payments());
        panel.add(viewPaymentsButton);
    }

    private void add_destination() {
        String name = destinationNameField.getText();
        String description = destinationDescriptionArea.getText();

        // Database connection and insertion logic
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourify", "root", "");
             PreparedStatement pst = conn.prepareStatement("INSERT INTO destinations (destination_name, dest_description) VALUES (?, ?)")) {
            pst.setString(1, name);
            pst.setString(2, description);
            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Ο προορισμός προστέθηκε επιτυχώς!");
                destinationNameField.setText("");  // Clear the input fields after successful insertion
                destinationDescriptionArea.setText("");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Σφάλμα κατά την προσθήκη προορισμού: " + ex.getMessage());
        }
    }
    private void delete_destination() {
        String name = deleteDestinationField.getText();

        // Database connection and deletion logic
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourify", "root", "");
             PreparedStatement pst = conn.prepareStatement("DELETE FROM destinations WHERE destination_name = ?")) {
            pst.setString(1, name);
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Ο προορισμός διαγράφτηκε επιτυχώς!");
                deleteDestinationField.setText("");  // Clear the input field after successful deletion
            } else {
                JOptionPane.showMessageDialog(this, "Δεν βρέθηκε προορισμός με αυτό το όνομα!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Σφάλμα κατά την διαγραφή προορισμού: " + ex.getMessage());
        }
    }

    private void view_reservations_and_payments() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Name", "Surname", "Email", "Check In", "Check Out", "Guests", "Total Cost", "Hotel Name"}, 0);
        paymentsTable.setModel(model);

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourify", "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM payments")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"), rs.getString("name"), rs.getString("surname"), rs.getString("email"), 
                    rs.getDate("check_in"), rs.getDate("check_out"), rs.getInt("guests"), 
                    rs.getDouble("total_cost"), rs.getString("hotel_name")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving payments data: " + ex.getMessage());
        }
    }

    private void add_hotel() {
        String hotelName = hotelNameField.getText();
        String hotelAddress = hotelAddressField.getText();
        String destinationName = destinationForHotelField.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourify", "root", "");
             PreparedStatement pst = conn.prepareStatement("INSERT INTO hotels (hotel_name, hotel_address, destination_name) VALUES (?, ?, ?)")) {
            pst.setString(1, hotelName);
            pst.setString(2, hotelAddress);
            pst.setString(3, destinationName);
            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Το ξενοδοχείο προστέθηκε επιτυχώς!");
                hotelNameField.setText("");
                hotelAddressField.setText("");
                destinationForHotelField.setText("");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Σφάλμα κατά την προσθήκη ξενοδοχείου: " + ex.getMessage());
        }
    }

    private void delete_hotel() {
        String hotelName = deleteHotelField.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourify", "root", "");
             PreparedStatement pst = conn.prepareStatement("DELETE FROM hotels WHERE hotel_name = ?")) {
            pst.setString(1, hotelName);
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Το ξενοδοχείο διαγράφτηκε επιτυχώς!");
                deleteHotelField.setText(""); // Clear the input field after successful deletion
            } else {
                JOptionPane.showMessageDialog(this, "Δεν βρέθηκε ξενοδοχείο με αυτό το όνομα!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Σφάλμα κατά την διαγραφή ξενοδοχείου: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new Admin(); 
    }
}
