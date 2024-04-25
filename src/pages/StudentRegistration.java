package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class StudentRegistration extends JFrame {

    public int w = 1540, h = 1200;
    public JPanel registrationPanel, navPanel;
    public JButton register, logoutbtn;
    public JLabel sName, sEmail, sDept, sPRN, sPassword;
    public JTextField sNameF, sEmailF, sPRNF;
    public JPasswordField sPasswordF;
    public JComboBox<String> depts;
    String[] departments = {"AIDS", "CSE", "ENTC", "CHEM", "IT", "INSTRU"};
    private DBstudRegistration dbRegistration;
    String userNme  = new QuizLoginPage().getusername();

    public JLabel profile_Name ;
    public JComboBox<String> navigationDropDown;


    public StudentRegistration() {
        dbRegistration = new DBstudRegistration();
    }

    public void initializeStudentRegistration() {
        setTitle("Student Registration");
        setSize(w, h);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Panels
        navPanel = new QuizLoginPage().createNavbarPanel("Student Registration");
        registrationPanel = new AdminDashboard().mainPanel();
        registrationPanel.setBounds(w / 4, 160, w / 2, h / 2);
        logoutbtn = new AdminDashboard().logOutButton();

        // Labels
        sName = new JLabel("Name:");
        sName.setFont(new Font("Arial", Font.BOLD, 24));
        sEmail = new JLabel("Email:");
        sEmail.setFont(new Font("Arial", Font.BOLD, 24));
        sDept = new JLabel("Department:");
        sDept.setFont(new Font("Arial", Font.BOLD, 24));
        sPRN = new JLabel("PRN:");
        sPRN.setFont(new Font("Arial", Font.BOLD, 24));
        sPassword = new JLabel("Password:");
        sPassword.setFont(new Font("Arial", Font.BOLD, 24));

        // Text Fields
        sNameF = new JTextField();
        sNameF.setFont(new Font("Arial", Font.BOLD, 24));
        sEmailF = new JTextField();
        sEmailF.setFont(new Font("Arial", Font.BOLD, 24));
        depts = new JComboBox<>(departments);
        depts.setFont(new Font("Arial", Font.BOLD, 24));
        sPRNF = new JTextField();
        sPRNF.setFont(new Font("Arial", Font.BOLD, 24));
        sPasswordF = new JPasswordField();
        sPasswordF.setFont(new Font("Arial", Font.BOLD, 24));

        // Register Button
        register = new JButton("Register");
        register.setFont(new Font("Arial", Font.BOLD, 24));
        register.addActionListener(e -> registerStudent());

        // Set bounds for labels and text fields
        sName.setBounds(50, 50, 150, 50);
        sNameF.setBounds(200, 50, 300, 50);
        sEmail.setBounds(50, 150, 150, 50);
        sEmailF.setBounds(200, 150, 300, 50);
        sDept.setBounds(50, 250, 150, 50);
        depts.setBounds(200, 250, 200, 50);
        sPRN.setBounds(50, 350, 150, 50);
        sPRNF.setBounds(200, 350, 300, 50);
        sPassword.setBounds(50, 450, 150, 50);
        sPasswordF.setBounds(200, 450, 300, 50);
        register.setBounds(w / 4 - 100, 540, 200, 50);

        profile_Name = new CreateExam().profile(userNme);
        // Add components to the registration panel
        registrationPanel.add(sName);
        registrationPanel.add(sNameF);
        registrationPanel.add(sEmail);
        registrationPanel.add(sEmailF);
        registrationPanel.add(sDept);
        registrationPanel.add(depts);
        registrationPanel.add(sPRN);
        registrationPanel.add(sPRNF);
        registrationPanel.add(sPassword);
        registrationPanel.add(sPasswordF);
        registrationPanel.add(register);

        // Add components to the nav panel
        navigationDropDown = new AdminDashboard().navDrop();

        navPanel.add(navigationDropDown);

        LogoutUtility.addLogoutListener(logoutbtn);
        navPanel.add(logoutbtn);

        navPanel.add(profile_Name);


        // Add panels to the frame
        add(registrationPanel);
        add(navPanel);

        setVisible(true);
        navigateFunction();
    }

    private void registerStudent() {
        String name = sNameF.getText();
        String email = sEmailF.getText();
        String dept = (String) depts.getSelectedItem();
        String prn = sPRNF.getText();
        String password = new String(sPasswordF.getPassword());

        // Check if PRN already exists
        if (dbRegistration.isPrnExists(prn)) {
            JOptionPane.showMessageDialog(this, "PRN already exists! Please retry with a different PRN.",
                    "Duplicate PRN", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Insert student data into the database
        if (dbRegistration.addStudent(name, email, dept, prn, password)) {
            JOptionPane.showMessageDialog(this, "Student registered successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            // Clear the text fields after successful registration
            sNameF.setText("");
            sEmailF.setText("");
            sPRNF.setText("");
            sPasswordF.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to register student!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
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

    public static void main(String[] arg) {
        StudentRegistration sr = new StudentRegistration();
        sr.initializeStudentRegistration();
    }
}
