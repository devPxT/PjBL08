import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.util.List;

public class RegisterFrame extends JFrame {
    private Dados dados;

    private JTextField nomeField;
    private JFormattedTextField cpfField;
    private JFormattedTextField cnpjField;
    private JFormattedTextField cepField;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JComboBox<String> userTypeComboBox;
    private JPanel painelCEPeCPNJ;

    public RegisterFrame(Dados dados) {
        this.dados = dados;

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
        try {
            MaskFormatter cpfFormatter = new MaskFormatter("###.###.###-##");
            cpfFormatter.setPlaceholderCharacter('_');
            cpfField = new JFormattedTextField(cpfFormatter);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JLabel cnpjLabel = new JLabel("CNPJ:");
        try {
            MaskFormatter cnpjFormatter = new MaskFormatter("##.###.###/####-##");
            cnpjFormatter.setPlaceholderCharacter('_');
            cnpjField = new JFormattedTextField(cnpjFormatter);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JLabel cepLabel = new JLabel("CEP:");
        try {
            MaskFormatter cepFormatter = new MaskFormatter("#####-###");
            cepFormatter.setPlaceholderCharacter('_');
            cepField = new JFormattedTextField(cepFormatter);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JLabel loginLabel = new JLabel("Login:");
        loginField = new JTextField();

        JLabel passwordLabel = new JLabel("Senha:");
        passwordField = new JPasswordField();

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
            LoginFrame loginFrame = new LoginFrame(dados);
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
        panel.add(new JLabel(""));

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
        String nome = nomeField.getText().trim();
        String cpf = cpfField.getText().replaceAll("[^0-9]", "").trim();
        String cnpj = cnpjField.getText().replaceAll("[^0-9]", "").trim();
        String cep = cepField.getText().replaceAll("[^0-9]", "").trim();
        String login = loginField.getText().trim();
        String senha = new String(passwordField.getPassword()).trim();
        String userType = (String) userTypeComboBox.getSelectedItem();

        // Validando campos obrigatórios
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome não pode estar vazio.");
            return;
        }

        if (cpf.isEmpty() || cpf.length() != 11) {
            JOptionPane.showMessageDialog(this, "CPF inválido. Deve ter 11 dígitos.");
            return;
        }

        if (login.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Login não pode estar vazio.");
            return;
        }

        if (senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Senha não pode estar vazia.");
            return;
        }

        if (userType.equals("Vendedor")) {
            if (cnpj.isEmpty() || cnpj.length() != 14) {
                JOptionPane.showMessageDialog(this, "CNPJ inválido. Deve ter 14 dígitos.");
                return;
            }
        } else {
            if (cep.isEmpty() || cep.length() != 8) {
                JOptionPane.showMessageDialog(this, "CEP inválido. Deve ter 8 dígitos.");
                return;
            }
        }

        if (isLoginRepetido(login, userType)) {
            JOptionPane.showMessageDialog(this, "Login indisponível. Por favor, escolha um login diferente.");
            return;
        }

        Usuario usuario;
        if ("Vendedor".equals(userType)) {
            usuario = new Vendedor(nome, cpf, login, senha, cnpj);
        } else {
            usuario = new Cliente(nome, cpf, login, senha, cep);
        }

        dados.loadUsers();
        dados.saveUser(usuario);

        JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
    }

    private boolean isLoginRepetido(String login, String userType) {
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