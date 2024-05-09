import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/tourify";
    private static final String USER = "root";
    private static final String PASS = "";

    public List<String> getDestinations() {
        List<String> destinations = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT destination_name FROM destinations")) {
            while (rs.next()) {
                destinations.add(rs.getString("destination_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return destinations;
    }

    public List<String> getHotelsByDestination(String destination) {
        String query = "SELECT hotel_name FROM hotels WHERE destination_name = '" + destination + "'";
        System.out.println("Executing query: " + query);  // Εκτύπωση για διάγνωση
        List<String> hotels = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                hotels.add(rs.getString("hotel_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hotels;
    }
    
}
