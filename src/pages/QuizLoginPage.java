package pages;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class QuizLoginPage extends JFrame {
//    public JFrame login_frame;
    public JPanel adminPanel, navbarPanel;
    public JButton adminLoginButton, adminSignInButton;
    public JLabel adminUsernameLabel, adminPasswordLabel, titleLabel, adminTitleLabel,navbarLabel;
    public JTextField adminUsernameField;
    public JPasswordField adminPasswordField;
    public JComboBox<String> admin_stud;
    String [] adminStud = {"Admin", "Student"};
    int w = 1540, h = 1200;

    public JFrame frame()
    {
        JFrame mainFrame = new JFrame("Login Page");

        mainFrame.setSize(w, h);
        mainFrame.setLocationRelativeTo(null);

        return mainFrame;

    }

    public JPanel createNavbarPanel(String title)
    {
        navbarPanel = new JPanel();
        navbarPanel.setLayout(null);
        navbarPanel.setBounds(0, 0, w, 100);
        navbarPanel.setBackground(Color.BLUE);

        // Title labels
        titleLabel = new JLabel("Quizee");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(25, 25, 200, 50); // Centered horizontally, 25 pixels from the top
        titleLabel.setFont(new Font("Arial", Font.BOLD, 34)); // Set font size to 24 and style to bold
        titleLabel.setForeground(Color.WHITE); // Set font color to white

        // NavBar  label

        navbarLabel = new JLabel(title);
        navbarLabel.setHorizontalAlignment(SwingConstants.CENTER);
        navbarLabel.setVerticalAlignment(SwingConstants.CENTER);
        navbarLabel.setBounds(w/2-175, 25, 350, 50); // Centered horizontally, 25 pixels from the top
        navbarLabel.setFont(new Font("Arial", Font.BOLD, 34)); // Set font size to 24 and style to bold
        navbarLabel.setForeground(Color.WHITE); // Set font color to white

        navbarPanel.add(titleLabel);
        navbarPanel.add(navbarLabel);

        return navbarPanel;


    }
   public static String userNameforProfile;

    public void initializeQuizLoginPage() {



        // Fonts
        Font labelFont = new Font("Arial", Font.BOLD, 24);




        // Panels
        adminPanel = new JPanel();
        adminPanel.setLayout(null);



        adminPanel.setBounds(w / 2 - w/6, h / 8, w / 3, h / 2);


        adminPanel.setBackground(Color.LIGHT_GRAY);

        // DropDown
        admin_stud = new JComboBox<>(adminStud);
        admin_stud.setBounds(300,25,150,50 );
        admin_stud.setFont(new Font("Arial", Font.BOLD, 27));
        adminPanel.add(admin_stud);


        // Fields
        adminUsernameField = new JTextField();
        adminUsernameField.setBounds(250, 200, 200, 30);
        adminUsernameField.setFont(new Font("Arial", Font.BOLD, 24));
        adminPasswordField = new JPasswordField();
        adminPasswordField.setBounds(250, 300, 200, 30);
        adminPasswordField.setFont(new Font("Arial", Font.BOLD, 24));




        // Panel labels
        adminTitleLabel= new JLabel("Login as: ");
        adminTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        adminTitleLabel.setVerticalAlignment(SwingConstants.CENTER);
        adminTitleLabel.setBounds(80, 25, 300, 50); // Centered horizontally, 25 pixels from the top
        adminTitleLabel.setFont(new Font("Arial", Font.BOLD, 27)); // Set font size to 24 and style to bold

        // Labels

        adminUsernameLabel = new JLabel("Username");
        adminUsernameLabel.setBounds(100, 200, 220, 30);
        adminUsernameLabel.setFont(labelFont);

        adminPasswordLabel = new JLabel("Password");
        adminPasswordLabel.setBounds(100, 300, 220, 30);
        adminPasswordLabel.setFont(labelFont);


        // Buttons
        adminLoginButton = new JButton("Login");
        adminLoginButton.setBounds(w/6 - 70, 400, 140, 50);
        adminLoginButton.setFont(labelFont);

        adminLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = adminUsernameField.getText();
                String password = new String(adminPasswordField.getPassword());
                String userType = (String) admin_stud.getSelectedItem();
                setUserName(username);
//                System.out.println(userNameforProfile);
                authenticateUser(username, password, userType);
            }



        });
        System.out.println(getusername());
        adminSignInButton = new JButton("Sign In");
        adminSignInButton.setBounds(w/6 - 70, 500, 140, 50);
        adminSignInButton.setFont(labelFont);




        // Add components to panels
        adminPanel.add(adminUsernameLabel);
        adminPanel.add(adminPasswordLabel);
        adminPanel.add(adminUsernameField);
        adminPanel.add(adminPasswordField);
        adminPanel.add(adminLoginButton);
        adminPanel.add(adminSignInButton);
        adminPanel.add(adminTitleLabel);


        JFrame mainFrame = frame();
        // Add panels to frame
        mainFrame.add(adminPanel);
        navbarPanel = createNavbarPanel("Login/Sign in");
        mainFrame.add(navbarPanel);

        mainFrame.setLayout(null);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public String getusername(){
        System.out.println(userNameforProfile);
        return userNameforProfile;

    }
    public void setUserName(String username)
    {
        userNameforProfile = username;
    }
    public void authenticateUser(String username, String password, String userType) {
        DatabaseConnector dbConnector = new DatabaseConnector();
        userNameforProfile= username;

        String hashedPassword = hashPassword(password); // Hash the entered password

        boolean isAuthenticated = dbConnector.authenticateUser(username, hashedPassword, userType);

        if (isAuthenticated) {
            if (userType.equals("Admin")) {
                AdminDashboard adminDashboard = new AdminDashboard();
                adminDashboard.initializeAdminDashboard();
                dispose();
            } else if (userType.equals("Student")) {
                StudentDashBoard studentDashboard = new StudentDashBoard();
                studentDashboard.initializeStudentDashBoard();
                dispose();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password!", "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void main(String[] args) {
       QuizLoginPage LoginPage =new QuizLoginPage();
       LoginPage.initializeQuizLoginPage();
    }
}
