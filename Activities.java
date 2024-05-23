import javax.swing.*;
import java.awt.BorderLayout;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class Activities {
    private JFrame parentFrame;
    private JComboBox<String> destinationComboBox;

    public Activities(JFrame parentFrame, JComboBox<String> destinationComboBox) {
        this.parentFrame = parentFrame;
        this.destinationComboBox = destinationComboBox;
    }

    public void showActivities() {
        String selectedDestination = (String) destinationComboBox.getSelectedItem();
        String query = "SELECT * FROM activities WHERE activity_location = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourify", "root", "");
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, selectedDestination);
            ResultSet rs = stmt.executeQuery();
    
            String[] columnNames = {"Activity ID", "Location", "Type", "Description", "Price"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("activity_id"),
                    rs.getString("activity_location"),
                    rs.getString("activity_type"),
                    rs.getString("activity_description"),
                    rs.getDouble("activity_price")
                });
            }
    
            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            JButton bookButton = new JButton("Κράτηση δραστηριότητας");
            bookButton.addActionListener(e -> bookActivity());
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(scrollPane, BorderLayout.CENTER);
            panel.add(bookButton, BorderLayout.SOUTH);

            JDialog resultsDialog = new JDialog(parentFrame, "Δραστηριότητες στην " + selectedDestination, true);
            resultsDialog.add(panel);
            resultsDialog.setSize(800, 400);
            resultsDialog.setLocationRelativeTo(parentFrame);
            resultsDialog.setVisible(true);
    
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(parentFrame, "Error accessing database: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    private void bookActivity() {
        String activityId = JOptionPane.showInputDialog(parentFrame, "Εισάγετε το ID της δραστηριότητας που θέλετε να κρατήσετε:");
        if (activityId != null && !activityId.isEmpty()) {
            String email = JOptionPane.showInputDialog(parentFrame, "Εισάγετε το Email σας:");
            if (email != null && !email.isEmpty()) {
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourify", "root", "");
                     PreparedStatement stmt = conn.prepareStatement("INSERT INTO book_activity (id, email) VALUES (?, ?)")) {
                    stmt.setInt(1, Integer.parseInt(activityId));
                    stmt.setString(2, email);
                    int result = stmt.executeUpdate();
                    if (result > 0) {
                        JOptionPane.showMessageDialog(parentFrame, "Επιτυχής κράτηση!\nActivity ID: " + activityId + "\nEmail: " + email);
                        showActivityDetails(activityId);  // Κλήση μεθόδου για εμφάνιση λεπτομερειών
                    } else {
                        JOptionPane.showMessageDialog(parentFrame, "Η κράτηση απέτυχε.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(parentFrame, "SQL Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(parentFrame, "Εισάγετε ένα έγκυρο id.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Η κράτηση ακυρώθηκε: Το email είναι απαραίτητο.", "Booking Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(parentFrame, "Η κράτηση ακυρώθηκε: Το id είναι απαραίτητο.", "Booking Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showActivityDetails(String activityId) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourify", "root", "");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM activities WHERE activity_id = ?")) {
            stmt.setInt(1, Integer.parseInt(activityId));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String details = "ID: " + rs.getInt("activity_id") + "\nLocation: " + rs.getString("activity_location")
                    + "\nType: " + rs.getString("activity_type") + "\nDescription: " + rs.getString("activity_description")
                    + "\nPrice: " + rs.getDouble("activity_price");
                JOptionPane.showMessageDialog(parentFrame, details, "Λεπτομέρειες δραστηριότητας", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Δεν βρέθηκε δραστηριότητα με αυτό το ID: " + activityId, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(parentFrame, "SQL Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(parentFrame, "Μη έγκυρο id .", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
