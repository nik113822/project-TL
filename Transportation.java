import java.sql.*;
import java.util.ArrayList;
import java.util.List;





public class Transportation {
    private int transportation_id;
    private String departure_location;
    private String arrival_location;
    private String departure_time;
    private String arrival_time;
    private double price;
    private int availability;
    private String reservation_status;

    public Transportation(int transportation_id, String departure_location, String arrival_location, String departure_time, String arrival_time, double price, int availability, String reservation_status) {
        this.transportation_id = transportation_id;
        this.departure_location = departure_location;
        this.arrival_location = arrival_location;
        this.departure_time = departure_time;
        this.arrival_time = arrival_time;
        this.price = price;
        this.availability = availability;
        this.reservation_status = reservation_status;
    }
   
    // Getters
    public int getTransportationId() {
        return transportation_id;
    }

    public String getDepartureLocation() {
        return departure_location;
    }

    public String getArrivalLocation() {
        return arrival_location;
    }

    public String getDepartureTime() {
        return departure_time;
    }

    public String getArrivalTime() {
        return arrival_time;
    }

    public double getPrice() {
        return price;
    }

    public int getAvailability() {
        return availability;
    }

    public String getReservationStatus() {
        return reservation_status;
    }
    public static List<Transportation> getAllTransportations() {
        List<Transportation> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourify", "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM transportations")) {
            while (rs.next()) {
                list.add(new Transportation(
                    rs.getInt("transportation_id"),
                    rs.getString("departure_location"),
                    rs.getString("arrival_location"),
                    rs.getString("departure_time"),
                    rs.getString("arrival_time"),
                    rs.getDouble("price"),
                    rs.getInt("availability"),
                    rs.getString("reservation_status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Transportation getTransportationById(int id) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourify", "root", "");
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM transportations WHERE transportation_id = ?")) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Transportation(
                    rs.getInt("transportation_id"),
                    rs.getString("departure_location"),
                    rs.getString("arrival_location"),
                    rs.getString("departure_time"),
                    rs.getString("arrival_time"),
                    rs.getDouble("price"),
                    rs.getInt("availability"),
                    rs.getString("reservation_status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateAvailability(int newAvailability) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourify", "root", "");
             PreparedStatement pstmt = conn.prepareStatement("UPDATE transportations SET availability = ? WHERE transportation_id = ?")) {
            pstmt.setInt(1, newAvailability);
            pstmt.setInt(2, this.transportation_id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



}

