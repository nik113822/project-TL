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
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\30690\\OneDrive - University of Patras\\Έγγραφα\\project-tl\\paradoteo3\\src\\τλ.png");
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

        // Adding other tabs
        tabbedPane.add("Μεταφορικά", new JLabel("Content for Μεταφορικά", SwingConstants.CENTER));
        tabbedPane.add("Προφίλ", new JLabel("Content for Προφίλ", SwingConstants.CENTER));

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
    
        // You can place the combo box wherever you want, here is an example
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