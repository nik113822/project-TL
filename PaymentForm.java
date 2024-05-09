import javax.swing.JFrame;     
import javax.swing.JLabel;     
import javax.swing.JButton;    
import javax.swing.JOptionPane; 
import java.awt.FlowLayout;  


public class PaymentForm extends JFrame {
    public PaymentForm(long days, long totalCost) {
        setTitle("Πληρωμή");
        setSize(300, 200);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        add(new JLabel("Συνολικό Κόστος: " + totalCost + "€"));
        JButton payButton = new JButton("Πληρωμή με αντικαταβολή");
        add(payButton);
        payButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Η πληρωμή σας θα γίνει την ώρα του check-in! Ευχαριστούμε για την προτίμηση σας!");
            dispose();
        });

        setVisible(true);
    }
}

