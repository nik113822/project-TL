import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class transportation extends JPanel {
    private JComboBox<String> destinationComboBox;
    private JTextField startingLocationField;
    private JComboBox<String> dateTimeComboBox;
    private JButton bookRouteButton;
    private JTextField fullNameField;
    private JTextField ticketQuantityField;

    public transportation() {
        setLayout(new GridLayout(20, 1));  // Layout με 4 γραμμές
        addDestinationDropDown();
        addStartingPointField();
        addFullNameField();
        addTicketQuantityField();
        addDateTimeDropdown();
        addBookingButton();
    }

    private void addDestinationDropDown() {
        destinationComboBox = new JComboBox<>();
        List<String> destinations = new Destination().getDestinations(); 
        for (String destination : destinations) {
            destinationComboBox.addItem(destination);
        }
        add(new JLabel("Επιλέξτε τον προορισμό σας από το μενού παρακάτω:"));
        add(destinationComboBox);
    }

    private void addStartingPointField() {
        startingLocationField = new JTextField();
        add(new JLabel("Πληκτρολογήστε την αφετηρία σας: (Οι διαθέσιμες αφετηρίες είναι μια από τις πόλεις που υπάρχουν και στους προορισμούς. Οποιαδήποτε άλλη επιλογή δεν θα είναι διαθέσιμη)"));
        add(startingLocationField);
    }

    private void addFullNameField() {
        fullNameField = new JTextField();
        add(new JLabel("Ονοματεπώνυμό για την κράτηση:"));
        add(fullNameField);
    }

    private void addTicketQuantityField() {
        ticketQuantityField = new JTextField();
        add(new JLabel("Πληκτρολογήστε τον αριθμό εισιτηρίων που θέλετε:"));
        add(ticketQuantityField);
    }

    private void addDateTimeDropdown() {
        dateTimeComboBox = new JComboBox<>();
        List<String> dateTimes = generateDateTimeOptions();
        dateTimes.forEach(dateTimeComboBox::addItem);
        add(new JLabel("Επιλέξτε ημερομηνία και ώρα:"));
        add(dateTimeComboBox);
    }

    private List<String> generateDateTimeOptions() {
        List<String> dateTimes = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate nextMonth = today.plusMonths(1);
        LocalDate firstDayOfNextMonth = nextMonth.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDayOfNextMonth = nextMonth.with(TemporalAdjusters.lastDayOfMonth());
        LocalDate date = firstDayOfNextMonth;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (date.isBefore(lastDayOfNextMonth) || date.isEqual(lastDayOfNextMonth)) {
            if (date.getDayOfWeek().getValue() == 6 || date.getDayOfWeek().getValue() == 7) { // Saturday or Sunday
                dateTimes.add(date.format(formatter) + " 10:00 AM");
                dateTimes.add(date.format(formatter) + " 12:00 PM");
            }
            date = date.plusDays(1);
        }
        return dateTimes;
    }

    private void addBookingButton() {
        bookRouteButton = new JButton("Κράτηση Δρομολογίου");
        add(bookRouteButton);
        bookRouteButton.addActionListener(e -> confirmBooking());
    }

    private void confirmBooking() {
        String destination = (String) destinationComboBox.getSelectedItem();
        String startingPoint = startingLocationField.getText();
        String fullName = fullNameField.getText();
        String dateTime = (String) dateTimeComboBox.getSelectedItem();
        int tickets;
        try {
            tickets = Integer.parseInt(ticketQuantityField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Παρακαλώ εισάγετε έγκυρο αριθμό εισιτηρίων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int totalCost = tickets * 20;
        JOptionPane.showMessageDialog(this, "Τα εισιτήρια σας κοστίζουν " + totalCost + " ευρώ. Η πληρωμή θα πραγματοποιηθεί όταν εισέλθετε στο λεωφορείο. (μετρητά ή χρεωστική/πιστωτική κάρτα)",
                                      "Επιβεβαίωση Πληρωμής", JOptionPane.INFORMATION_MESSAGE);
        displayBookingDetails(fullName, startingPoint, destination, dateTime, tickets, totalCost);
    }
    
    private void displayBookingDetails(String fullName, String startingPoint, String destination, String dateTime, int tickets, int totalCost) {
        JTextArea details = new JTextArea();
        details.setText("Λεπτομέρειες Κράτησης:\n\n" +
                        "Όνοματεπώνυμο: " + fullName + "\n" +
                        "Αφετηρία: " + startingPoint + "\n" +
                        "Προορισμός: " + destination + "\n" +
                        "Ημερομηνία και ώρα: " + dateTime + "\n" +
                        "Αριθμός Εισιτηρίων: " + tickets + "\n" +
                        "Συνολικό Κόστος: " + totalCost + " ευρώ.\n\n" +
                        "Ευχαριστούμε για την προτίμησή σας! Καλό ταξίδι!");
        details.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(details);
        JOptionPane.showMessageDialog(this, scrollPane, "Λεπτομέρειες Κράτησης", JOptionPane.INFORMATION_MESSAGE);

        savetransportationDetails(fullName, startingPoint, destination, dateTime, tickets, totalCost);
    }
    private void savetransportationDetails(String fullName, String startingPoint, String destination, String dateTime, int tickets, int totalCost) {
        String sql = "INSERT INTO transportation (full_name, starting_point, destination, date_time, tickets, total_cost) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourify", "root", "");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fullName);
            pstmt.setString(2, startingPoint);
            pstmt.setString(3, destination);
            pstmt.setString(4, dateTime);
            pstmt.setInt(5, tickets);
            pstmt.setInt(6, totalCost);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Σφάλμα κατά την αποθήκευση στη βάση δεδομένων: " + e.getMessage(), "Σφάλμα Βάσης Δεδομένων", JOptionPane.ERROR_MESSAGE);
        }
    }
}

