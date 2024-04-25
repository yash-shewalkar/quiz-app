package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class StudentResult extends JFrame {
    public int w = 1540, h = 1200;
    public int wp = 8 * (w / 10), hp = 5 * (h / 10);
    public JLabel sName, sPRR, subjects,marks,grades,results;
    public JTextField name, prn,MarksGot, GradesGot, ResultsGot;
    public JButton result,logoutbtn;
    public JComboBox<String> subjectDropdown;
    public String[] subs = {"DS", "DBMS", "JAVA","IOT","MATHS"};
    public JPanel mainPanel, marksheetPanel,navPanel;
    public JComboBox<String> navigationDropDown;

    String userNme  = new QuizLoginPage().getusername();

    public JLabel profile_Name;
    public JComboBox<String> StudnavigateDrop;
    public JComboBox StudnavDrop()
    {
        JComboBox<String> navigationDropDown;
        String[] navigations = {"Home","View Result"};
        navigationDropDown = new JComboBox<>(navigations);
        navigationDropDown.setBounds(950,25,150,50);
        navigationDropDown.setFont(new Font("Arial", Font.BOLD, 20));
        return navigationDropDown;
    }
    public void initializeStudentResult()
    {
        setTitle("Student Registration");
        setSize(w, h); // Set the same size as QuizLoginPage
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // Set layout of the frame to null for absolute positioning

        navPanel = new QuizLoginPage().createNavbarPanel("Student Results");
        mainPanel = new AdminDashboard().mainPanel();
        mainPanel.setBounds(w / 8, 160, w / 2, h / 2);

        //  set bounds for labels n text fields
        sName = new JLabel("Name:");
        sName.setFont(new Font("Arial", Font.BOLD, 24));
        sName.setBounds(90, 100, 100, 30);
        mainPanel.add(sName);

        name = new JTextField();
        name.setBounds(240, 100, 300, 30);
        name.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(name);

        sPRR = new JLabel("PRN:");
        sPRR.setFont(new Font("Arial", Font.BOLD, 24));
        sPRR.setBounds(90, 200, 100, 30);
        mainPanel.add(sPRR);

        prn = new JTextField(8);
        prn.setBounds(240, 200, 200, 30);
        prn.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(prn);

        // Create and set bounds for the subjects label
        subjects = new JLabel("Subjects:");
        subjects.setFont(new Font("Arial", Font.BOLD, 24));
        subjects.setBounds(90, 300, 150, 30);
        mainPanel.add(subjects);

        // Create and set bounds for the subjectDropdown JComboBox
        subjectDropdown = new JComboBox<>(subs);
        subjectDropdown.setBounds(240, 300, 120, 30);
        subjectDropdown.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(subjectDropdown);

        // Create and set bounds for the result button
        result = new JButton("Get Result");
        result.setFont(new Font("Arial", Font.BOLD, 24));
        result.setBounds(w/4-100, 500, 180, 50);
        result.setBackground(Color.BLUE);
        result.setForeground(Color.WHITE);
        mainPanel.add(result);

        //event handling for result button
        result.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String studentName = name.getText(); // Get student's name
                String prnValue = prn.getText(); // Get PRN value
                String selectedSubject = (String) subjectDropdown.getSelectedItem(); // Get selected subject

                // Step 1: Retrieve user_id from usersdata table based on PRN
                int userId = getUserIdFromPRN(prnValue);

                if (userId != -1) { // If user_id is found
                    // Step 2: Retrieve answers selected by the user from useranswers table
                    // You can implement a method to retrieve answers and calculate marks

                    // Step 3: Calculate marks based on selected answers and correct answers
                    int marksObtained = calculateMarks(userId, selectedSubject);
                    System.out.println(marksObtained);

                    // Step 4: Determine grade based on marks obtained
                    String grade = calculateGrade(marksObtained);
                    System.out.println(grade);

                    // Step 5: Determine result (pass or fail) based on criteria
                    String resultStatus = determineResult(grade);

                    // Display marks, grade, and result in respective text fields
                    MarksGot.setText(Integer.toString(marksObtained));
                    GradesGot.setText(grade);
                    ResultsGot.setText(resultStatus);
                } else {
                    // If user_id is not found, display an error message
                    JOptionPane.showMessageDialog(null, "User not found for the provided PRN.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        marksheetPanel = new AdminDashboard().mainPanel();
        marksheetPanel.setBounds(3 * w / 4 - 100, 160, w / 4, h / 2);



        // Create and set bounds for labels and non-editable text fields in marksheetPanel
        marks = new JLabel("Marks:");
        marks.setFont(new Font("Arial", Font.BOLD, 24));
        marks.setBounds(50, 100, 100, 30);
        marksheetPanel.add(marks);

        MarksGot = new JTextField();
        MarksGot.setEditable(false);
        MarksGot.setBounds(150, 100, 200, 30);
        MarksGot.setFont(new Font("Arial", Font.BOLD, 24));
        marksheetPanel.add(MarksGot);

        grades = new JLabel("Grades:");
        grades.setFont(new Font("Arial", Font.BOLD, 24));
        grades.setBounds(50, 200, 100, 30);
        marksheetPanel.add(grades);

        GradesGot = new JTextField();
        GradesGot.setEditable(false);
        GradesGot.setBounds(150, 200, 200, 30);
        GradesGot.setFont(new Font("Arial", Font.BOLD, 24));
        marksheetPanel.add(GradesGot);

        results = new JLabel("Results:");
        results.setFont(new Font("Arial", Font.BOLD, 24));
        results.setBounds(50, 300, 100, 30);
        marksheetPanel.add(results);

        ResultsGot = new JTextField();
        ResultsGot.setEditable(false);
        ResultsGot.setBounds(150, 300, 200, 30);
        ResultsGot.setFont(new Font("Arial", Font.BOLD, 24));
        marksheetPanel.add(ResultsGot);

        //create profile name
        profile_Name = new CreateExam().profile(userNme);

        logoutbtn = new AdminDashboard().logOutButton();

        LogoutUtility.addLogoutListener(logoutbtn);
        navPanel.add(logoutbtn);

        navigationDropDown = new AdminDashboard().navDrop();
        StudnavigateDrop = StudnavDrop();



        navPanel.add(profile_Name);
        add(marksheetPanel);
        add(mainPanel);
        add(navPanel);
        System.out.println("getUserRole"+getUserRole(userNme));
        if(getUserRole(userNme) == 1)
        {
            navPanel.add(navigationDropDown);
            navigateFunction();
        }
        else if(getUserRole(userNme) == 0){
            navPanel.add(StudnavigateDrop);
            studNavFunction();
        }


        setVisible(true);
//        AdminDashboard navFunction = new AdminDashboard();
//        navFunction.navigateFunction();


    }

    // Method to retrieve user_id from usersdata table based on PRN
    private int getUserIdFromPRN(String prnValue) {
        try {
            String query = "SELECT sr_no FROM userinfo WHERE PRN_NO = ?";
            // Establishing the connection and preparing the statement
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz_app", "root", "1234");
           PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, prnValue);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("sr_no");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to retrieve user ID.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return -1; // If user not found or any error occurs
    }
    // Helper method to retrieve selected answers for a user and subject from useranswers table
    private Map<Integer, String> getSelectedAnswers(int userId, String subject) {
        Map<Integer, String> selectedAnswers = new HashMap<>();
        try {
            String query = "SELECT Q_id, answer_selected FROM useranswers WHERE user_id = ? AND s_id = (SELECT s_id FROM examsdata WHERE subject = ?)";
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz_app", "root", "1234");
            PreparedStatement  preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, subject);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int qId = resultSet.getInt("Q_id");
                String answer = resultSet.getString("answer_selected");
                selectedAnswers.put(qId, answer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to retrieve selected answers.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return selectedAnswers;
    }

    // Method to calculate marks based on selected answers and correct answers
    private int calculateMarks(int userId, String selectedSubject) {
        int totalMarks = 0;

        // Retrieve selected answers
        Map<Integer, String> selectedAnswers = getSelectedAnswers(userId, selectedSubject);
        System.out.println(selectedAnswers);

        try {
            // Query to retrieve correct answers for the subject
            String correctAnswersQuery = "SELECT Q_id, answer FROM questionsdata WHERE s_id = (SELECT s_id FROM examsdata WHERE subject = ?)";
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz_app", "root", "1234");
            PreparedStatement  preparedStatement = connection.prepareStatement(correctAnswersQuery);
            preparedStatement.setString(1, selectedSubject);
            ResultSet resultSet = preparedStatement.executeQuery();


            // Compare selected answers with correct answers and calculate marks
            // Compare selected answers with correct answers and calculate marks
            int i= 1;
            while (resultSet.next()) {
//                int qId = resultSet.getInt("Q_id");
                String correctAnswer = resultSet.getString("Answer");
                String selectedAnswer = selectedAnswers.get(i++);
                System.out.println("selected :"+selectedAnswer);
                System.out.println("corrected :"+correctAnswer);

                // If selected answer matches correct answer, increment marks
                if (selectedAnswer != null && selectedAnswer.equals(correctAnswer)) {
                    totalMarks++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to calculate marks.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return totalMarks;
    }


    // Method to calculate grade based on marks obtained
    private String calculateGrade(int marksObtained) {
        if (marksObtained >= 10) {
            return "A";
        } else if (marksObtained >= 5) {
            return "B";
        } else if (marksObtained == 4) {
            return "C";
        } else {
            return "F";
        }
    }

    // Method to determine result (pass or fail) based on criteria
    private String determineResult(String grades) {
        return (grades !="F") ? "Pass" : "Fail";
    }
    public void navigateFunction()
    {
        navigationDropDown.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {

                String navigate = (String) navigationDropDown.getSelectedItem();
                if(navigate == "Home")
                {
                    AdminDashboard adminDs = new AdminDashboard();
                    adminDs.initializeAdminDashboard();
                    dispose();
                }
                else if(navigate == "Add Exam")
                {
                    AdminDashboard  addexm =new AdminDashboard();
                    addexm.RedirectToAddExam();
                    dispose();
                }
                else if(navigate == "Add Student")
                {
                    AdminDashboard  addstd =new AdminDashboard();
                    addstd.RedirectToStudentRegistration();
                    dispose();
                }
                else if(navigate == "View Exam")
                {
                    AdminDashboard  addexm =new AdminDashboard();
                    addexm.RedirectToViewExam();
                    dispose();
                }
                else {
                    AdminDashboard  addexm =new AdminDashboard();
                    addexm.RedirectToViewResult();
                    dispose();
                }
            }




        });

    }
    private int getUserRole(String username) {
        int role = 1;
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz_app", "root", "1234");
            String query = "SELECT adminYN FROM userinfo WHERE uname = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userNme);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                role = resultSet.getInt("adminYN");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }
    public void studNavFunction() {
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
    public static void main(String []arg)
    {
        StudentResult studResult = new StudentResult();
        studResult.initializeStudentResult();
    }
}
