import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;


public class Main extends JFrame {
    
    private JComboBox<String> destinationComboBox;
    private JTabbedPane tabbedPane;
    private Accommodation accommodationPanel;
    private Activities activities;
    private Offer offer;


    public Main() {

        destinationComboBox = new JComboBox<>();
    List<String> destinations = new Destination().getDestinations(); // Εδώ φορτώνονται οι προορισμοί
    for (String destination : destinations) {
        destinationComboBox.addItem(destination);
    }
    activities = new Activities(this, destinationComboBox);
        // Set up the main window
        setTitle("Tourify");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());

        // Prevent the window from being resized
        setResizable(false);

        // Load the image
        ImageIcon imageIcon = new ImageIcon("C:\\\\\\\\Users\\\\\\\\30690\\\\\\\\OneDrive - University of Patras\\\\\\\\Έγγραφα\\\\\\\\project-tl\\\\\\\\paradoteo3\\\\\\\\src\\\\\\\\τλ.png");
        JLabel imageLabel = new JLabel(imageIcon);

        // Top label
        JLabel titleLabel = new JLabel("Καλωσήρθατε στο Tourify", SwingConstants.CENTER); 
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Tabbed pane for different sections
        tabbedPane = new JTabbedPane();

       
        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.add(new JLabel("Βρείτε τον επόμενο προορισμό σας! ", SwingConstants.CENTER), BorderLayout.NORTH);

        // Button panel for the extra buttons
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1));
        JButton[] buttons = new JButton[]{
            new JButton("ΠΡΟΣΦΟΡΕΣ"),
            new JButton("Δείτε δραστηριότητες για τον επιλεγμένο προορισμό"),
            new JButton("Επικοινωνήστε μαζί μας"),
            new JButton("Αποσύνδεση")
        };

        JButton searchButton = new JButton("Αναζήτηση");
    

        buttonPanel.add(searchButton); 
        for (JButton button : buttons) {
            buttonPanel.add(button);
        }
        homePanel.add(buttonPanel, BorderLayout.SOUTH);

        tabbedPane.add("Αρχική", homePanel);

        offer = new Offer(this);
        buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                offer.show_list_offers();
            }
        });

        activities = new Activities(this, destinationComboBox);
        buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activities.showActivities();
            }
        });
        
        buttons[2].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Profile contactForm = new Profile(Main.this); // Assume Profile is available
                contactForm.setVisible(true);
            }
        });
        buttons[3].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               dispose();
               new user().setVisible(true);
            }
        });



        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to the "Διαμονή" tab
                tabbedPane.setSelectedIndex(tabbedPane.indexOfTab("Διαμονή"));
        
                // Get selected destination
                String selectedDestination = (String) destinationComboBox.getSelectedItem();
                // Update the accommodation panel based on the selected destination
                accommodationPanel.updateHotels(selectedDestination);
            }

            
        });

        // Initialize the accommodation panel
        accommodationPanel = new Accommodation();
        tabbedPane.add("Διαμονή", accommodationPanel);

        transportation transportationPanel = new transportation();
        tabbedPane.add("Μεταφορικά", transportationPanel);

       // In the profile panel or wherever you manage user settings
JPanel profilePanel = new JPanel();
JLabel notificationLabel = new JLabel("Ειδοποιήσεις:");
JLabel choiceLabel = new JLabel("");
profilePanel.add(choiceLabel);


JToggleButton notificationToggleButton = new JToggleButton("Enable Notifications", false);
notificationToggleButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (notificationToggleButton.isSelected()) {
            notificationToggleButton.setText("Disable Notifications");
            // Logic to enable notifications
            Object[] options = {"Email", "Push Notifications"};
                int choice = JOptionPane.showOptionDialog(null,
                        "Επιλέξτε τον τρόπο λήψης των ειδοποιήσεων:",
                        "Επιλογή Ειδοποίησης",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);
                if (choice == JOptionPane.YES_OPTION) {
                    // Επιλογή Email
                    choiceLabel.setText("Επιλογή: Email"); 
                    JOptionPane.showMessageDialog(null, "Έχετε επιλέξει να λαμβάνετε ειδοποιήσεις μέσω Email.");
                } else if (choice == JOptionPane.NO_OPTION) {
                    // Επιλογή Push Notifications
                    choiceLabel.setText("Επιλογή: Push Notifications"); 
                    JOptionPane.showMessageDialog(null, "Έχετε επιλέξει να λαμβάνετε ειδοποιήσεις μέσω Push Notifications.");
                }
            JOptionPane.showMessageDialog(null, "Notifications Enabled.");
        } else {
            notificationToggleButton.setText("Enable Notifications");
            // Logic to disable notifications
            choiceLabel.setText("Επιλογή: Disabled Notifications"); 
            JOptionPane.showMessageDialog(null, "Notifications Disabled.");
        }
    }
});

