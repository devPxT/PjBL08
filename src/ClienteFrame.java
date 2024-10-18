import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteFrame extends JFrame {
    private Cliente cliente;

    public ClienteFrame(Cliente cliente) {
        this.cliente = cliente;

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

    private void adicionarProdutoAoCarrinho() {
        DadosSingleton dados = DadosSingleton.getInstance();

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
            DadosSingleton dados = DadosSingleton.getInstance();
    
            for (Produto produto : carrinho) {
                message.append(produto.imprimeDescricao()).append("\n");
                total += produto.getPreco();
            }
            message.append("Total: ").append(total);
            message.append("\n\nEscolha um método de pagamento:");
    
            String[] opcoesPagamento = {"Cartão", "Dinheiro", "PayPal"};
            int metodoPagamento = JOptionPane.showOptionDialog(this, message.toString(), "Finalizar Compra",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, opcoesPagamento, opcoesPagamento[0]);
    
            MetodoPagamento pagamento = null;
            switch (metodoPagamento) {
                case 0:
                    pagamento = new PagamentoCartao();
                    break;
                case 1:
                    pagamento = new PagamentoDinheiro();
                    break;
                case 2:
                    pagamento = new PagamentoPaypal();
                    break;
                default:
                    return;
            }
    
            String confirmacaoMessage = "Deseja finalizar a compra?";
            Object[] options = {"Sim", "Não", "Limpar Carrinho"};
            int option = JOptionPane.showOptionDialog(this, confirmacaoMessage, "Finalizar Compra",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[0]);
    
            if (option == JOptionPane.YES_OPTION) {
                try {
                    verificarSaldo(cliente.getSaldo(), total);
                    String mensagemPagamento = pagamento.pagar(total);
                    JOptionPane.showMessageDialog(this, mensagemPagamento, "Pagamento Realizado", JOptionPane.INFORMATION_MESSAGE);
    
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
                    JOptionPane.showMessageDialog(this, "Compra finalizada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    
                } catch (ExcecaoSaldoInsuficiente e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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

    private void verificarSaldoRemover(double saldo, double total) throws ExcecaoSaldoInsuficiente {
        if (saldo < total) {
            throw new ExcecaoSaldoInsuficiente("Seu saldo não pode ficar negativo.");
        }
    }

    private void limparCarrinho() {
        cliente.getCarrinho().clear();
        JOptionPane.showMessageDialog(this, "O carrinho foi esvaziado.");
    }

    private void gerenciarSaldo() {
        DadosSingleton dados = DadosSingleton.getInstance();

        String message = "Saldo atual: " + cliente.getSaldo();
        String[] options = {"Voltar", "Remover Saldo", "Adicionar Saldo"};
        int choice = JOptionPane.showOptionDialog(this, message, "Gerenciar Saldo",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 1) {
            String valorStr = JOptionPane.showInputDialog("Digite o valor para remover:");
            if (valorStr == null) {
                return;
            }

            try {
                double valor = Double.parseDouble(valorStr);
                verificarSaldoRemover(cliente.getSaldo(), valor);

                cliente.debitarSaldo(valor);
                dados.saveUsers();
                JOptionPane.showMessageDialog(this, "Saldo atualizado.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Valor inválido.");

            } catch (ExcecaoSaldoInsuficiente e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }

        else if (choice == 2) {
            String valorStr = JOptionPane.showInputDialog("Digite o valor para adicionar:");
            if (valorStr == null) {
                return;
            }
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
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            });
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(logoutButton);
            topFrame.dispose();
        });
        return logoutButton;
    }
}
