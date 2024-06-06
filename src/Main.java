import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String USERS_FILE = "users.txt";
    private static List<Produto> estoque = new ArrayList<>();
    private static List<Produto> carrinho = new ArrayList<>();
    private static int nextId = 1;

    public static void main(String[] args) {
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

    static class LoginFrame extends JFrame {
        private JTextField loginField;
        private JPasswordField passwordField;
        private JComboBox<String> userTypeComboBox;

        public LoginFrame() {
            setTitle("Login");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(6, 2));

            JLabel userTypeLabel = new JLabel("Tipo de Usuário:");
            String[] userTypes = {"Vendedor", "Cliente"};
            userTypeComboBox = new JComboBox<>(userTypes);

            JLabel loginLabel = new JLabel("Login:");
            loginField = new JTextField();

            JLabel passwordLabel = new JLabel("Senha:");
            passwordField = new JPasswordField();

            JButton loginButton = new JButton("Logar");

            JButton registerButton = new JButton("Cadastrar");

            registerButton.addActionListener(e -> {
                try {
                    cadastrar();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar os dados do usuário.");
                }
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

        private void cadastrar() throws IOException {
            String login = loginField.getText();
            String senha = new String(passwordField.getPassword());
            String userType = (String) userTypeComboBox.getSelectedItem();

            if (isLoginRepetido(userType, login)) {
                JOptionPane.showMessageDialog(this, "Login indisponível. Por favor, escolha um login diferente.");
                return;
            }

            createUsersFileIfNeed();

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
                bw.write(userType + "," + login + "," + senha);
                bw.newLine();
            }

            JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
        }

        private boolean isLoginRepetido(String userType, String login) throws IOException {
            createUsersFileIfNeed();

            try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] user = line.split(",");
                    if (user[0].equals(userType) && user[1].equals(login)) {
                        return true;
                    }
                }
            }
            return false;
        }

        private void createUsersFileIfNeed() throws IOException {
            File file = new File(USERS_FILE);
            if (!file.exists()) {
                file.createNewFile();
            }
        }
    }
}

    //private static boolean login(Scanner scanner) throws IOException {
        //int tipoUsuario = getTipoUsuario(scanner);
        //System.out.print("Digite o login: ");
        //String login = scanner.nextLine();
        //System.out.print("Digite a senha: ");
        //String senha = scanner.nextLine();

        // try (BufferedReader br = new BufferedReader(new FileReader(Users_File))) {
        //BufferedReader br = new BufferedReader(new FileReader(Users_File));
        //String line;
        //while ((line = br.readLine()) != null) {
            //String[] user = line.split(",");
            //if (Integer.parseInt(user[0]) == tipoUsuario && user[1].equals(login) && user[2].equals(senha)) {
                //br.close();
                //System.out.println("Login bem-sucedido!");
                //return true;
            //}
        //}
        // } catch (IOException e) {
        //     System.out.println("Erro ao ler o arquivo de usuários.");
        // }print
        //br.close();
        //System.out.println("Login ou senha inválidos!");
        //return false;
    //}
            // } catch (IOException e) {
            //     System.out.println("Erro ao salvar os dados do usuário.");
            // }

    /*private static boolean isLoginRepetido(int userType, String login) {
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] user = line.split(",");
                if (Integer.parseInt(user[0]) == userType && user[1].equals(login)) {
                    br.close();
                    return true;
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de usuários.");
        }
        return false;
    }

    //private static int getTipoUsuario(Scanner scanner) {
        //int tipoUsuario;
        //do {
            //System.out.println("1. Vendedor");
            //System.out.println("2. Cliente");
            //System.out.print("Digite o tipo de usuário (1 ou 2): ");
            //tipoUsuario = scanner.nextInt();
            //scanner.nextLine(); // Limpar o buffer de entrada
            //if (tipoUsuario != 1 && tipoUsuario != 2) {
                //System.out.println("Entrada inválida! Por favor, insira 1 para Vendedor ou 2 para Cliente.");
            //}
        //} while (tipoUsuario != 1 && tipoUsuario != 2);
        //return tipoUsuario;
    //}

    private static void menuPrincipal(Scanner scanner) {
        int choice;

        do {
            // Menu principal
            System.out.println("\n=== Menu Principal ===");
            System.out.println("1. Listar Produtos");
            System.out.println("2. Buscar Produto por ID");
            System.out.println("3. Adicionar Produto ao Carrinho");
            System.out.println("4. Visualizar Carrinho");
            System.out.println("5. Cadastrar Produto");
            System.out.println("0. Encerrar Programa");
            System.out.print("Escolha uma opção: ");

            try {
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        listarEstoque();
                        break;
                    case 2:
                        buscarProdutoPorId(scanner);
                        break;
                    case 3:
                        adicionarProdutoAoCarrinho(scanner);
                        break;
                    case 4:
                        visualizarCarrinho();
                        break;
                    case 5:
                        cadastrarProduto(scanner);
                        break;
                    case 0:
                        System.out.println("Encerrando o programa...");
                        break;
                    default:
                        System.out.println("Opção inválida! Por favor, tente novamente.");
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida! Por favor, insira um número.");
                scanner.next(); // Limpar o buffer de entrada
                choice = -1; // Define uma opção inválida para continuar o loop
            }

        } while (choice != 0);
    }



    private static void listarEstoque() {
        System.out.println("\n=== Produtos em Estoque ===");
        if (estoque.isEmpty()) {
            System.out.println("O estoque está vazio.");
        } else {
            for (Produto produto : estoque) {
                System.out.println(produto.toString());
            }
        }
    }

    private static void buscarProdutoPorId(Scanner scanner) {
        System.out.print("Digite o ID do produto: ");
        int id = scanner.nextInt();

        for (Produto produto : estoque) {
            if (produto.getIdProduto() == id) {
                System.out.println("Produto encontrado:");
                System.out.println(produto.toString());
                return;
            }
        }

        System.out.println("Produto não encontrado.");
    }

    private static void adicionarProdutoAoCarrinho(Scanner scanner) {
        System.out.print("Digite o ID do produto que deseja adicionar ao carrinho: ");
        int id = scanner.nextInt();

        for (Produto produto : estoque) {
            if (produto.getIdProduto() == id) {
                carrinho.add(produto);
                System.out.println("Produto adicionado ao carrinho.");
                return;
            }
        }

        System.out.println("Produto não encontrado.");
    }

    private static void visualizarCarrinho() {
        System.out.println("\n=== Carrinho de Compras ===");
        for (Produto produto : carrinho) {
            System.out.println(produto.toString());
        }
    }
    
    public static void cadastrarProduto(Scanner scanner) {
        System.out.println("\n=== Cadastro de Produto ===");
        System.out.println("Escolha o tipo de produto a cadastrar:");
        System.out.println("1. Vestimenta");
        System.out.println("2. Computador");
        System.out.println("3. Celular");
        System.out.println("4. Carro");
        System.out.println("5. Moto");
        System.out.println("6. Eletrodoméstico");
        System.out.print("Opção: ");
    
        int opcao = scanner.nextInt();
        scanner.nextLine();
    
        switch (opcao) {
            case 1:
                cadastrarVestimenta(scanner);
                incrementNextId();
                break;
            case 2:
                cadastrarComputador(scanner);
                incrementNextId();
                break;
            case 3:
                cadastrarCelular(scanner);
                incrementNextId();
                break;
            case 4:
                cadastrarCarro(scanner);
                incrementNextId();
                break;
            case 5:
                cadastrarMoto(scanner);
                incrementNextId();
                break;
            case 6:
                cadastrarEletroDomestico(scanner);
                incrementNextId();
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    public static void cadastrarVestimenta(Scanner scanner) {
        System.out.print("Informe o preço: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();
    
        System.out.print("Informe o gênero: ");
        String genero = scanner.nextLine();
    
        System.out.print("Informe o material: ");
        String material = scanner.nextLine();
    
        System.out.print("Informe a cor: ");
        String cor = scanner.nextLine();
    
        System.out.print("Informe a marca: ");
        String marca = scanner.nextLine();
    
        //Vestimenta vestimenta = new Vestimenta(getNextId(), preco, genero, material, cor, marca);
        // estoque.add(vestimenta);
        Produto p = new Vestimenta(getNextId(), preco, genero, material, cor, marca);
        estoque.add(p);
    
        System.out.println("Vestimenta cadastrada com sucesso.");
    }

    public static void cadastrarComputador(Scanner scanner) {
        System.out.print("Informe o preço: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();
    
        System.out.print("Informe o sistema operacional: ");
        String sistemaOperacional = scanner.nextLine();
    
        System.out.print("Informe a fonte (em watts): ");
        int fonte = scanner.nextInt();
        scanner.nextLine();
    
        System.out.print("Informe a conectividade: ");
        String conectividade = scanner.nextLine();
    
        System.out.print("Informe a garantia (em meses): ");
        int garantia = scanner.nextInt();
    
        System.out.print("Possui mouse? (true/false): ");
        boolean mouse = scanner.nextBoolean();
    
        // Computador computador = new Computador(getNextId(), preco, sistemaOperacional, fonte, conectividade, garantia, mouse);
        // estoque.add(computador);
        Produto p = new Computador(getNextId(), preco, sistemaOperacional, fonte, conectividade, garantia, mouse);
        estoque.add(p);
    
        System.out.println("Computador cadastrado com sucesso.");
    }

    public static void cadastrarCelular(Scanner scanner) {
        System.out.print("Informe o preço: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();
    
        System.out.print("Informe o sistema operacional: ");
        String sistemaOperacional = scanner.nextLine();
    
        System.out.print("Informe a fonte (em watts): ");
        int fonte = scanner.nextInt();
        scanner.nextLine();
    
        System.out.print("Informe a conectividade: ");
        String conectividade = scanner.nextLine();
    
        System.out.print("Informe a garantia (em meses): ");
        int garantia = scanner.nextInt();
    
        System.out.print("Informe a capacidade da bateria (em mAh): ");
        float bateria = scanner.nextFloat();
    
        // Celular celular = new Celular(getNextId(), preco, sistemaOperacional, fonte, conectividade, garantia, bateria);
        // estoque.add(celular);
        Produto p = new Celular(getNextId(), preco, sistemaOperacional, fonte, conectividade, garantia, bateria);
        estoque.add(p);
    
        System.out.println("Celular cadastrado com sucesso.");
    }

    public static void cadastrarCarro(Scanner scanner) {
        System.out.print("Informe o preço: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();
    
        System.out.print("Informe a marca: ");
        String marca = scanner.nextLine();
    
        System.out.print("Informe o modelo: ");
        String modelo = scanner.nextLine();
    
        System.out.print("Informe o ano: ");
        int ano = scanner.nextInt();
    
        System.out.print("Informe o número de portas: ");
        int portas = scanner.nextInt();
    
        System.out.print("Informe o tamanho do porta-malas (em litros): ");
        float tamanhoPortaMalas = scanner.nextFloat();
    
        // Carro carro = new Carro(getNextId(), preco, marca, modelo, ano, portas, tamanhoPortaMalas);
        // estoque.add(carro);
        Produto p = new Carro(getNextId(), preco, marca, modelo, ano, portas, tamanhoPortaMalas);
        estoque.add(p);
    
        System.out.println("Carro cadastrado com sucesso.");
    }

    public static void cadastrarMoto(Scanner scanner) {
        System.out.print("Informe o preço: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();
    
        System.out.print("Informe a marca: ");
        String marca = scanner.nextLine();
    
        System.out.print("Informe o modelo: ");
        String modelo = scanner.nextLine();
    
        System.out.print("Informe o ano: ");
        int ano = scanner.nextInt();
    
        System.out.print("Informe o tipo: ");
        String tipo = scanner.next();
    
        // Moto moto = new Moto(getNextId(), preco, marca, modelo, ano, tipo);
        // estoque.add(moto);
        Produto p = new Moto(getNextId(), preco, marca, modelo, ano, tipo);
        estoque.add(p);
    
        System.out.println("Moto cadastrada com sucesso.");
    }

    public static void cadastrarEletroDomestico(Scanner scanner) {
        System.out.print("Informe o preço: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();
    
        System.out.print("Informe a marca: ");
        String marca = scanner.nextLine();
    
        System.out.print("Informe o modelo: ");
        String modelo = scanner.nextLine();
    
        System.out.print("Informe o volume em litros: ");
        float volume = scanner.nextFloat();
        scanner.nextLine();
    
        System.out.print("Informe a Eficiência Energética: ");
        String eficienciaEnergetica = scanner.nextLine();
    
        Produto p = new EletroDomestico(getNextId(), preco, marca, modelo, volume, eficienciaEnergetica);
        estoque.add(p);
    }
}*/