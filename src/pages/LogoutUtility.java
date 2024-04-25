package pages;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogoutUtility {

    public static void addLogoutListener(JButton logoutButton) {
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    // If user clicks Yes, perform logout
                    performLogout(logoutButton);
                }
            }
        });
    }

    private static void performLogout(JButton logoutButton) {

        QuizLoginPage loginPage = new QuizLoginPage();
        loginPage.initializeQuizLoginPage();
        // Close the current window
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(logoutButton);
        frame.dispose();

    }
}
