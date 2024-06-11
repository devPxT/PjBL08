import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteFrame extends JFrame {
    Dados dados = new Dados();

    private List<Produto> carrinho = new ArrayList<>();

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
        // dados.loadEstoque();
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
        // dados.loadEstoque();
        List<Produto> estoque = dados.getEstoque();

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
