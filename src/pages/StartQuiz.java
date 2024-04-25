    package pages;
    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.*;
    import java.sql.*;

    public class StartQuiz extends JFrame {

        private JPanel examDashboard, navbarPanel;
        private JLabel subjectL, questionL,subjectText;
        private JLabel option1Label, option2Label, option3Label, option4Label; // Change JTextField to JLabel
        public JLabel questionText, questionNumber;
//        public JLabel questionTextLabel;
        public JRadioButton option1RadioButton, option2RadioButton, option3RadioButton, option4RadioButton;
        String userNme  = new QuizLoginPage().getusername();

        public JLabel profile_Name ;

        private JButton logoutbtn, saveNext, previous,submit;
        private String[] subs = {"DS", "DBMS", "JAVA","IOT","DS"};
        public int w = 1540, h = 1200;
        public int wp = 8 * (w / 10), hp = 5 * (h / 10);
        public int currentQuestionNumber = 1;// lastQuestion = 1;
//        public int questionFoundFlag = 0;

        // Helper method to create radio buttons for options
        public JRadioButton optionRadioButton(String label) {
            JRadioButton radioButton = new JRadioButton(label);
            radioButton.setFont(new Font("Arial", Font.BOLD, 20));
            return radioButton;
        }

        public JButton submiT()
        {
            JButton subt = new JButton("Submit");
            subt.setBounds(850, 40, 150, 50);
            subt.setFont(new Font("Arial", Font.BOLD, 20));
            subt.setBackground(Color.GREEN);
            return subt;
        }

        // Database connection variables
        private Connection connection;
        private PreparedStatement preparedStatement;
        private ResultSet resultSet;

        // Establish database connection
        public void connectToDatabase() {
            try {
                // Your database connection details
                String url = "jdbc:mysql://localhost:3306/quiz_app";
                String username = "root";
                String password = "1234";

                // Create connection
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to connect to the database.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Method to fetch one question from the database based on the currentQuestionNumber
        public void fetchQuestionFromDatabase(String subject) {
            try {
                // Prepare the SQL query with LIMIT and OFFSET
                String query = "SELECT QuestionText, option1, option2, option3, option4 " +
                        "FROM questionsdata " +
                        "WHERE s_id = (SELECT s_id FROM examsData WHERE subject = ?) " +
                        "LIMIT 1 OFFSET ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, subject);
                preparedStatement.setInt(2, currentQuestionNumber - 1); // Offset is currentQuestionNumber - 1

                // Execute the query
               resultSet = preparedStatement.executeQuery();


                // Check if there are any results
                if (resultSet.next()) {
                    // If there are results, update the GUI components with the fetched data
                    updateGUIWithQuestion();
//                    questionFoundFlag = 1;

                } else {
                    JOptionPane.showMessageDialog(null, "No questions found for the selected subject. \n Please Submit!", "Info", JOptionPane.INFORMATION_MESSAGE);
//                    questionFoundFlag = 0;
//                    lastQuestion = currentQuestionNumber-1;
//                    System.out.println("lastquestion "+lastQuestion);

                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to fetch questions from the database.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Method to update GUI components with the fetched question
        public void updateGUIWithQuestion() throws SQLException {
            String questionText2 = resultSet.getString("QuestionText");
            String option1 = resultSet.getString("option1");
            String option2 = resultSet.getString("option2");
            String option3 = resultSet.getString("option3");
            String option4 = resultSet.getString("option4");

            // Update the GUI components with the fetched data
//            questionTextLabel.setText(questionText);
            questionText.setText(questionText2);
            option1RadioButton.setText(option1);
            option2RadioButton.setText(option2);
            option3RadioButton.setText(option3);
            option4RadioButton.setText(option4);
        }

        public void initializeStartQuiz(String subjectName) {
            setTitle("Welcome Student");
            setSize(w, h); // Set the same size as QuizLoginPage
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(null); // Set layout of the frame to null for absolute positioning

            // Labels
            subjectL = new JLabel("Subject:");
            subjectL.setFont(new Font("Arial", Font.BOLD, 25)); // Setting font size to 25 and bold

            questionL = new JLabel("Question:");
            questionL.setFont(new Font("Arial", Font.BOLD, 25)); // Setting font size to 25 and bold

            questionNumber = new JLabel("1");
            questionNumber.setFont(new Font("Arial", Font.BOLD, 25));

            // Inputs Field
            questionText = new JLabel("Please put your question here");
            questionText.setFont(new Font("Arial", Font.PLAIN, 25)); // Setting font size to 25

            subjectText = new JLabel(subjectName);
            subjectText.setFont(new Font("Arial", Font.BOLD, 25));

            // setting the bounds
            subjectL.setBounds(50, 40, 150, 50);
            subjectText.setBounds(260, 40, 200, 50);

            questionL.setBounds(50, 230, 150, 50);
            questionNumber.setBounds(220,230,30,50);
            questionText.setBounds(260, 230, 850, 100);

            // Change JTextField to JLabel and add radio buttons for options
            option1Label = new JLabel("1:");
            option1Label.setFont(new Font("Arial", Font.BOLD, 25));
            option2Label = new JLabel("2:");
            option2Label.setFont(new Font("Arial", Font.BOLD, 25));
            option3Label = new JLabel("3:");
            option3Label.setFont(new Font("Arial", Font.BOLD, 25));
            option4Label = new JLabel("4:");
            option4Label.setFont(new Font("Arial", Font.BOLD, 25));

// Creating radio buttons for options
            option1RadioButton = optionRadioButton(""); // Instantiate instance variables
            option2RadioButton = optionRadioButton("");
            option3RadioButton = optionRadioButton("");
            option4RadioButton = optionRadioButton("");

// Grouping radio buttons so that only one can be selected at a time
            ButtonGroup optionGroup = new ButtonGroup();
            optionGroup.add(option1RadioButton);
            optionGroup.add(option2RadioButton);
            optionGroup.add(option3RadioButton);
            optionGroup.add(option4RadioButton);


            // Set bounds for labels and radio buttons
            option1Label.setBounds(140, 380, 50, 50);
            option1RadioButton.setBounds(200, 380, 300, 50);
            option2Label.setBounds(550, 380, 50, 50);
            option2RadioButton.setBounds(600, 380, 300, 50);
            option3Label.setBounds(140, 450, 50, 50);
            option3RadioButton.setBounds(200, 450, 300, 50);
            option4Label.setBounds(550, 450, 50, 50);
            option4RadioButton.setBounds(600, 450, 300, 50);

            // Initialize questionTextLabel
//            questionTextLabel = new JLabel();
//            questionTextLabel.setFont(new Font("Arial", Font.PLAIN, 25)); // Setting font size to 25
//
//            // Set bounds for questionTextLabel
//            questionTextLabel.setBounds(260, 330, 850, 100);

            profile_Name = new CreateExam().profile(userNme);


            // adding to the frame
            navbarPanel = new QuizLoginPage().createNavbarPanel("All the Best!! ");
            examDashboard = new AdminDashboard().mainPanel();
            logoutbtn = new AdminDashboard().logOutButton();
            saveNext = saveNext(subjectName);
            previous = previous(subjectName);
            submit = submiT();
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    JOptionPane.showMessageDialog(null, "Submitted Successfully!!");
                    StudentDashBoard stduDash = new StudentDashBoard();
                    stduDash.initializeStudentDashBoard();
                    dispose();

                }
            });
            LogoutUtility.addLogoutListener(logoutbtn);
            navbarPanel.add(logoutbtn);

            examDashboard.add(subjectL);
            examDashboard.add(subjectText);
            examDashboard.add(questionL);
            examDashboard.add(questionText);
            examDashboard.add(option1Label);
            examDashboard.add(option1RadioButton);
            examDashboard.add(option2Label);
            examDashboard.add(option2RadioButton);
            examDashboard.add(option3Label);
            examDashboard.add(option3RadioButton);
            examDashboard.add(option4Label);
            examDashboard.add(option4RadioButton);
            examDashboard.add(saveNext);
            examDashboard.add(previous);
            examDashboard.add(submit);
            examDashboard.add(questionNumber);
            // Add questionTextLabel to examDashboard
//            examDashboard.add(questionTextLabel);
            navbarPanel.add(logoutbtn);
            navbarPanel.add(profile_Name);
            add(examDashboard);
            add(navbarPanel);
            setVisible(true);
        }
        public JButton saveNext(String subject) {
            JButton saveNxt = new JButton("Save & Next");
            saveNxt.setBounds(600, 520, 170, 50);
            saveNxt.setFont(new Font("Arial", Font.BOLD, 20));
            saveNxt.setBackground(Color.green);

            // Add ActionListener to the button to increment the question number
// Modify the Save & Next button ActionListener
            // Modify the Save & Next button ActionListener
            saveNxt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        // Get the selected option
                        String selectedOption = null;
                        if (option1RadioButton.isSelected()) {
                            selectedOption = option1RadioButton.getText();
                        } else if (option2RadioButton.isSelected()) {
                            selectedOption = option2RadioButton.getText();
                        } else if (option3RadioButton.isSelected()) {
                            selectedOption = option3RadioButton.getText();
                        } else if (option4RadioButton.isSelected()) {
                            selectedOption = option4RadioButton.getText();
                        }

                        if (selectedOption != null) {
                            // Prepare the SQL query to retrieve s_id based on subject
                            String sIdQuery = "SELECT s_id FROM examsdata WHERE subject = ?";
                            preparedStatement = connection.prepareStatement(sIdQuery);
                            preparedStatement.setString(1, subject);

                            // Execute the query to retrieve s_id
                            ResultSet sIdResult = preparedStatement.executeQuery();

                            String userIdQuery = "SELECT sr_no FROM userinfo WHERE uname = ?";
                            preparedStatement = connection.prepareStatement(userIdQuery);
                            preparedStatement.setString(1,userNme);


                            ResultSet userIdResult = preparedStatement.executeQuery();

                            // Check if s_id is retrieved
                            if (sIdResult.next() && userIdResult.next()) {
                                int s_id = sIdResult.getInt("s_id");
                                int user_id = userIdResult.getInt("sr_no");

                                // Prepare the SQL query to insert user's answer into the useranswers table
                                String insertQuery = "INSERT INTO useranswers (user_id, s_id, Q_id, answer_selected) VALUES (?, ?, ?, ?)";
                                preparedStatement = connection.prepareStatement(insertQuery);
                                preparedStatement.setInt(1, user_id);
                                preparedStatement.setInt(2, s_id);
                                preparedStatement.setInt(3, currentQuestionNumber); // Assuming Q_id is the same as currentQuestionNumber
                                preparedStatement.setString(4, selectedOption);

                                // Execute the insert query
                                preparedStatement.executeUpdate();
                            } else {
                                // If s_id is not found for the subject, show an error message
                                JOptionPane.showMessageDialog(null, "No s_id found for the subject: " + subject, "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            // If no option is selected, show a message
                            JOptionPane.showMessageDialog(null, "Please select an option before proceeding.", "Info", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Failed to save answer.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    // Increment the current question number and update GUI
                    currentQuestionNumber++;
                    questionNumber.setText(Integer.toString(currentQuestionNumber));
                    fetchQuestionFromDatabase(subject);
                }
            });




            return saveNxt;
        }
        public JButton previous(String subject) {
            JButton preVious = new JButton("Previous");
            preVious.setBounds(270, 520, 170, 50);
            preVious.setFont(new Font("Arial", Font.BOLD, 20));
            preVious.setBackground(Color.cyan);

            // Add ActionListener to the button to increment the question number
            preVious.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Increment the current question number
                    if(currentQuestionNumber > 1)
                    {
                        currentQuestionNumber--;
                    }

                    // Update the questionNumber label with the current question number
                    questionNumber.setText(Integer.toString(currentQuestionNumber));
                    fetchQuestionFromDatabase(subject);
                }
            });

            return preVious;

        }

        public static void main(String[] arg) {
            StartQuiz startExam = new StartQuiz();
            startExam.connectToDatabase();
            startExam.initializeStartQuiz("Maths");
            startExam.fetchQuestionFromDatabase("Maths");
            startExam.saveNext("Maths");
            startExam.previous("Maths");
        }
    }
