import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.List;

public class RegisterFrame extends JFrame {
    private JTextField nomeField;
    private JFormattedTextField cpfField;
    private JFormattedTextField cnpjField;
    private JFormattedTextField cepField;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JComboBox<String> userTypeComboBox;
    private JPanel painelCEPeCPNJ;
    private JPanel painelCEPeCPNJText;

    public RegisterFrame() {
        DadosSingleton dados = DadosSingleton.getInstance();

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

        try {
            MaskFormatter cnpjFormatter = new MaskFormatter("##.###.###/####-##");
            cnpjFormatter.setPlaceholderCharacter('_');
            cnpjField = new JFormattedTextField(cnpjFormatter);
        } catch (ParseException e) {
            e.printStackTrace();
        }

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

        painelCEPeCPNJText = new JPanel();
        atualizarPainelCEPeCNPJText();

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

        panel.add(painelCEPeCPNJText);
        panel.add(painelCEPeCPNJ);

        panel.add(backButton);
        panel.add(registerButton);

        add(panel);
    }

    private void atualizarPainelCEPeCPNJ() {
        painelCEPeCPNJ.removeAll();
        String userType = (String) userTypeComboBox.getSelectedItem();
        if ("Vendedor".equals(userType)) {
            painelCEPeCPNJ.setLayout(new GridLayout(1, 1));
            painelCEPeCPNJ.add(cnpjField);
        } else {
            painelCEPeCPNJ.setLayout(new GridLayout(1, 1));
            painelCEPeCPNJ.add(cepField);
        }
        painelCEPeCPNJ.revalidate();
        painelCEPeCPNJ.repaint();
        atualizarPainelCEPeCNPJText();
    }

    private void atualizarPainelCEPeCNPJText() {
        painelCEPeCPNJText.removeAll();
        String userType = (String) userTypeComboBox.getSelectedItem();
        if ("Vendedor".equals(userType)) {
            painelCEPeCPNJText.setLayout(new GridLayout(1, 1));
            painelCEPeCPNJText.add(new JLabel("CNPJ:"));
        } else {
            painelCEPeCPNJText.setLayout(new GridLayout(1, 1));
            painelCEPeCPNJText.add(new JLabel("CEP:"));
        }
        painelCEPeCPNJText.revalidate();
        painelCEPeCPNJText.repaint();
    }

    private void cadastrar() throws IOException {
        String nome = nomeField.getText().trim();
        String cpf = cpfField.getText().replaceAll("[^0-9]", "").trim();
        String cnpj = cnpjField.getText().replaceAll("[^0-9]", "").trim();
        String cep = cepField.getText().replaceAll("[^0-9]", "").trim();
        String login = loginField.getText().trim();
        String senha = new String(passwordField.getPassword()).trim();
        String userType = (String) userTypeComboBox.getSelectedItem();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome não pode estar vazio.");
            nomeField.requestFocus();
            return;
        }

        if (cpf.isEmpty() || cpf.length() != 11 || !isCPFValido(cpf)) {
            JOptionPane.showMessageDialog(this, "CPF inválido. Deve ter 11 dígitos e ser um CPF válido.");
            cpfField.requestFocus();
            return;
        }

        if (login.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Login não pode estar vazio.");
            loginField.requestFocus();
            return;
        }

        if (senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Senha não pode estar vazia.");
            passwordField.requestFocus();
            return;
        }

        if (userType.equals("Vendedor")) {
            if (cnpj.isEmpty() || cnpj.length() != 14) {
                JOptionPane.showMessageDialog(this, "CNPJ inválido. Deve ter 14 dígitos.");
                cnpjField.requestFocus();
                return;
            }
        } else {
            if (cep.isEmpty() || cep.length() != 8) {
                JOptionPane.showMessageDialog(this, "CEP inválido. Deve ter 8 dígitos.");
                cepField.requestFocus();
                return;
            }
        }

        if (isLoginRepetido(login, userType)) {
            JOptionPane.showMessageDialog(this, "Login indisponível. Por favor, escolha um login diferente.");
            loginField.requestFocus();
            return;
        }

        Usuario usuario;
        if ("Vendedor".equals(userType)) {
            usuario = new Vendedor(nome, cpf, login, senha, cnpj);
        } else {
            usuario = new Cliente(nome, cpf, login, senha, cep);
        }
        DadosSingleton dados = DadosSingleton.getInstance();

        dados.loadUsers();
        dados.saveUser(usuario);

        JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
    }

    private boolean isLoginRepetido(String login, String userType) {
        DadosSingleton dados = DadosSingleton.getInstance();

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

    private boolean isCPFValido(String cpf) {
    if (cpf.equals("00000000000") || cpf.equals("11111111111") ||
        cpf.equals("22222222222") || cpf.equals("33333333333") ||
        cpf.equals("44444444444") || cpf.equals("55555555555") ||
        cpf.equals("66666666666") || cpf.equals("77777777777") ||
        cpf.equals("88888888888") || cpf.equals("99999999999") ||
       (cpf.length() != 11))
       return(false);

    char dig10, dig11;
    int sm, i, r, num, peso;

    try 
    {
        sm = 0;
        peso = 10;
        for (i=0; i<9; i++) {              
        num = (int)(cpf.charAt(i) - 48); 
        sm = sm + (num * peso);
        peso = peso - 1;
    }
        r = 11 - (sm % 11);
        if ((r == 10) || (r == 11))
            dig10 = '0';
        else dig10 = (char)(r + 48);

        sm = 0;
        peso = 11;
        for (i=0; i<10; i++) {
            num = (int)(cpf.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
        }

        r = 11 - (sm % 11);
        if ((r == 10) || (r == 11))
            dig11 = '0';
        else dig11 = (char)(r + 48);

        if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
            return(true);
        else return(false);
    } catch (InputMismatchException erro) {
        return(false);
    }
}
}