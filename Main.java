import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Main extends JFrame {

    private JTable table; // Make table accessible in the class

    public Main() {
        setTitle("Tourify");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // Load image and setup labels
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\User\\Documents\\TL\\paradot3\\src\\tourify.jpg");
        JLabel imageLabel = new JLabel(imageIcon);
        JLabel titleLabel = new JLabel("Καλωσήρθατε στο Tourify", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Setup tabbed pane with tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        String[] tabNames = {"Αρχική", "Διαμονή", "Μεταφορικά", "Προφίλ", "Κρατήσεις"};
        for (String name : tabNames) {
            if (name.equals("Μεταφορικά")) {
                tabbedPane.add(name, createTransportationTab());
            } else if (name.equals("Κρατήσεις")) {
                tabbedPane.add(name, createReservationTab());
            } else {
                tabbedPane.add(name, new JLabel("Content for " + name, SwingConstants.CENTER));
            }
        }

        // Setup buttons
        JPanel buttonPanel = setupButtonPanel();

        // Organize central panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(titleLabel, BorderLayout.NORTH);
        centerPanel.add(tabbedPane, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add components to JFrame
        add(imageLabel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private JPanel setupButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1));
        JButton btnOffers = new JButton("ΠΡΟΣΦΟΡΕΣ");
        JButton btnDestinations = new JButton("Εδώ θα δείτε όλους τους πιθανούς προορισμούς");
        JButton btnContact = new JButton("Επικοινωνήστε μαζί μας");
        JButton btnLogout = new JButton("Αποσύνδεση");

        btnLogout.addActionListener(e -> {
            dispose(); // Close the current window
            new user().setVisible(true); // Assuming Login class has a visible method to show the login window
        });

        buttonPanel.add(btnOffers);
        buttonPanel.add(btnDestinations);
        buttonPanel.add(btnContact);
        buttonPanel.add(btnLogout);

        return buttonPanel;
    }

    private JScrollPane createTransportationTab() {
        List<Transportation> dataList = Transportation.getAllTransportations();
        String[] columnNames = {"ID", "Departure", "Arrival", "Departure Time", "Arrival Time", "Price", "Availability", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (Transportation data : dataList) {
            model.addRow(new Object[]{
                data.getTransportationId(),
                data.getDepartureLocation(),
                data.getArrivalLocation(),
                data.getDepartureTime(),
                data.getArrivalTime(),
                data.getPrice(),
                data.getAvailability(),
                data.getReservationStatus()
            });
        }
        JTable table = new JTable(model);
        return new JScrollPane(table);
    }
    

    private JPanel createReservationTab() {
        JPanel panel = new JPanel(new BorderLayout());
        List<Transportation> availableTransports = Transportation.getAllTransportations(); 
        String[] columns = {"ID", "Departure", "Arrival", "Departure Time", "Arrival Time", "Price", "Availability"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Transportation transport : availableTransports) {
            model.addRow(new Object[]{
                transport.getTransportationId(),
                transport.getDepartureLocation(),
                transport.getArrivalLocation(),
                transport.getDepartureTime(),
                transport.getArrivalTime(),
                transport.getPrice(),
                transport.getAvailability()
            });
        }

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton reserveButton = new JButton("Make Reservation");
        reserveButton.addActionListener(e -> makeReservation(table.getSelectedRow()));
        panel.add(reserveButton, BorderLayout.SOUTH);

        return panel;
    }

    private void makeReservation(int selectedRowIndex) {
        if (selectedRowIndex != -1) { // Check if a row is selected
            int transportId = (int) table.getModel().getValueAt(selectedRowIndex, 0);
            Reservation reservation = new Reservation();
            boolean success = reservation.createReservation(transportId);
            if (success) {
                JOptionPane.showMessageDialog(this, "Reservation successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Reservation failed!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a transportation option.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new); // Ensures GUI is created in the Event Dispatch Thread
    }
}
