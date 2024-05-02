package pages;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;


public class AddExam extends JFrame {

    public JPanel examDashboard, navbarPanel;
    public JLabel subjectL, questionSetL, questionL,correctOptz;
    public JTextField option1, option2, option3, option4;
    public JTextArea questionText;
    public JComboBox<Integer> questionSetDropdown, correctOpts;
    public JComboBox<String> subjectDropdown;
    public JButton logoutbtn, saveNext, previous;
    public Integer[] sets = {10, 15, 30};
    public String[] subs = {"DS", "DBMS", "JAVA","IOT","MATHS"};
    public int w = 1540, h = 1200;
    public int wp = 8 * (w / 10), hp = 5 * (h / 10);
    String userNme  = new QuizLoginPage().getusername();
    public String subject;
    public JLabel profile_Name ;

    public JComboBox<String> navigationDropDown;

    // Helper method to create text fields with centered text
    public JTextField inputs(String label) {
        JTextField inputField = new JTextField(label);
        inputField.setSize(200, 40);
        inputField.setHorizontalAlignment(JTextField.CENTER); // Centering text
        return inputField;
    }
    public JButton saveNext()
    {
        JButton saveNxt = new JButton("Save & Next");
        saveNxt.setBounds(600, 520, 170, 50);
        saveNxt.setFont(new Font("Arial", Font.BOLD, 20));
        return saveNxt;
    }
    public JButton previous()
    {
        JButton preVious = new JButton("Previous");
        preVious.setBounds(270, 520, 170, 50);
        preVious.setFont(new Font("Arial", Font.BOLD, 20));
        return preVious;
    }
    public JComboBox<Integer> correctOption()
    {
        Integer [] correct = {1,2,3,4};
        JComboBox<Integer> correctOpt = new JComboBox<>(correct);
        correctOpt.setBounds(950,450,100,50);
        correctOpt.setFont(new Font("Arial", Font.BOLD, 20));

       return correctOpt;
    }
    public void initializeAddExam() {
        setTitle("Welcome Admin");
        setSize(w, h); // Set the same size as QuizLoginPage
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // Set layout of the frame to null for absolute positioning

        // Labels
        subjectL = new JLabel("Subject:");
        subjectL.setFont(new Font("Arial", Font.BOLD, 25)); // Setting font size to 25 and bold
        questionSetL = new JLabel("Questions Set:");
        questionSetL.setFont(new Font("Arial", Font.BOLD, 25)); // Setting font size to 25 and bold
        questionL = new JLabel("Question:");
        questionL.setFont(new Font("Arial", Font.BOLD, 25)); // Setting font size to 25 and bold
        correctOptz = new JLabel("Correct Option");
        correctOptz.setFont(new Font("Arial", Font.BOLD, 25)); // Setting font size to 25 and bold


        // Inputs Field
//        subjectField = inputs("Subject");
//        subjectField.setFont(new Font("Arial", Font.BOLD, 25));

        // Text Fields // DropDowns
        subjectDropdown = new JComboBox<>(subs);
        subjectDropdown.setFont(new Font("Arial", Font.BOLD, 24)); // Set font size to 24


        option1 = inputs("option 1");
        option1.setFont(new Font("Arial", Font.BOLD, 25));
        option2 = inputs("option 2");
        option2.setFont(new Font("Arial", Font.BOLD, 25));
        option3 = inputs("option 3");
        option3.setFont(new Font("Arial", Font.BOLD, 25));
        option4 = inputs("option 4");
        option4.setFont(new Font("Arial", Font.BOLD, 25));

        questionText = new JTextArea("Please put your question here");
        questionText.setFont(new Font("Arial", Font.PLAIN, 25)); // Setting font size to 25
        questionText.setAlignmentX(JTextArea.CENTER_ALIGNMENT); // Centering text

        // DropDown
        questionSetDropdown = new JComboBox<>(sets);
        questionSetDropdown.setFont(new Font("Arial", Font.BOLD, 25));

        // setting the bounds
        subjectL.setBounds(50, 40, 150, 50);
        subjectDropdown.setBounds(260, 40, 200, 50);

        questionSetL.setBounds(50, 120, 200, 50);
        questionSetDropdown.setBounds(260, 120, 200, 50);

        questionL.setBounds(50, 230, 150, 50);
        questionText.setBounds(200, 230, 850, 100);

        option1.setBounds(200, 380, 300, 50);
        option2.setBounds(540, 380, 300, 50);
        option3.setBounds(200, 450, 300, 50);
        option4.setBounds(540, 450, 300, 50);

        correctOptz.setBounds(900,380,200,50);

        // adding to the frame

        navbarPanel = new QuizLoginPage().createNavbarPanel("Add Exam");
        examDashboard = new AdminDashboard().mainPanel();
        logoutbtn = new AdminDashboard().logOutButton();
        saveNext = saveNext();
        previous = previous();
        correctOpts = correctOption();

        //add profile name
        profile_Name = new CreateExam().profile(userNme);

        examDashboard.add(subjectL);
        examDashboard.add(subjectDropdown);
        examDashboard.add(questionSetL);
        examDashboard.add(questionSetDropdown);
        examDashboard.add(questionL);
        examDashboard.add(questionText);
        examDashboard.add(option1);
        examDashboard.add(option2);
        examDashboard.add(option3);
        examDashboard.add(option4);
        examDashboard.add(saveNext);
        examDashboard.add(previous);
        examDashboard.add(correctOpts);
        examDashboard.add(correctOptz);

        LogoutUtility.addLogoutListener(logoutbtn);
        navbarPanel.add(logoutbtn);
        navigationDropDown = new AdminDashboard().navDrop();

        navbarPanel.add(navigationDropDown);
        navbarPanel.add(profile_Name);
        add(examDashboard);
        add(navbarPanel);
        setVisible(true);

        //navigation function
//        AdminDashboard navFunction = new AdminDashboard();
//        navFunction.navigateFunction();
        navigateFunction();


        saveNext.addActionListener(e -> {
            subject = (String) subjectDropdown.getSelectedItem();
            int questionSet = (int) questionSetDropdown.getSelectedItem();
            String questionTxt = questionText.getText();
            String option1Text = option1.getText();
            String option2Text = option2.getText();
            String option3Text = option3.getText();
            String option4Text = option4.getText();
            int correctOptionIndex = correctOpts.getSelectedIndex() + 1;
            System.out.println("CorrectOption Index"+correctOptionIndex);
            String correctOption = "";
            switch (correctOptionIndex) {
                case 1:
                    correctOption = option1Text;
                    break;
                case 2:
                    correctOption = option2Text;
                    break;
                case 3:
                    correctOption = option3Text;
                    break;
                case 4:
                    correctOption = option4Text;
                    break;
            }
            System.out.println("CorrectOption "+ correctOption);

            // Insert exam data into ExamsData table
//            DBExamManager.insertExamData(subject, questionSet);

            // Get the last inserted s_id
            int s_id = getSubjectId("examsdata", subject);

            // Insert question data into QuestionsData table
            DBExamManager.insertQuestionData(s_id, questionTxt, correctOption, option1Text, option2Text, option3Text, option4Text);

            // Clear fields after saving
            clearFields();
        });
    }


    private int getSubjectId(String tableName, String subject) {
        String sql = "SELECT s_id FROM " + tableName + " WHERE subject = '" + subject + "'";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz_app", "root", "1234");
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("s_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
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

    private void clearFields() {
        // Clear fields
        questionText.setText("");
        option1.setText("");
        option2.setText("");
        option3.setText("");
        option4.setText("");
        correctOpts.setSelectedIndex(0);
    }

    public static void main(String[] arg) {
        AddExam addExam = new AddExam();
        addExam.initializeAddExam();
    }
}
