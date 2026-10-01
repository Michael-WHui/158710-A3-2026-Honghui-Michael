package cinema.gui;

import cinema.staff.Staff;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final Staff loggedInStaff;

    public MainFrame(Staff staff) {

        this.loggedInStaff = staff;

        setTitle("Cinema Ticket Management System");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());

        JLabel userLabel = new JLabel(
                "Logged in as: "
                    + staff.getUsername()
                    + " (" + staff.getRole() + ")"
        );

        userLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        JButton logoutButton = new JButton("Logout");

        topPanel.add(userLabel, BorderLayout.WEST);
        topPanel.add(logoutButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        MoviePanel moviePanel =
                new MoviePanel(loggedInStaff);

        add(moviePanel, BorderLayout.CENTER);

        logoutButton.addActionListener(e -> logout());
    }

    private void logout() {

        dispose();

        LoginFrame loginFrame =
                new LoginFrame();

        loginFrame.setVisible(true);
    }
}
