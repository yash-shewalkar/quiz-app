package pages;
import java.sql.*;

public class DatabaseConnector {
    // Other database-related methods

    public boolean authenticateUser(String username, String password, String userType) {
        // Implement authentication logic here
        // For example:
        boolean isAuthenticated = false;

        try {
            // Connect to the database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz_app", "root", "1234");

            // Prepare the query
            String query = "SELECT * FROM userInfo WHERE uname = ? AND password = ? AND adminYN = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, userType.equals("Admin") ? "1" : "0");

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if the user exists
            if (resultSet.next()) {
                isAuthenticated = true;
            }

            // Close the connection and resources
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return isAuthenticated;
    }
}