import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;

public class RegisterFrame extends JFrame {
    private JTextField nomeField;
    private JTextField cpfField;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JComboBox<String> userTypeComboBox;
    private JTextField cnpjField;
    private JTextField cepField;
    private JPanel painelCEPeCPNJ;

    public RegisterFrame() {
        setTitle("Cadastro");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));

        JLabel userTypeLabel = new JLabel("Tipo de Usuário:");
        String[] userTypes = {"Vendedor", "Cliente"};
        userTypeComboBox = new JComboBox<>(userTypes);
        userTypeComboBox.addActionListener(e -> atualizarPainelCEPeCPNJ());

        JLabel nomeLabel = new JLabel("Nome:");
        nomeField = new JTextField();

        JLabel cpfLabel = new JLabel("CPF:");
        cpfField = new JTextField();

        JLabel loginLabel = new JLabel("Login:");
        loginField = new JTextField();

        JLabel passwordLabel = new JLabel("Senha:");
        passwordField = new JPasswordField();

        cnpjField = new JTextField();
        cepField = new JTextField();

        painelCEPeCPNJ = new JPanel();
        atualizarPainelCEPeCPNJ();

        JButton registerButton = new JButton("Cadastrar");
        registerButton.addActionListener(e -> {
            try {
                cadastrar();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar os dados do usuário.");
            }
        });

        JButton backButton = new JButton("Voltar");
        backButton.addActionListener(e -> {
            dispose();
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });

        panel.add(userTypeLabel);
        panel.add(userTypeComboBox);

        panel.add(nomeLabel);
        panel.add(nomeField);
        panel.add(cpfLabel);
        panel.add(cpfField);

        panel.add(loginLabel);
        panel.add(loginField);
        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(painelCEPeCPNJ);
        panel.add(new JLabel("")); //dirty fix no layout do campo CEP/CNPJ

        panel.add(backButton);
        panel.add(registerButton);

        add(panel);
    }

    private void atualizarPainelCEPeCPNJ() {
        painelCEPeCPNJ.removeAll();
        String userType = (String) userTypeComboBox.getSelectedItem();
        if ("Vendedor".equals(userType)) {
            painelCEPeCPNJ.setLayout(new GridLayout(1, 2));
            painelCEPeCPNJ.add(new JLabel("CNPJ:"));
            painelCEPeCPNJ.add(cnpjField);
        } else {
            painelCEPeCPNJ.setLayout(new GridLayout(1, 2));
            painelCEPeCPNJ.add(new JLabel("CEP:"));
            painelCEPeCPNJ.add(cepField);
        }
        painelCEPeCPNJ.revalidate();
        painelCEPeCPNJ.repaint();
    }

    private void cadastrar() throws IOException {
        String nome = nomeField.getText();
        String cpf = cpfField.getText();
        String login = loginField.getText();
        String senha = new String(passwordField.getPassword());
        String userType = (String) userTypeComboBox.getSelectedItem();

        String cepORcnpj = userType.equals("Vendedor") ? cnpjField.getText() : cepField.getText();
        if (cepORcnpj.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, userType.equals("Vendedor") ? "CNPJ não pode estar vazio" : "CEP não pode estar vazio");
            return;
        }

        if (isLoginRepetido(login, userType)) {
            JOptionPane.showMessageDialog(this, "Login indisponível. Por favor, escolha um login diferente.");
            return;
        }

        Usuario usuario;
        if ("Vendedor".equals(userType)) {
            usuario = new Vendedor(nome, cpf, login, senha, cepORcnpj);
        } else {
            usuario = new Cliente(nome, cpf, login, senha, cepORcnpj);
        }
        // usuarios.add(usuario);
        // saveUsers();

        Dados dados = new Dados();
        dados.loadUsers();

        dados.saveUsers(usuario);

        JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
    }

    private boolean isLoginRepetido(String login, String userType) {
        Dados dados = new Dados();
        dados.loadUsers();

        List<Usuario> usuarios = dados.getUsuarios();
        if (usuarios != null) {
            for (Usuario usuario : usuarios) {
                if (usuario.getLogin().equals(login) &&
                    ((usuario instanceof Vendedor && "Vendedor".equals(userType)) || (usuario instanceof Cliente && "Cliente".equals(userType)))) {
                    return true;
                }
            }
        }
        
        return false;
    }
}
