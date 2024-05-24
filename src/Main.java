import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String Users_File = "users.txt";

    private static List<Produto> estoque = new ArrayList<>();
    private static List<Produto> carrinho = new ArrayList<>();
    private static int nextId = 1;

    public static int getNextId() {
        return nextId;
    }

    public static void incrementNextId() {
        nextId++;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.println("\n=== Menu de Login e Cadastro ===");
            System.out.println("1. Login");
            System.out.println("2. Cadastro");
            System.out.println("0. Encerrar Programa");
            System.out.print("Escolha uma opção: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer de entrada

            switch (choice) {
                case 1:
                    loggedIn = login(scanner);
                    break;
                case 2:
                    cadastrar(scanner);
                    break;
                case 0:
                    System.out.println("Encerrando o programa...");
                    return;
                default:
                    System.out.println("Opção inválida! Por favor, tente novamente.");
            }
        }

        // Após o login bem-sucedido, mostrar o menu principal
        menuPrincipal(scanner);
    }

    private static boolean login(Scanner scanner) {
        int userType = getUserType(scanner);
        System.out.print("Digite o login: ");
        String login = scanner.nextLine();
        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(Users_File))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] user = line.split(",");
                if (Integer.parseInt(user[0]) == userType && user[1].equals(login) && user[2].equals(senha)) {
                    System.out.println("Login bem-sucedido!");
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de usuários.");
        }

        System.out.println("Login ou senha inválidos!");
        return false;
    }

    private static void cadastrar(Scanner scanner) {
        int userType = getUserType(scanner);
        System.out.print("Digite o login: ");
        String login = scanner.nextLine();
        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();

        if (usuarioExiste(userType, login)) {
            System.out.println("Login indisponível. Por favor, escolha um login diferente.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Users_File, true))) {
            bw.write(userType + "," + login + "," + senha);
            bw.newLine();
            System.out.println("Cadastro realizado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados do usuário.");
        }
    }

    private static boolean usuarioExiste(int userType, String login) {
        try (BufferedReader br = new BufferedReader(new FileReader(Users_File))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] user = line.split(",");
                if (Integer.parseInt(user[0]) == userType && user[1].equals(login)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de usuários.");
        }
        return false;
    }

    private static int getUserType(Scanner scanner) {
        int userType;
        do {
            System.out.println("1. Vendedor");
            System.out.println("2. Cliente");
            System.out.print("Digite o tipo de usuário (1 ou 2): ");
            userType = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer de entrada
            if (userType != 1 && userType != 2) {
                System.out.println("Entrada inválida! Por favor, insira 1 para Vendedor ou 2 para Cliente.");
            }
        } while (userType != 1 && userType != 2);
        return userType;
    }

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
}
