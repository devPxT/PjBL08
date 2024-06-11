import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VendedorFrame extends JFrame {
    Dados dados = new Dados();

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
        // dados.loadUsers();
        dados.loadEstoque();
        List<Produto> estoque = dados.getEstoque();

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
        dados.loadEstoque();
        List<Produto> estoque = dados.getEstoque();

        while (true) {
            String idStr = JOptionPane.showInputDialog("Digite o ID do produto:");
            try {
                if (idStr == null) {
                    break;
                }
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
                // incrementNextId();
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
    
                dados.loadEstoque();
                Produto roupa = new Roupa(dados.getProdutoID(), newPreco, genero, material, cor, marca, tamanho);
                dados.saveEstoque(roupa);
    
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

                dados.loadEstoque();
                Produto computador = new Computador(dados.getProdutoID(), newPreco, sistemaOperacional, newFonte, conectividade, newGarantia, mouse);
                dados.saveEstoque(computador);

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

            dados.loadEstoque();
            Produto celular = new Celular(dados.getProdutoID(), preco, sistemaOperacional, fonte, conectividade, garantia, bateria);
            dados.saveEstoque(celular);

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

            dados.loadEstoque();
            Produto carro = new Carro(dados.getProdutoID(), preco, marca, modelo, ano, portas, tamanhoPortaMalas);
            dados.saveEstoque(carro);

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

            dados.loadEstoque();
            Produto moto = new Moto(dados.getProdutoID(), preco, marca, modelo, ano, tipo);
            dados.saveEstoque(moto);

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

            dados.loadEstoque();
            Produto eletroDomestico = new EletroDomestico(dados.getProdutoID(), preco, marca, modelo, volume, eficienciaEnergetica);
            dados.saveEstoque(eletroDomestico);

            JOptionPane.showMessageDialog(this, "Eletrodoméstico cadastrado com sucesso.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Entrada inválida.");
        }
    }

    public JButton deslogarButton() {
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
