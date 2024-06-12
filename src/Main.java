import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Dados dados = new Dados();

        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame(dados);
            loginFrame.setVisible(true);
        });
    }
}
