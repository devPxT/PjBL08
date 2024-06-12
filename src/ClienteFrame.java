import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteFrame extends JFrame {
    private Cliente cliente;

    private Dados dados;

    public ClienteFrame(Cliente cliente, Dados dados) {
        this.cliente = cliente;
        this.dados = dados;

        setTitle("Menu Cliente");
        setSize(512, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JButton listarButton = new JButton("Listar Produtos");
        listarButton.addActionListener(e -> listarEstoque());

        JButton adicionarButton = new JButton("Adicionar Produto ao Carrinho");
        adicionarButton.addActionListener(e -> adicionarProdutoAoCarrinho());

        JButton visualizarButton = new JButton("Visualizar Carrinho");
        visualizarButton.addActionListener(e -> visualizarCarrinho());

        JButton saldoButton = new JButton("Gerenciar Saldo");
        saldoButton.addActionListener(e -> gerenciarSaldo());

        panel.add(listarButton);
        panel.add(adicionarButton);
        panel.add(visualizarButton);
        panel.add(saldoButton);
        panel.add(deslogarButton());

        add(panel);
    }

    private void listarEstoque() {
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

    private void adicionarProdutoAoCarrinho() {
        dados.loadEstoque();
        
        List<Produto> estoque = new ArrayList<>();
        List<Produto> produtosTemp;
    
        List<Usuario> usuarios = dados.getUsuarios();
        for (Usuario usuario : usuarios) {
            if (usuario instanceof Vendedor) {
                produtosTemp = ((Vendedor) usuario).getProdutos();
                estoque.addAll(produtosTemp);
            }
        }
        
        List<Produto> carrinho = cliente.getCarrinho();
    
        String idStr = JOptionPane.showInputDialog("Digite o ID do produto que deseja adicionar ao carrinho:");
        try {
            int id = Integer.parseInt(idStr);
    
            for (Produto produto : carrinho) {
                if (produto.getIdProduto() == id) {
                    JOptionPane.showMessageDialog(this, "Produto já está no carrinho.");
                    return;
                }
            }
    
            for (Produto produto : estoque) {
                if (produto.getIdProduto() == id) {
                    carrinho.add(produto);
                    dados.saveUsers();
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
        double total = 0.0;

        List<Produto> carrinho = cliente.getCarrinho();
    
        if (carrinho.isEmpty()) {
            message.append("O carrinho está vazio.");
            JOptionPane.showMessageDialog(this, message.toString());
        } else {
            for (Produto produto : carrinho) {
                message.append(produto.imprimeDescricao()).append("\n");
                total += produto.getPreco();
            }
            message.append("Total: ").append(total);
            message.append("\n\nDeseja finalizar a compra?");
            // int option = JOptionPane.showConfirmDialog(this, message.toString(), "Finalizar Compra", JOptionPane.YES_NO_OPTION);
    
            Object[] options = {"Sim", "Não", "Limpar Carrinho"};
            int option = JOptionPane.showOptionDialog(this, message.toString(), "Finalizar Compra",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[0]);

            if (option == JOptionPane.YES_OPTION) {
                try {
                    verificarSaldo(cliente.getSaldo(), total);
                    for (Produto produto : carrinho) {
                        for (Usuario usuario : dados.getUsuarios()) {
                            if (usuario instanceof Vendedor) {
                                if (((Vendedor) usuario).getProdutos().contains(produto)) {
                                    usuario.adicionarSaldo(produto.getPreco());
                                    ((Vendedor) usuario).getProdutos().remove(produto);
                                }
                            }
                        }
                        dados.removeProdutoPorId(produto.getIdProduto());
                    }
                    cliente.debitarSaldo(total);
                    carrinho.clear();
                    dados.saveUsers();
                    dados.saveEstoque();
                    JOptionPane.showMessageDialog(this, "Compra finalizada com sucesso.");

                } catch (ExcecaoSaldoInsuficiente e) {
                    JOptionPane.showMessageDialog(this, e.getMessage());
                }
            } else if (option == JOptionPane.CANCEL_OPTION) {
                limparCarrinho();
            }
        }
    }

    private void verificarSaldo(double saldo, double total) throws ExcecaoSaldoInsuficiente {
        if (saldo < total) {
            throw new ExcecaoSaldoInsuficiente();
        }
    }

    private void limparCarrinho() {
        cliente.getCarrinho().clear();
        JOptionPane.showMessageDialog(this, "O carrinho foi esvaziado.");
    }

    private void gerenciarSaldo() {
        String message = "Saldo atual: " + cliente.getSaldo();
        String[] options = {"Adicionar Saldo", "Voltar"};
        int choice = JOptionPane.showOptionDialog(this, message, "Gerenciar Saldo",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            String valorStr = JOptionPane.showInputDialog("Digite o valor para adicionar:");
            try {
                double valor = Double.parseDouble(valorStr);
                cliente.adicionarSaldo(valor);
                dados.saveUsers();
                JOptionPane.showMessageDialog(this, "Saldo atualizado.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Valor inválido.");
            }
        }
    }

    private JButton deslogarButton() {
        JButton logoutButton = new JButton("Deslogar");
        logoutButton.setForeground(Color.RED);
        logoutButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                LoginFrame loginFrame = new LoginFrame(dados);
                loginFrame.setVisible(true);
            });
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(logoutButton);
            topFrame.dispose();
        });
        return logoutButton;
    }
}
