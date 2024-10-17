import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VendedorFrame extends JFrame {
    private Vendedor vendedor;

    public VendedorFrame(Vendedor vendedor) {
        this.vendedor = vendedor;

        DadosSingleton dados = DadosSingleton.getInstance();

        setTitle("Menu Vendedor");
        setSize(640, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JButton listarButton = new JButton("Listar Produtos");
        listarButton.addActionListener(e -> listarEstoque());

        JButton buscarButton = new JButton("Buscar Produto por ID");
        buscarButton.addActionListener(e -> buscarProdutoPorId());

        JButton cadastrarButton = new JButton("Cadastrar Produto");
        cadastrarButton.addActionListener(e -> cadastrarProduto());

        JButton saldoButton = new JButton("Visualizar Saldo");
        saldoButton.addActionListener(e -> visualizarSaldo());

        panel.add(listarButton);
        panel.add(buscarButton);
        panel.add(cadastrarButton);
        panel.add(saldoButton);
        panel.add(deslogarButton());

        add(panel);
    }

    private void visualizarSaldo() {
        String message = "Saldo atual: " + vendedor.getSaldo();
        JOptionPane.showMessageDialog(this, message);
    }

    private void listarEstoque() {
        DadosSingleton dados = DadosSingleton.getInstance();

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
        DadosSingleton dados = DadosSingleton.getInstance();

        dados.loadEstoque();
        // List<Produto> estoque = dados.getEstoque();

        while (true) {
            String idStr = JOptionPane.showInputDialog("Digite o ID do produto:");
            try {
                if (idStr == null) {
                    break;
                }
                int id = Integer.parseInt(idStr);
                for (Produto produto : dados.getEstoque()) {
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
        panel.setLayout(new GridLayout(6, 1));
    
        String[] options = {"Roupa", "Computador", "Celular", "Carro", "Moto", "Fogão"};
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
                        cadastrarFogao();
                        break;
                    default:
                        JOptionPane.showMessageDialog(panel, "Opção inválida.");
                }
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

        DadosSingleton dados = DadosSingleton.getInstance();
        while (true) {
            try {
                String preco = inputWithCheck("Informe o preço:");
                if (preco == null) {
                    cancel = true;
                    break; // Usuário pressionou cancelar
                }
                double newPreco = Double.parseDouble(preco);

                String[] generos = {"Masculino", "Feminino"};
                String genero = (String) JOptionPane.showInputDialog(
                        null,
                        "Informe o gênero:",
                        "Seleção de Gênero",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        generos,
                        generos[0]);
                if (genero == null) {
                    cancel = true;
                    break;
                }

                String[] materiais = {"Algodão", "Nylon"};
                String material = (String) JOptionPane.showInputDialog(
                        null,
                        "Informe o material:",
                        "Seleção de Material",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        materiais,
                        materiais[0]);
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

                String[] tamanhos = {"PP", "P", "M", "G", "GG"};
                String tamanho = (String) JOptionPane.showInputDialog(
                        null,
                        "Informe o tamanho:",
                        "Seleção de Tamanho",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        tamanhos,
                        tamanhos[0]);
                if (tamanho == null) {
                    cancel = true;
                    break;
                }

                dados.loadEstoque();
                Produto roupa = new Roupa(dados.getProdutoID(), newPreco, genero, material, cor, marca, tamanho);
                dados.saveProduto(roupa, vendedor);

                JOptionPane.showMessageDialog(null, "Roupa cadastrada com sucesso.");
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Entrada inválida.");
            }
        }

        if (cancel) {
            JOptionPane.showMessageDialog(null, "Cadastro de roupa cancelado.");
        }
    }

    private void cadastrarComputador() {
        boolean cancel = false;

        DadosSingleton dados = DadosSingleton.getInstance();
        while (true) {
            try {
                String preco = inputWithCheck("Informe o preço:");
                if (preco == null) {
                    cancel = true;
                    break;
                }
                
                double newPreco = Double.parseDouble(preco);

                String[] sistemaOperacional = {"Windows", "Linux", "MacOS"};
                String OS = (String) JOptionPane.showInputDialog(
                        null,
                        "Informe o sistema operaional:",
                        "Seleção de Sistema Operacional",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        sistemaOperacional,
                        sistemaOperacional[0]);
                if (OS == null) {
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
                Produto computador = new Computador(dados.getProdutoID(), newPreco, OS, newFonte, conectividade, newGarantia, mouse);
                dados.saveProduto(computador, vendedor);

                JOptionPane.showMessageDialog(this, "Computador cadastrado com sucesso.");
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Entrada inválida.");
            }
        }

        if (cancel) {
            JOptionPane.showMessageDialog(this, "Cadastro de computador cancelado.");
        }
    }

    private void cadastrarCelular() {
        boolean cancel = false;

        DadosSingleton dados = DadosSingleton.getInstance();
        while (true) {
            try {
                String preco = inputWithCheck("Informe o preço:");
                if (preco == null) {
                    cancel = true;
                    break;
                }
                double newPreco = Double.parseDouble(preco);
                
                String[] sistemaOperacional = {"Android", "IOS"};
                String OS = (String) JOptionPane.showInputDialog(
                        null,
                        "Informe o sistema operaional:",
                        "Seleção de Sistema Operacional",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        sistemaOperacional,
                        sistemaOperacional[0]);
                if (OS == null) {
                    cancel = true;
                    break;
                }

                String fonte = inputWithCheck("Informe a fonte (em watts):");
                if (fonte == null) {
                    cancel = true;
                    break;
                }
                int newFonte = Integer.parseInt(fonte);


                String[] tiposConectividade = {"Wi-Fi", "Bluetooth", "USB", "Ethernet", "3G", "4G"};
                String conectividade = (String) JOptionPane.showInputDialog(
                        null,
                        "Informe a conectividade:",
                        "Seleção de Conectividade",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        tiposConectividade,
                        tiposConectividade[0]);
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

                String bateria = inputWithCheck("Informe a capacidade da bateria (em mAh):");
                if (bateria == null) {
                    cancel = true;
                    break;
                }
                float newBateria = Float.parseFloat(bateria);

                dados.loadEstoque();
                Produto celular = new Celular(dados.getProdutoID(), newPreco, OS, newFonte, conectividade, newGarantia, newBateria);
                dados.saveProduto(celular, vendedor);

                JOptionPane.showMessageDialog(this, "Celular cadastrado com sucesso.");
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Entrada inválida.");
            }
        }
        if (cancel) {
            JOptionPane.showMessageDialog(this, "Cadastro de celular cancelado.");
        }
    }

    private void cadastrarCarro() {
        boolean cancel = false;

        DadosSingleton dados = DadosSingleton.getInstance();
        while(true) {
            try {
                String preco = inputWithCheck("Informe o preço:");
                if (preco == null) {
                    cancel = true;
                    break;
                }
                double newPreco = Double.parseDouble(preco);

                String marca = inputWithCheck("Informe a marca:");
                if (marca == null) {
                    cancel = true;
                    break;
                }
                
                String modelo = inputWithCheck("Informe o modelo:");
                if (modelo == null) {
                    cancel = true;
                    break;
                }

                String ano = inputWithCheck("Informe o ano:");
                if (ano == null) {
                    cancel = true;
                    break;
                }
                int newAno = Integer.parseInt(ano);
                
                String[] portas = {"2", "4"};
                String porta = (String) JOptionPane.showInputDialog(
                        null,
                        "Informe o número de portas:",
                        "Seleção de Portas",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        portas,
                        portas[0]);
                if (porta == null) {
                    cancel = true;
                    break;
                }
                int newPortas = Integer.parseInt(porta);

                String tamanhoPortaMalas = inputWithCheck("Informe o tamanho do porta-malas (em litros):");
                if (tamanhoPortaMalas == null) {
                    cancel = true;
                    break;
                }
                float newTamanhoPortaMalas = Float.parseFloat(tamanhoPortaMalas);

                dados.loadEstoque();
                Produto carro = new Carro(dados.getProdutoID(), newPreco, marca, modelo, newAno, newPortas, newTamanhoPortaMalas);
                dados.saveProduto(carro, vendedor);

                JOptionPane.showMessageDialog(this, "Carro cadastrado com sucesso.");
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Entrada inválida.");
            }
        }

        if (cancel) {
            JOptionPane.showMessageDialog(this, "Cadastro de carro cancelado.");
        }
    }

    private void cadastrarMoto() {
        boolean cancel = false;

        DadosSingleton dados = DadosSingleton.getInstance();
        while(true) {
            try {
                String preco = inputWithCheck("Informe o preço:");
                if (preco == null) {
                    cancel = true;
                    break;
                }
                double newPreco = Double.parseDouble(preco);

                String marca = inputWithCheck("Informe a marca:");
                if (marca == null) {
                    cancel = true;
                    break;
                }
                
                String modelo = inputWithCheck("Informe o modelo:");
                if (modelo == null) {
                    cancel = true;
                    break;
                }

                String ano = inputWithCheck("Informe o ano:");
                if (ano == null) {
                    cancel = true;
                    break;
                }
                int newAno = Integer.parseInt(ano);

                String[] tipoMoto = {"Naked", "Esportiva", "Trail", "Street", "Scooter", "Custom", "MotoCross"};
                String tipo = (String) JOptionPane.showInputDialog(
                        null,
                        "Informe o tipo:",
                        "Seleção de Tipo",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        tipoMoto,
                        tipoMoto[0]);
                if (tipo == null) {
                    cancel = true;
                    break;
                }

                dados.loadEstoque();
                Produto moto = new Moto(dados.getProdutoID(), newPreco, marca, modelo, newAno, tipo);
                dados.saveProduto(moto, vendedor);

                JOptionPane.showMessageDialog(this, "Moto cadastrada com sucesso.");
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Entrada inválida.");
            }
        }

        if (cancel) {
            JOptionPane.showMessageDialog(this, "Cadastro de carro cancelado.");
        }
        
    }

    private void cadastrarFogao() {
        boolean cancel = false;

        DadosSingleton dados = DadosSingleton.getInstance();
        while(true) {
            try {
                String preco = inputWithCheck("Informe o preço:");
                if (preco == null) {
                    cancel = true;
                    break;
                }
                double newPreco = Double.parseDouble(preco);

                String marca = inputWithCheck("Informe a marca:");
                if (marca == null) {
                    cancel = true;
                    break;
                }
                
                String modelo = inputWithCheck("Informe o modelo:");
                if (modelo == null) {
                    cancel = true;
                    break;
                }
                
                String volume = inputWithCheck("Informe o volume em litros:");
                if (volume == null) {
                    cancel = true;
                    break;
                }
                float newVolume = Float.parseFloat(volume);
                
                String[] eficienciaEnergetica = {"A", "B", "C", "D", "E", "F"};
                String ef_en = (String) JOptionPane.showInputDialog(
                        null,
                        "Informe a eficiência energética:",
                        "Seleção de Eficiência Energética",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        eficienciaEnergetica,
                        eficienciaEnergetica[0]);
                if (ef_en == null) {
                    cancel = true;
                    break;
                }
                
                String[] numeroBocas = {"1", "2", "3", "4", "5", "6"};
                String bocas = (String) JOptionPane.showInputDialog(
                        null,
                        "Informe a quantidade de bocas:",
                        "Seleção de número de bocas",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        numeroBocas,
                        numeroBocas[0]);
                if (bocas == null) {
                    cancel = true;
                    break;
                }
                int newBocas = Integer.parseInt(bocas);

                dados.loadEstoque();
                Produto fogao = new Fogao(dados.getProdutoID(), newPreco, marca, modelo, newVolume, ef_en, newBocas);
				dados.saveProduto(fogao, vendedor);

                JOptionPane.showMessageDialog(this, "Fogão cadastrado com sucesso.");
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Entrada inválida.");
            }
        }

        if (cancel) {
            JOptionPane.showMessageDialog(this, "Cadastro de fogão cancelado.");
        }

    }

    private String inputWithCheck(String message) {
        while (true) {
            String input = JOptionPane.showInputDialog(message);
            if (input == null) {
                return null; //usuario cancelou
            }
            if (input.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, tente novamente.");
            } else {
                return input.trim();
            }
        }
    }

    private JButton deslogarButton() {
        JButton logoutButton = new JButton("Deslogar");
        logoutButton.setForeground(Color.RED);
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
