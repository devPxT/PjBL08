import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        new Dados();

        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}