import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class Offer {

    private JFrame parentFrame;

    public Offer(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    public void show_list_offers() {
        String url = "jdbc:mysql://localhost:3306/tourify";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT offer_id, offer_description FROM offers")) {

            String[] columnNames = {"ID", "Description"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            while (rs.next()) {
                int id = rs.getInt("offer_id");
                String description = rs.getString("offer_description");
                model.addRow(new Object[]{id, description});
            }

            JTable table = new JTable(model);
            table.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    int row = table.rowAtPoint(evt.getPoint());
                    if (row >= 0) {
                        int selectedId = (int) table.getValueAt(row, 0);
                        String selectedDescription = (String) table.getValueAt(row, 1);
                        getUserEmailAndBookOffer(selectedId, selectedDescription);
                    }
                }
            });

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(900, 200));

            JDialog dialog = new JDialog(parentFrame, "Available Offers", true);
            dialog.setLayout(new BorderLayout());
            dialog.add(scrollPane, BorderLayout.CENTER);
            dialog.pack();
            dialog.setLocationRelativeTo(parentFrame);
            dialog.setVisible(true);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(parentFrame, "Error accessing the database: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void getUserEmailAndBookOffer(int offerId, String offerDescription) {
        JTextField emailField = new JTextField();
        Object[] message = {
            "Εισάγετε το email σας:", emailField
        };

        int response = JOptionPane.showConfirmDialog(null, message, "Email Εισαγωγή", JOptionPane.OK_CANCEL_OPTION);
        if (response == JOptionPane.OK_OPTION && !emailField.getText().isEmpty()) {
            if (isValidEmail(emailField.getText())) {
                bookOffer(offerId, emailField.getText(), offerDescription);
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Το email που δώσατε δεν είναι έγκυρο.",
                    "Σφάλμα Εισαγωγής", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private void bookOffer(int offerId, String userEmail, String offerDescription) {
        String url = "jdbc:mysql://localhost:3306/tourify";
        String user = "root";
        String password = "";
    
        // Ανοίγουμε μια σύνδεση με τη βάση δεδομένων
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO book_offer (email, offer_description) VALUES (?, ?)")) {
    
            // Εκχωρούμε τιμές στα placeholders του SQL ερωτήματος
            stmt.setString(1, userEmail);
            stmt.setString(2, offerDescription);
    
            // Εκτελούμε το SQL ερώτημα
            stmt.executeUpdate();
    
            // Ενημερώνουμε τον χρήστη για την επιτυχή κράτηση
            JOptionPane.showMessageDialog(parentFrame, "Η κράτηση έγινε με επιτυχία για την προσφορά: " + offerDescription + "\nEmail: " + userEmail,
                    "Κράτηση", JOptionPane.INFORMATION_MESSAGE);
    
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(parentFrame, "Σφάλμα κατά την πρόσβαση στη βάση δεδομένων: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
