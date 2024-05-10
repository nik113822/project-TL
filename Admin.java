import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class Admin extends JFrame {
    private JTextField destinationNameField, deleteDestinationField, hotelNameField, hotelAddressField, destinationForHotelField, deleteHotelField;
    private JTextArea destinationDescriptionArea;
    private JButton addDestinationButton, deleteDestinationButton, viewPaymentsButton, addHotelButton, deleteHotelButton;
    private JTable paymentsTable;
    private JScrollPane scrollPane;  // To hold the JTable
    

    public Admin() {
        setTitle("Administrator Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome, Admin!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 24));
        add(welcomeLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(20, 1));
        setupFormPanel(formPanel);
        add(formPanel, BorderLayout.WEST);

        // Initialize the payments table and scroll pane
        paymentsTable = new JTable();
        scrollPane = new JScrollPane(paymentsTable);
        add(scrollPane, BorderLayout.CENTER); // Ensure it is added to the center to be visible

        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            this.dispose();
            new user().setVisible(true);
        });

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(logoutButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set window properties
        setSize(800, 400);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private void setupFormPanel(JPanel panel) {
        panel.add(new JLabel("Destination Name:"));
        destinationNameField = new JTextField(20);
        panel.add(destinationNameField);

        panel.add(new JLabel("Destination Description:"));
        destinationDescriptionArea = new JTextArea(5, 20);
        destinationDescriptionArea.setLineWrap(true);
        panel.add(new JScrollPane(destinationDescriptionArea));

        addDestinationButton = new JButton("Add Destination");
        addDestinationButton.addActionListener(e -> add_destination());
        panel.add(addDestinationButton);

        panel.add(new JLabel("Delete Destination by Name:"));
        deleteDestinationField = new JTextField(20);
        panel.add(deleteDestinationField);

        deleteDestinationButton = new JButton("Delete Destination");
        deleteDestinationButton.addActionListener(e -> delete_destination());
        panel.add(deleteDestinationButton);

        panel.add(new JLabel("Hotel Name:"));
        hotelNameField = new JTextField(20);
        panel.add(hotelNameField);

        panel.add(new JLabel("Hotel Address:"));
        hotelAddressField = new JTextField(20);
        panel.add(hotelAddressField);

        panel.add(new JLabel("Destination for Hotel:"));
        destinationForHotelField = new JTextField(20);
        panel.add(destinationForHotelField);

        addHotelButton = new JButton("Add Hotel");
        addHotelButton.addActionListener(e -> add_hotel());
        panel.add(addHotelButton);

        panel.add(new JLabel("Delete Hotel by Name:"));
        deleteHotelField = new JTextField(20);
        panel.add(deleteHotelField);

        deleteHotelButton = new JButton("Delete Hotel");
        deleteHotelButton.addActionListener(e -> delete_hotel());
        panel.add(deleteHotelButton);

        viewPaymentsButton = new JButton("View reservations and payments");
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
                JOptionPane.showMessageDialog(this, "Destination added successfully!");
                destinationNameField.setText("");  // Clear the input fields after successful insertion
                destinationDescriptionArea.setText("");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding destination: " + ex.getMessage());
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
                JOptionPane.showMessageDialog(this, "Destination deleted successfully!");
                deleteDestinationField.setText("");  // Clear the input field after successful deletion
            } else {
                JOptionPane.showMessageDialog(this, "No destination found with that name!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting destination: " + ex.getMessage());
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
                JOptionPane.showMessageDialog(this, "Hotel added successfully!");
                hotelNameField.setText("");
                hotelAddressField.setText("");
                destinationForHotelField.setText("");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding hotel: " + ex.getMessage());
        }
    }

    private void delete_hotel() {
        String hotelName = deleteHotelField.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourify", "root", "");
             PreparedStatement pst = conn.prepareStatement("DELETE FROM hotels WHERE hotel_name = ?")) {
            pst.setString(1, hotelName);
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Hotel deleted successfully!");
                deleteHotelField.setText(""); // Clear the input field after successful deletion
            } else {
                JOptionPane.showMessageDialog(this, "No hotel found with that name!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting hotel: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new Admin(); // To test the Admin page independently
    }
}
