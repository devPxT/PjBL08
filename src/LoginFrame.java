import javax.swing.*;
import java.awt.*;
import java.io.*;

public class LoginFrame extends JFrame {
    private JTextField loginField;
    private JPasswordField passwordField;
    private JComboBox<String> userTypeComboBox;

    public LoginFrame() {
        setTitle("Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel userTypeLabel = new JLabel("Tipo de Usuário:");
        String[] userTypes = {"Vendedor", "Cliente"};
        userTypeComboBox = new JComboBox<>(userTypes);

        JLabel loginLabel = new JLabel("Login:");
        loginField = new JTextField();

        JLabel passwordLabel = new JLabel("Senha:");
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Logar");
        loginButton.addActionListener(e -> {
            try {
                Usuario usuarioLogado = login();
                if (usuarioLogado != null) {
                    dispose();
                    if (usuarioLogado instanceof Vendedor) {
                        VendedorFrame vendedorFrame = new VendedorFrame((Vendedor) usuarioLogado);
                        vendedorFrame.setVisible(true);
                    } else {
                        ClienteFrame clienteFrame = new ClienteFrame((Cliente) usuarioLogado);
                        clienteFrame.setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Login, senha ou tipo de usuário inválidos!");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao ler o arquivo de usuários.");
            }
        });

        JButton registerButton = new JButton("Cadastrar");
        registerButton.addActionListener(e -> {
            dispose();
            RegisterFrame registerFrame = new RegisterFrame();
            registerFrame.setVisible(true);
        });

        panel.add(userTypeLabel);
        panel.add(userTypeComboBox);

        panel.add(loginLabel);
        panel.add(loginField);
        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(registerButton);
        panel.add(loginButton);

        add(panel);
    }

    private Usuario login() throws IOException {
        DadosSingleton dados = DadosSingleton.getInstance();

        String login = loginField.getText();
        String senha = new String(passwordField.getPassword());
        String userType = (String) userTypeComboBox.getSelectedItem();
        
        dados.loadUsers();

        for (Usuario usuario : dados.getUsuarios()) {
            if (usuario.getLogin().equals(login) && usuario.getSenha().equals(senha)) {
                if ((usuario instanceof Vendedor && "Vendedor".equals(userType)) || (usuario instanceof Cliente && "Cliente".equals(userType))) {
                    return usuario;
                }
            }
        }
        return null;
    }
}
