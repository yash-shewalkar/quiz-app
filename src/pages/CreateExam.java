    package pages;

    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.PreparedStatement;
    import java.sql.SQLException;

    public class CreateExam extends JFrame {

        public int w = 1540, h = 1200;
        public int wp = 8 * (w / 10), hp = 5 * (h / 10);
        public JPanel registrationPanel, navPanel;
        public JButton end, create, logoutbtn;
        public JLabel subject, status, user_profile_name;

        public JComboBox<String> subjectDropdown, statusDropdown;
        public String[] subs = {"DS", "DBMS", "JAVA", "IOT", "MATHS"};
        public String[] stats = {"Active", "InActive"};

        public JComboBox<String> navigationDropDown;



        public void initializeCreateExam() {
            setTitle("Create Exam");
            setSize(w, h); // Set the same size as QuizLoginPage
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(null); // Set layout of the frame to null for absolute positioning

            String userNaMe= new QuizLoginPage().getusername();
            //panels
            navPanel = new QuizLoginPage().createNavbarPanel("Create Exam");
            registrationPanel = new AdminDashboard().mainPanel();
            registrationPanel.setBounds(w / 4, 160, w / 2, h / 2);
            logoutbtn = new AdminDashboard().logOutButton();



            // Labels
            subject = new JLabel("Subject:");
            subject.setFont(new Font("Arial", Font.BOLD, 24)); // Set font size to 24

            status = new JLabel("Status:");
            status.setFont(new Font("Arial", Font.BOLD, 24)); // Set font size to 24

            // Text Fields
            subjectDropdown = new JComboBox<>(subs);
            subjectDropdown.setFont(new Font("Arial", Font.BOLD, 24)); // Set font size to 24

            // DropDown
            statusDropdown = new JComboBox<>(stats);
            statusDropdown.setFont(new Font("Arial", Font.BOLD, 25));

            // Register Button
            create = new JButton("Create");
            create.setFont(new Font("Arial", Font.BOLD, 24)); // Set font size to 24
            create.setBackground(Color.green);
            end = new JButton("End");
            end.setFont(new Font("Arial", Font.BOLD, 24)); // Set font size to 24
            end.setBackground(Color.ORANGE);

            // Set bounds for labels and text fields
            subject.setBounds(50, 50, 150, 50);
            subjectDropdown.setBounds(200, 50, 300, 50);

            status.setBounds(50, 250, 150, 50);
            statusDropdown.setBounds(200, 250, 200, 50);
            create.setBounds(w / 4 - 250, 500, 150, 50); // Adjust the bounds as needed
            end.setBounds(w / 4 + 100, 500, 150, 50);

            // Add components to the registration panel
            registrationPanel.add(subject);
            registrationPanel.add(subjectDropdown);

            registrationPanel.add(status);
            registrationPanel.add(statusDropdown);
            registrationPanel.add(end);
            registrationPanel.add(create);

            user_profile_name = profile(userNaMe);
//            user_profile_name.setBounds(100, 10, 250, 50);
            //adds
            navPanel.add(logoutbtn);
            navigationDropDown = new AdminDashboard().navDrop();

            navPanel.add(navigationDropDown);
            navPanel.add(user_profile_name);

            // Add ActionListener to the logout button
            LogoutUtility.addLogoutListener(logoutbtn);
            navPanel.add(logoutbtn);
            add(registrationPanel);
            add(navPanel);

            setVisible(true);

            navigateFunction();

            // Add action listeners
            create.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateDatabase(1); // 1 represents Active status
                }
            });

            end.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateDatabase(0); // 0 represents InActive status
                }
            });
        }

        public JLabel profile(String username) {
            JLabel profile_name = new JLabel(username);
            profile_name.setBounds(200, 25, 250, 50);
            profile_name.setFont(new Font("Arial", Font.BOLD, 24));
            profile_name.setForeground(Color.WHITE);
            return profile_name;
        }

        private void updateDatabase(int isActive) {
            String selectedSubject = (String) subjectDropdown.getSelectedItem();
            String sql = "UPDATE examsData SET isactive = ? WHERE subject = ?";
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz_app", "root", "1234");
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, isActive);
                preparedStatement.setString(2, selectedSubject);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Database updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update database!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

//        public void navigateFunction()
//        {
//            AdminDashboard navFunction = new AdminDashboard();
//            navFunction.navigateFunction();
//        }
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

        public static void main(String[] arg) {
            CreateExam sr = new CreateExam();
            sr.initializeCreateExam();
        }
    }
