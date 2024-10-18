import javax.swing.*;
import java.awt.*;

public class Notificacao {
    private JFrame frame;
    private JPanel notificacaoPanel;
    
    public Notificacao(String mensagem, Color corDeFundo, int posX, int posY) {
        frame = new JFrame();
        frame.setUndecorated(true);
        frame.setSize(450, 70);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocation(posX, posY); 
        frame.setLayout(new BorderLayout());
        frame.setAlwaysOnTop(true);

        notificacaoPanel = new JPanel();
        notificacaoPanel.setLayout(new FlowLayout());
        notificacaoPanel.setBackground(corDeFundo);
        notificacaoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel label = new JLabel(mensagem);
        label.setForeground(Color.WHITE);
        notificacaoPanel.add(label);

        frame.add(notificacaoPanel, BorderLayout.CENTER);
        frame.setVisible(true);

        Timer timer = new Timer(5000, e -> frame.dispose());
        timer.setRepeats(false);
        timer.start();
    }
}
