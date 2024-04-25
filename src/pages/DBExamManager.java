package pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBExamManager {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/quiz_app";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    public static void insertExamData(String subject, int questionSet) {
        String sql = "INSERT INTO ExamsData (subject, questionSet) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, subject);
            preparedStatement.setInt(2, questionSet);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertQuestionData(int s_id, String questionText, String answer, String option1, String option2, String option3, String option4) {
        String sql = "INSERT INTO QuestionsData (s_id, QuestionText, Answer, option1, option2, option3, option4) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, s_id);
            preparedStatement.setString(2, questionText);
            preparedStatement.setString(3, answer);
            preparedStatement.setString(4, option1);
            preparedStatement.setString(5, option2);
            preparedStatement.setString(6, option3);
            preparedStatement.setString(7, option4);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
