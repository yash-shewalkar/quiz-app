package pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DBstudRegistration {
    // JDBC URL, username, and password
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/quiz_app";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";
    private Connection connection; // Connection variable declaration

    public DBstudRegistration() {
        try {
            // Establish connection
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addStudent(String name, String email, String department, String prn, String password) {
        // Check if the PRN already exists
        if (isPrnExists(prn)) {
            JOptionPane.showMessageDialog(null, "Duplicate PRN is not allowed. Please retry with a different PRN.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // SQL INSERT statement
        String sql = "INSERT INTO userInfo (uname, Email, Department, PRN_NO, Password, adminYN) VALUES (?, ?, ?, ?, SHA2(?, 256), '0')";

        try {
            // Create prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, department);
            preparedStatement.setString(4, prn);
            preparedStatement.setString(5, password);

            // Execute the INSERT statement
            int rowsAffected = preparedStatement.executeUpdate();

            // Close the prepared statement
            preparedStatement.close();

            // Check if any rows were affected (indicating successful insertion)
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isPrnExists(String prn) {
        String sql = "SELECT COUNT(*) AS count FROM userInfo WHERE PRN_NO = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, prn);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
