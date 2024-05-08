import javax.swing.JFrame;     // For creating the window (JFrame)
import javax.swing.JLabel;     // For displaying text labels
import javax.swing.JButton;    // For creating buttons
import javax.swing.JOptionPane; // For displaying dialogs
import java.awt.FlowLayout;    // For layout management


public class PaymentForm extends JFrame {
    public PaymentForm(long days, long totalCost) {
        setTitle("Πληρωμή");
        setSize(300, 200);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        add(new JLabel("Συνολικό Κόστος: " + totalCost + "€"));
        JButton payButton = new JButton("Πληρώστε τώρα");
        add(payButton);
        payButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Η πληρωμή σας ολοκληρώθηκε!");
            dispose();
        });

        setVisible(true);
    }
}