profilePanel.add(notificationLabel);
profilePanel.add(notificationToggleButton);

JLabel historyLabel = new JLabel("Πατήστε εδώ για να δείτε το ιστορικό σας --->");
JButton viewHistoryButton = new JButton("Προβολή ιστορικού ταξιδιών");

viewHistoryButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // new JDialog for input
        JDialog dialog = new JDialog(Main.this, "Είσοδος Email", true);
        dialog.setLayout(new BorderLayout());
        
        // Label to instruct the user
        JLabel emailLabel = new JLabel("Παρακαλώ εισάγετε το email σας:");
        JTextField emailField = new JTextField(20); // Field to enter email
        
        // Panel to hold the input components
        JPanel inputPanel = new JPanel();
        inputPanel.add(emailLabel);
        inputPanel.add(emailField);
        dialog.add(inputPanel, BorderLayout.CENTER);
        
        // Button to submit the email and view history
        JButton submitButton = new JButton("Προβολή");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to handle the email submission and history retrieval
                String userEmail = emailField.getText();
                if (userEmail != null && !userEmail.isEmpty()) {
                    displayPaymentHistory(userEmail, dialog);
                } else {
                    JOptionPane.showMessageDialog(dialog, "Παρακαλώ εισάγετε ένα έγκυρο email.", "Σφάλμα Εισαγωγής", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        
        
        // Panel for the button
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        // Setup the dialog window
        dialog.pack();
        dialog.setLocationRelativeTo(Main.this); // Center the dialog relative to the main frame
        dialog.setVisible(true);
    }
});

profilePanel.add(historyLabel);
profilePanel.add(viewHistoryButton);
tabbedPane.add("Προφίλ", profilePanel);

        // Add panels to the main frame
        setLayout(new BorderLayout());
        add(imageLabel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);

        // Set visibility
        pack();  // Adjust window size to fit the components
        setLocationRelativeTo(null); // Center the window
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    
    
        JPanel comboPanel = new JPanel();
        comboPanel.add(new JLabel("Επιλέξτε Προορισμό:"));
        comboPanel.add(destinationComboBox);
        homePanel.add(comboPanel, BorderLayout.CENTER);
    }

    private void displayPaymentHistory(String userEmail, JDialog inputDialog) {
        String url = "jdbc:mysql://localhost:3306/tourify";
        String user = "root";
        String password = "";
    
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM payments WHERE email = ?")) {
            stmt.setString(1, userEmail);
            ResultSet rs = stmt.executeQuery();
    
            // Προετοιμασία του JTable για την εμφάνιση των αποτελεσμάτων
            String[] columnNames = {"ID", "Name", "Surname", "Email", "Check-In", "Check-Out", "Guests", "Total Cost", "Hotel Name"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getString("email"),
                    rs.getDate("check_in"),
                    rs.getDate("check_out"),
                    rs.getInt("guests"),
                    rs.getDouble("total_cost"),
                    rs.getString("hotel_name")
                });
            }
            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            JDialog historyDialog = new JDialog(Main.this, "Ιστορικό ταξιδιών", true);
            historyDialog.add(scrollPane);
            historyDialog.setSize(800, 400);
            historyDialog.setLocationRelativeTo(inputDialog.getOwner());
            historyDialog.setVisible(true);
    
            inputDialog.dispose(); // Κλείσιμο του αρχικού διαλόγου
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(inputDialog, "Σφάλμα κατά την πρόσβαση στη βάση δεδομένων.", "Σφάλμα Βάσης Δεδομένων", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}