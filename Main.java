import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
       
        // Set up the main window
        setTitle("Tourify");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());

        // Prevent the window from being resized
        setResizable(false);

         // Load the image
    ImageIcon imageIcon = new ImageIcon("C:\\Users\\User\\Documents\\TL\\paradot3\\src\\tourify.jpg");
    JLabel imageLabel = new JLabel(imageIcon);

        // Top label
        JLabel titleLabel = new JLabel("Καλωσήρθατε στο Tourify", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Tabbed pane for different sections
        JTabbedPane tabbedPane = new JTabbedPane();
        String[] tabNames = {"Αρχική", "Διαμονή", "Μεταφορικά", "Προφίλ"};
        for (String name : tabNames) {
            tabbedPane.add(name, new JLabel("Content for " + name, SwingConstants.CENTER));
        }

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1)); // Grid layout to stack buttons

        JButton[] buttons = new JButton[]{
            new JButton("ΠΡΟΣΦΟΡΕΣ"),
            new JButton("Εδώ θα δείτε όλους τους πληρωμένους προορισμούς"),
            new JButton("Επικοινωνήστε μαζί μας"),
            new JButton("Αποσύνδεση")
        };

        for (JButton button : buttons) {
            buttonPanel.add(button);
        }

           // Center panel containing title, tabbed pane, and buttons
    JPanel centerPanel = new JPanel(new BorderLayout());
    centerPanel.add(titleLabel, BorderLayout.NORTH);
    centerPanel.add(tabbedPane, BorderLayout.CENTER);
    centerPanel.add(buttonPanel, BorderLayout.SOUTH);

    // Add panels to the main frame
    setLayout(new BorderLayout());
    add(imageLabel, BorderLayout.NORTH);
    add(centerPanel, BorderLayout.CENTER);
        // Set visibility
        pack();  // Adjust window size to fit the components
        setLocationRelativeTo(null); // Center the window
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}
