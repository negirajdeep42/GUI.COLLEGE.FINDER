import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CollegeDAO {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/CollegeFinder", "root", "Rajdeep@1234&");
    }

    public static List<CollegeModel> getEligibleColleges(double jee, int cuet) {
        List<CollegeModel> colleges = new ArrayList<>();
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = null;

            if (jee >= 98) {
                stmt = conn.prepareStatement("SELECT name, category FROM colleges WHERE category='IIT' AND jee_min_percent <= ?");
                stmt.setDouble(1, jee);
            } else if (jee >= 90) {
                stmt = conn.prepareStatement("SELECT name, category FROM colleges WHERE category='NIT' AND jee_min_percent <= ?");
                stmt.setDouble(1, jee);
            } else if (cuet >= 500) {
                stmt = conn.prepareStatement("SELECT name, category FROM colleges WHERE cuet_min <= ?");
                stmt.setInt(1, cuet);
            }

            if (stmt != null) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    colleges.add(new CollegeModel(rs.getString("name"), rs.getString("category")));
                }
            }
        } catch (SQLException e) {
            System.out.println("DB Error: " + e.getMessage());
        }
        return colleges;
    }

    public static List<CollegeModel> getFallbackCUETColleges(int cuet) {
        List<CollegeModel> colleges = new ArrayList<>();
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT name, category FROM colleges WHERE cuet_min <= ?");
            stmt.setInt(1, cuet);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                colleges.add(new CollegeModel(rs.getString("name"), rs.getString("category")));
            }
        } catch (SQLException e) {
            System.out.println("DB Error: " + e.getMessage());
        }
        return colleges;
    }
}