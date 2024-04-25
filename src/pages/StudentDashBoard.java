package pages;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class StudentDashBoard  extends JFrame {
    public int w = 1540, h = 1200;
    public int wp = 8 * (w / 10), hp = 5 * (h / 10);
    public JLabel dbms, java, maths, ds,quizes;
    public JButton result,logoutbtn,startEnd1,startEnd2,startEnd3,startEnd4;

    public JPanel mainPanel, resultPanel,navPanel;
    String userNme  = new QuizLoginPage().getusername();
    public JComboBox<String> StudnavigateDrop;

    public JLabel profile_Name ;
    public JComboBox StudnavDrop()
    {
        JComboBox<String> navigationDropDown;
        String[] navigations = {"Home","View Result"};
        navigationDropDown = new JComboBox<>(navigations);
        navigationDropDown.setBounds(950,25,150,50);
        navigationDropDown.setFont(new Font("Arial", Font.BOLD, 20));
        return navigationDropDown;
    }
    public void initializeStudentDashBoard()
    {
        setTitle("Student Registration");
        setSize(w, h); // Set the same size as QuizLoginPage
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // Set layout of the frame to null for absolute positioning

        navPanel = new QuizLoginPage().createNavbarPanel("Student Exams");
        mainPanel = new AdminDashboard().mainPanel();
        mainPanel.setBounds(w / 8, 160, w / 2, h / 2);

        //  set bounds for labels n text fields
        quizes = new JLabel("Quizes");
        quizes.setFont(new Font("Arial", Font.BOLD, 29));
        quizes.setForeground(Color.BLUE);
        quizes.setBounds(50, 30, 100, 30);
        mainPanel.add(quizes);


        maths = new JLabel("Maths:");
        maths.setFont(new Font("Arial", Font.BOLD, 24));
        maths.setBounds(90, 100, 100, 30);
        mainPanel.add(maths);

        dbms = new JLabel("DBMS:");
        dbms.setFont(new Font("Arial", Font.BOLD, 24));
        dbms.setBounds(90, 200, 100, 30);
        mainPanel.add(dbms);

        java = new JLabel("JAVA:");
        java.setFont(new Font("Arial", Font.BOLD, 24));
        java.setBounds(90, 300, 100, 30);
        mainPanel.add(java);

        ds = new JLabel("DS:");
        ds.setFont(new Font("Arial", Font.BOLD, 24));
        ds.setBounds(90, 400, 100, 30);
        mainPanel.add(ds);


        // Buttons
        startEnd1 = new JButton("Start");
        startEnd1.setFont(new Font("Arial", Font.BOLD, 24));
        startEnd1.setBounds(250, 100, 180, 50);
        startEnd1.setBackground(Color.BLUE);
        startEnd1.setForeground(Color.WHITE);
        mainPanel.add(startEnd1);

        startEnd2 = new JButton("Start");
        startEnd2.setFont(new Font("Arial", Font.BOLD, 24));
        startEnd2.setBounds(250, 200, 180, 50);
        startEnd2.setBackground(Color.BLUE);
        startEnd2.setForeground(Color.WHITE);
        mainPanel.add(startEnd2);

        startEnd3 = new JButton("Start");
        startEnd3.setFont(new Font("Arial", Font.BOLD, 24));
        startEnd3.setBounds(250, 300, 180, 50);
        startEnd3.setBackground(Color.BLUE);
        startEnd3.setForeground(Color.WHITE);
        mainPanel.add(startEnd3);

        startEnd4 = new JButton("Start");
        startEnd4.setFont(new Font("Arial", Font.BOLD, 24));
        startEnd4.setBounds(250, 400, 180, 50);
        startEnd4.setBackground(Color.BLUE);
        startEnd4.setForeground(Color.WHITE);
        mainPanel.add(startEnd4);

        resultPanel = new AdminDashboard().mainPanel();
        resultPanel.setBounds(3 * w / 4 - 100, 160, w / 4, h / 2);

        result = new JButton("View Results");
        result.setFont(new Font("Arial", Font.BOLD, 24));
        result.setBounds(100, 100, 200, 50);
        result.setBackground(Color.BLUE);
        result.setForeground(Color.WHITE);
        resultPanel.add(result);

        //create the profile name label
        profile_Name = new CreateExam().profile(userNme);

        logoutbtn = new AdminDashboard().logOutButton();

        LogoutUtility.addLogoutListener(logoutbtn);
        navPanel.add(logoutbtn);

        navPanel.add(profile_Name);
        add(resultPanel);
        add(mainPanel);
        add(navPanel);

        StudnavigateDrop = StudnavDrop();
        navPanel.add(StudnavigateDrop);

        setVisible(true);
        updateButtonStatus();
        navigateFunction();

        result.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentResult stduresult = new StudentResult();
                stduresult.initializeStudentResult();
                dispose();
            }
        });
        startEnd1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if the button text is "Start"
                if (startEnd1.getText().equals("Start")) {
                    // Code to redirect to StartQuiz page when startEnd1 button is clicked
                    StartQuiz startQuiz = new StartQuiz();
                    startQuiz.connectToDatabase();
                    startQuiz.initializeStartQuiz("Maths");
                    startQuiz.fetchQuestionFromDatabase("Maths");
                }
            }
        });

        startEnd2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if the button text is "Start"
                if (startEnd2.getText().equals("Start")) {
                    // Code to redirect to StartQuiz page when startEnd2 button is clicked
                    StartQuiz startQuiz = new StartQuiz();
                    startQuiz.connectToDatabase();
                    startQuiz.initializeStartQuiz("DBMS");
                    startQuiz.fetchQuestionFromDatabase("DBMS");
                }
            }
        });

        startEnd3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if the button text is "Start"
                if (startEnd3.getText().equals("Start")) {
                    // Code to redirect to StartQuiz page when startEnd3 button is clicked
                    StartQuiz startQuiz = new StartQuiz();
                    startQuiz.connectToDatabase();
                    startQuiz.initializeStartQuiz("JAVA");
                    startQuiz.fetchQuestionFromDatabase("JAVA");
                }
            }
        });

        startEnd4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if the button text is "Start"
                if (startEnd4.getText().equals("Start")) {
                    // Code to redirect to StartQuiz page when startEnd4 button is clicked
                    StartQuiz startQuiz = new StartQuiz();
                    startQuiz.connectToDatabase();
                    startQuiz.initializeStartQuiz("DS");
                    startQuiz.fetchQuestionFromDatabase("DS");
                }
            }
        });

    }
    // Method to fetch subject status from the database and update button text
    private void updateButtonStatus() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz_app", "root", "1234")) {
            String query = "SELECT subject, isactive FROM examsData";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String subject = resultSet.getString("subject");
                boolean isActive = resultSet.getInt("isactive") == 1;
                updateButtonText(subject, isActive);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void navigateFunction() {
        StudnavigateDrop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String navigate = (String) StudnavigateDrop.getSelectedItem();
                if (navigate == "Home") {
                    StudentDashBoard studDs = new StudentDashBoard();
                    studDs.initializeStudentDashBoard();
                    dispose();
                } else {
                    StudentResult studExm = new StudentResult();
                    studExm.initializeStudentResult();
                    dispose();
                }
            }


        });
    }


    // Method to update button text based on subject status
    private void updateButtonText(String subject, boolean isActive) {
        switch (subject) {
            case "Maths":
                startEnd1.setText(isActive ? "Start" : "Ended");
                break;
            case "DBMS":
                startEnd2.setText(isActive ? "Start" : "Ended");
                break;
            case "JAVA":
                startEnd3.setText(isActive ? "Start" : "Ended");
                break;
            case "DS":
                startEnd4.setText(isActive ? "Start" : "Ended");
                break;
            default:
                break;

    }

}


    public static void main(String []args)
    {
        StudentDashBoard studDash = new StudentDashBoard();
        studDash.initializeStudentDashBoard();
    }


}
