import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String USERS_FILE = "users.dat"; //ARQUIVO DE ARMAZENAMENTO DE TODOS OS USUARIOS
    private static List<Usuario> usuarios = new ArrayList<>();

    private static List<Produto> estoque = new ArrayList<>();
    private static List<Produto> carrinho = new ArrayList<>();

    private static int nextId = 1;

    public static void main(String[] args) {
        loadUsers(); //carrega os usuarios do arquivo .dat
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }

    public static int getNextId() {
        return nextId;
    }

    public static void incrementNextId() {
        nextId++;
    }

    private static void loadUsers() {
        File file = new File(USERS_FILE);
        if (file.length() == 0) {
            return; //arquivo vazio ent n tem nada pra carregar
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            usuarios = (List<Usuario>) ois.readObject();
        } catch (FileNotFoundException e) {
            //arquivo não existe, ent começa com uma lista vazia
            usuarios = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class LoginFrame extends JFrame {
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
                    if (login()) {
                        dispose();
                        String userType = (String) userTypeComboBox.getSelectedItem();
                        if ("Vendedor".equals(userType)) {
                            VendedorFrame vendedorFrame = new VendedorFrame();
                            vendedorFrame.setVisible(true);
                        } else {
                            ClienteFrame clienteFrame = new ClienteFrame();
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

        private boolean login() throws IOException {
            String login = loginField.getText();
            String senha = new String(passwordField.getPassword());
            String userType = (String) userTypeComboBox.getSelectedItem();

            for (Usuario usuario : usuarios) {
                if (usuario.getLogin().equals(login) && usuario.getSenha().equals(senha)) {
                    if ((usuario instanceof Vendedor && "Vendedor".equals(userType)) || (usuario instanceof Cliente && "Cliente".equals(userType))) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    static class RegisterFrame extends JFrame {
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

            usuarios.add(usuario);
            saveUsers();
            JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
        }

        private boolean isLoginRepetido(String login, String userType) {
            for (Usuario usuario : usuarios) {
                if (usuario.getLogin().equals(login) &&
                   ((usuario instanceof Vendedor && "Vendedor".equals(userType)) || (usuario instanceof Cliente && "Cliente".equals(userType)))) {
                    return true;
                }
            }
            return false;
        }
    }


    static class VendedorFrame extends JFrame {
        public VendedorFrame() {
            setTitle("Menu Vendedor");
            setSize(640, 480);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(4, 1));

            JButton listarButton = new JButton("Listar Produtos");
            listarButton.addActionListener(e -> listarEstoque());

            JButton buscarButton = new JButton("Buscar Produto por ID");
            buscarButton.addActionListener(e -> buscarProdutoPorId());

            JButton cadastrarButton = new JButton("Cadastrar Produto");
            cadastrarButton.addActionListener(e -> cadastrarProduto());

            panel.add(listarButton);
            panel.add(buscarButton);
            panel.add(cadastrarButton);
            panel.add(deslogarButton());

            add(panel);
        }

        private void listarEstoque() {
            StringBuilder message = new StringBuilder();
            if (estoque.isEmpty()) {
                message.append("O estoque está vazio.");
            } else {
                for (Produto produto : estoque) {
                    message.append(produto.imprimeDescricao()).append("\n");
                }
            }
            JOptionPane.showMessageDialog(this, message.toString());
        }

        private void buscarProdutoPorId() {
            String idStr = JOptionPane.showInputDialog("Digite o ID do produto:");
            try {
                int id = Integer.parseInt(idStr);
                for (Produto produto : estoque) {
                    if (produto.getIdProduto() == id) {
                        JOptionPane.showMessageDialog(this, "Produto encontrado:\n" + produto.imprimeDescricao());
                        return;
                    }
                }
                JOptionPane.showMessageDialog(this, "Produto não encontrado.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID inválido.");
            }
        }

        private void cadastrarProduto() {
            JDialog dialog = new JDialog(this, "Escolha o tipo de produto a cadastrar:", true);
            dialog.setLayout(new BorderLayout());
            dialog.setSize(300, 300);
            dialog.setLocationRelativeTo(this);
        
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(6, 1)); // 6 rows, 1 column
        
            String[] options = {"Roupa", "Computador", "Celular", "Carro", "Moto", "Eletrodoméstico"};
            for (int i = 0; i < options.length; i++) {
                JButton button = new JButton(options[i]);
                final int optionIndex = i;
                button.addActionListener(e -> {
                    switch (optionIndex) {
                        case 0:
                            cadastrarRoupa();
                            break;
                        case 1:
                            cadastrarComputador();
                            break;
                        case 2:
                            cadastrarCelular();
                            break;
                        case 3:
                            cadastrarCarro();
                            break;
                        case 4:
                            cadastrarMoto();
                            break;
                        case 5:
                            cadastrarEletroDomestico();
                            break;
                        default:
                            JOptionPane.showMessageDialog(panel, "Opção inválida.");
                    }
                    incrementNextId();
                });
                panel.add(button);
            }
        
            JButton cancelButton = new JButton("Cancelar");
            cancelButton.addActionListener(e -> dialog.dispose());
        
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(cancelButton);
        
            dialog.add(panel, BorderLayout.CENTER);
            dialog.add(buttonPanel, BorderLayout.SOUTH);
        
            dialog.setVisible(true);
        }
        
        private void cadastrarRoupa() {
            boolean cancel = false;
            while (true) {
                try {
                    String preco = inputWithCheck("Informe o preço:");
                    if (preco == null) {
                        cancel = true;
                        break; // User pressed cancel
                    }
                    double newPreco = Double.parseDouble(preco);
        
                    String genero = inputWithCheck("Informe o gênero:");
                    if (genero == null) {
                        cancel = true;
                        break;
                    }
        
                    String material = inputWithCheck("Informe o material:");
                    if (material == null) {
                        cancel = true;
                        break;
                    }
        
                    String cor = inputWithCheck("Informe a cor:");
                    if (cor == null) {
                        cancel = true;
                        break;
                    }
        
                    String marca = inputWithCheck("Informe a marca:");
                    if (marca == null) {
                        cancel = true;
                        break;
                    }

                    String tamanho = inputWithCheck("Informe o tamanho:");
                    if (tamanho == null) {
                        cancel = true;
                        break;
                    }
        
                    Produto roupa = new Roupa(getNextId(), newPreco, genero, material, cor, marca, tamanho);
                    estoque.add(roupa);
        
                    JOptionPane.showMessageDialog(this, "Roupa cadastrada com sucesso.");
                    break;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Preço inválido. Por favor, insira um valor válido.");
                }
            }
            if (cancel) {
                JOptionPane.showMessageDialog(this, "Cadastro de roupa cancelado.");
            }
        }
        
        private String inputWithCheck(String message) {
            while (true) {
                String input = JOptionPane.showInputDialog(message);
                if (input == null) {
                    return null; // usuario clicou no cancel
                }
                if (input.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Entrada inválida. Por favor, tente novamente.");
                } else {
                    return input;
                }
            }
        }

        private void cadastrarComputador() {
            boolean cancel = false;
            while (true) {
                try {
                    String preco = inputWithCheck("Informe o preço:");
                    if (preco == null) {
                        cancel = true;
                        break;
                    }
                    
                    double newPreco = Double.parseDouble(preco);

                    String sistemaOperacional = inputWithCheck("Informe o sistema operacional:");
                    if (sistemaOperacional == null) {
                        cancel = true;
                        break;
                    }

                    String fonte = inputWithCheck("Informe a fonte (em watts):");
                    if (fonte == null) {
                        cancel = true;
                        break;
                    }
                    int newFonte = Integer.parseInt(fonte);
                 

                    String conectividade = inputWithCheck("Informe a conectividade:");
                    if (conectividade == null) {
                        cancel = true;
                        break;
                    }
                    
                    String garantia = inputWithCheck("Informe a garantia (em meses):");
                    if (garantia == null) {
                        cancel = true;
                        break;
                    }
                    int newGarantia = Integer.parseInt(garantia);

                    boolean mouse = JOptionPane.showConfirmDialog(this, "Possui mouse?") == JOptionPane.YES_OPTION;
                    // if (mouse == null) {
                    //     cancel = true;
                    //     break;
                    // }

                    Produto computador = new Computador(getNextId(), newPreco, sistemaOperacional, newFonte, conectividade, newGarantia, mouse);
                    estoque.add(computador);

                    JOptionPane.showMessageDialog(this, "Computador cadastrado com sucesso.");
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Entrada inválida.");
                }
            }
            if (cancel) {
                JOptionPane.showMessageDialog(this, "Cadastro de computador cancelado.");
            }
        }

        private void cadastrarCelular() {
            try {
                double preco = Double.parseDouble(JOptionPane.showInputDialog("Informe o preço:"));
                String sistemaOperacional = JOptionPane.showInputDialog("Informe o sistema operacional:");
                int fonte = Integer.parseInt(JOptionPane.showInputDialog("Informe a fonte (em watts):"));
                String conectividade = JOptionPane.showInputDialog("Informe a conectividade:");
                int garantia = Integer.parseInt(JOptionPane.showInputDialog("Informe a garantia (em meses):"));
                float bateria = Float.parseFloat(JOptionPane.showInputDialog("Informe a capacidade da bateria (em mAh):"));

                Produto celular = new Celular(getNextId(), preco, sistemaOperacional, fonte, conectividade, garantia, bateria);
                estoque.add(celular);

                JOptionPane.showMessageDialog(this, "Celular cadastrado com sucesso.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Entrada inválida.");
            }
        }

        private void cadastrarCarro() {
            try {
                double preco = Double.parseDouble(JOptionPane.showInputDialog("Informe o preço:"));
                String marca = JOptionPane.showInputDialog("Informe a marca:");
                String modelo = JOptionPane.showInputDialog("Informe o modelo:");
                int ano = Integer.parseInt(JOptionPane.showInputDialog("Informe o ano:"));
                int portas = Integer.parseInt(JOptionPane.showInputDialog("Informe o número de portas:"));
                float tamanhoPortaMalas = Float.parseFloat(JOptionPane.showInputDialog("Informe o tamanho do porta-malas (em litros):"));

                Produto carro = new Carro(getNextId(), preco, marca, modelo, ano, portas, tamanhoPortaMalas);
                estoque.add(carro);

                JOptionPane.showMessageDialog(this, "Carro cadastrado com sucesso.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Entrada inválida.");
            }
        }

        private void cadastrarMoto() {
            try {
                double preco = Double.parseDouble(JOptionPane.showInputDialog("Informe o preço:"));
                String marca = JOptionPane.showInputDialog("Informe a marca:");
                String modelo = JOptionPane.showInputDialog("Informe o modelo:");
                int ano = Integer.parseInt(JOptionPane.showInputDialog("Informe o ano:"));
                String tipo = JOptionPane.showInputDialog("Informe o tipo:");

                Produto moto = new Moto(getNextId(), preco, marca, modelo, ano, tipo);
                estoque.add(moto);

                JOptionPane.showMessageDialog(this, "Moto cadastrada com sucesso.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Entrada inválida.");
            }
        }

        private void cadastrarEletroDomestico() {
            try {
                double preco = Double.parseDouble(JOptionPane.showInputDialog("Informe o preço:"));
                String marca = JOptionPane.showInputDialog("Informe a marca:");
                String modelo = JOptionPane.showInputDialog("Informe o modelo:");
                float volume = Float.parseFloat(JOptionPane.showInputDialog("Informe o volume em litros:"));
                String eficienciaEnergetica = JOptionPane.showInputDialog("Informe a Eficiência Energética:");

                Produto eletroDomestico = new EletroDomestico(getNextId(), preco, marca, modelo, volume, eficienciaEnergetica);
                estoque.add(eletroDomestico);

                JOptionPane.showMessageDialog(this, "Eletrodoméstico cadastrado com sucesso.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Entrada inválida.");
            }
        }
    }

    static class ClienteFrame extends JFrame {
        public ClienteFrame() {
            setTitle("Menu Cliente");
            setSize(512, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(4, 1));

            JButton listarButton = new JButton("Listar Produtos");
            listarButton.addActionListener(e -> listarEstoque());

            JButton adicionarButton = new JButton("Adicionar Produto ao Carrinho");
            adicionarButton.addActionListener(e -> adicionarProdutoAoCarrinho());

            JButton visualizarButton = new JButton("Visualizar Carrinho");
            visualizarButton.addActionListener(e -> visualizarCarrinho());

            panel.add(listarButton);
            panel.add(adicionarButton);
            panel.add(visualizarButton);
            panel.add(deslogarButton());

            add(panel);
        }

        private void listarEstoque() {
            StringBuilder message = new StringBuilder();
            if (estoque.isEmpty()) {
                message.append("O estoque está vazio.");
            } else {
                for (Produto produto : estoque) {
                    message.append(produto.imprimeDescricao()).append("\n");
                }
            }
            JOptionPane.showMessageDialog(this, message.toString());
        }

        private void adicionarProdutoAoCarrinho() {
            String idStr = JOptionPane.showInputDialog("Digite o ID do produto que deseja adicionar ao carrinho:");
            try {
                int id = Integer.parseInt(idStr);
                for (Produto produto : estoque) {
                    if (produto.getIdProduto() == id) {
                        carrinho.add(produto);
                        JOptionPane.showMessageDialog(this, "Produto adicionado ao carrinho.");
                        return;
                    }
                }
                JOptionPane.showMessageDialog(this, "Produto não encontrado.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID inválido.");
            }
        }

        private void visualizarCarrinho() {
            StringBuilder message = new StringBuilder();
            if (carrinho.isEmpty()) {
                message.append("O carrinho está vazio.");
            } else {
                for (Produto produto : carrinho) {
                    message.append(produto.imprimeDescricao()).append("\n");
                }
            }
            JOptionPane.showMessageDialog(this, message.toString());
        }
    }
    
    private static JButton deslogarButton() {
        JButton logoutButton = new JButton("Deslogar");
        logoutButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            });
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(logoutButton);
            topFrame.dispose();
        });
        return logoutButton;
    }
}