package cinema;

import cinema.gui.LoginFrame;

import javax.swing.*;

public class main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            LoginFrame loginFrame =
                    new LoginFrame();

            loginFrame.setVisible(true);
        });
    }
}
