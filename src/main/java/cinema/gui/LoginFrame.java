package cinema.gui;

import cinema.service.LoginService;
import cinema.staff.Staff;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final LoginService loginService;

    public LoginFrame() {
        loginService = new LoginService();

        setTitle("Cinema Ticket Management System - Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel =
                new JLabel("Cinema Ticket Management System");

        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel usernameLabel= new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);

        JButton loginButton = new JButton("Login");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        add(panel);

        loginButton.addActionListener(e -> login());
    }

    private void login() {
        String username = usernameField.getText().trim();

        String password =
                new String(passwordField.getPassword());

        Staff staff =
                loginService.login(username, password);

        if (staff != null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Login successful!\nWelcome, "
                        + staff.getRole() + "."
            );

            dispose();

            MainFrame mainFrame =
                    new MainFrame(staff);

            mainFrame.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            passwordField.setText("");
        }
    }
}
