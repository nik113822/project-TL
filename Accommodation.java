import javax.swing.JPanel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;  // For handling lists of strings


public class Accommodation extends JPanel {
    private JList<String> hotelsList;
     private JButton bookButton;

    public Accommodation() {
        setLayout(new BorderLayout()); 
        // Initialize components, e.g., an empty list or table to display hotels
        hotelsList = new JList<>();
        add(new JScrollPane(hotelsList), BorderLayout.CENTER);
    // Δημιουργία και προσθήκη του κουμπιού κράτησης
    bookButton = new JButton("Κάντε την κράτησή σας");
    bookButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            openBookingForm();
        }
    });
    add(bookButton, BorderLayout.SOUTH);
}


    public void updateHotels(String destination) {
        System.out.println("Updating hotels for destination: " + destination);
        List<String> hotels = new DatabaseManager().getHotelsByDestination(destination);
        System.out.println("Hotels found: " + hotels);
         DefaultListModel<String> model = new DefaultListModel<>();
        for (String hotel : hotels) {
            model.addElement(hotel);
        }
        hotelsList.setModel(model);
    }
     private void openBookingForm() {
        String selectedHotel = hotelsList.getSelectedValue();
        if (selectedHotel != null) {
            reservation bookingForm = new reservation(selectedHotel);
            bookingForm.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Παρακαλώ επιλέξτε ξενοδοχείο.");
        }
    }
        public static void main(String[] args) {
            new Accommodation();
    }
}


