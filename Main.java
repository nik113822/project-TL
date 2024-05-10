import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;



public class Main extends JFrame {
    
    private JComboBox<String> destinationComboBox;
    private JTabbedPane tabbedPane;
    private Accommodation accommodationPanel;

    public Main() {
        // Set up the main window
        setTitle("Tourify");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());

        // Prevent the window from being resized
        setResizable(false);

        // Load the image
        ImageIcon imageIcon = new ImageIcon("C:\\\\\\\\Users\\\\\\\\User\\\\\\\\Documents\\\\\\\\TL\\\\\\\\paradot3\\\\\\\\src\\\\\\\\tourify.jpg");
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
            new JButton("Εδώ θα δείτε όλους τους πληρωμένους προορισμούς"),
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


        buttons[2].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Profile contactForm = new Profile(Main.this); // Assume Profile is available
                contactForm.setVisible(true);
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
                    choiceLabel.setText("Επιλογή: Email"); // Ενημερώστε το choiceLabel ανάλογα με την επιλογή
                    JOptionPane.showMessageDialog(null, "Έχετε επιλέξει να λαμβάνετε ειδοποιήσεις μέσω Email.");
                } else if (choice == JOptionPane.NO_OPTION) {
                    // Επιλογή Push Notifications
                    choiceLabel.setText("Επιλογή: Push Notifications"); // Ενημερώστε το choiceLabel ανάλογα με την επιλογή
                    JOptionPane.showMessageDialog(null, "Έχετε επιλέξει να λαμβάνετε ειδοποιήσεις μέσω Push Notifications.");
                }
            JOptionPane.showMessageDialog(null, "Notifications Enabled.");
        } else {
            notificationToggleButton.setText("Enable Notifications");
            // Logic to disable notifications
            choiceLabel.setText("Επιλογή: Disabled Notifications"); // Ενημερώστε το choiceLabel ανάλογα με την επιλογή
            JOptionPane.showMessageDialog(null, "Notifications Disabled.");
        }
    }
});

profilePanel.add(notificationLabel);
profilePanel.add(notificationToggleButton);
tabbedPane.add("Προφίλ", profilePanel);

        profilePanel.add(notificationLabel);
        profilePanel.add(notificationToggleButton);
        tabbedPane.add("Προφίλ", profilePanel);

        // Adding other tabs
        //tabbedPane.add("Μεταφορικά", new JLabel("Content for Μεταφορικά", SwingConstants.CENTER));
        //tabbedPane.add("Προφίλ", new JLabel("Για να ενεργοποιήσετε τις ειδοποιήσεις πατήστε εδώ ---->", SwingConstants.CENTER));

        // Add panels to the main frame
        setLayout(new BorderLayout());
        add(imageLabel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);

        // Set visibility
        pack();  // Adjust window size to fit the components
        setLocationRelativeTo(null); // Center the window
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

        destinationComboBox = new JComboBox<>();
        List<String> destinations = new DatabaseManager().getDestinations();
        for (String destination : destinations) {
            destinationComboBox.addItem(destination);
        }
    
    
        JPanel comboPanel = new JPanel();
        comboPanel.add(new JLabel("Επιλέξτε Προορισμό:"));
        comboPanel.add(destinationComboBox);
        homePanel.add(comboPanel, BorderLayout.CENTER);
    }

    
        

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}