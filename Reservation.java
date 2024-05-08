import java.sql.*;
import java.util.Date;

public class Reservation {
    private int reservation_id;
    private String res_status;
    private Date start_date;
    private Date end_date;
    private double total_cost;
    private String payment_status;

    // Constructors
    public Reservation() {}

    public Reservation(int reservation_id, String res_status, Date start_date, Date end_date, double total_cost, String payment_status) {
        this.reservation_id = reservation_id;
        this.res_status = res_status;
        this.start_date = start_date;
        this.end_date = end_date;
        this.total_cost = total_cost;
        this.payment_status = payment_status;
    }

    // Getters and setters
    public int getReservationId() { return reservation_id; }
    public void setReservationId(int reservation_id) { this.reservation_id = reservation_id; }
    public String getReservationStatus() { return res_status; }
    public void setReservationStatus(String res_status) { this.res_status = res_status; }
    public Date getStartDate() { return start_date; }
    public void setStartDate(Date start_date) { this.start_date = start_date; }
    public Date getEndDate() { return end_date; }
    public void setEndDate(Date end_date) { this.end_date = end_date; }
    public double getTotalCost() { return total_cost; }
    public void setTotalCost(double total_cost) { this.total_cost = total_cost; }
    public String getPaymentStatus() { return payment_status; }
    public void setPaymentStatus(String payment_status) { this.payment_status = payment_status; }

    public boolean createReservation(int transportationId) {
        Transportation transport = Transportation.getTransportationById(transportationId);
        if (transport != null && transport.getAvailability() > 0) {
            Date startDate = new Date();
            Date endDate = new Date(); // Modify this to set the end date as per your requirements
            String sql = "INSERT INTO reservations (res_status, start_date, end_date, total_cost, payment_status, transportation_id) VALUES (?, ?, ?, ?, ?, ?)";
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourify", "root", "");
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, "Booked");
                pstmt.setDate(2, new java.sql.Date(startDate.getTime()));
                pstmt.setDate(3, new java.sql.Date(endDate.getTime()));
                pstmt.setDouble(4, 0); // Placeholder for total cost
                pstmt.setString(5, "Paid"); // Placeholder for payment status
                pstmt.setInt(6, transportationId);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    return transport.updateAvailability(transport.getAvailability() - 1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean cancelReservation(int reservationId) {
        String sql = "DELETE FROM reservations WHERE reservation_id = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourify", "root", "");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reservationId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
