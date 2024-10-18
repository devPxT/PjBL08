import java.util.ArrayList;
import java.util.List;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Vendedor extends Usuario implements Observer {
    private String cnpj;
    private List<Produto> produtos;

    public Vendedor(String nome, String cpf, String login, String senha, String cnpj) {
        super(nome, cpf, login, senha);
        this.cnpj = cnpj;

        produtos = new ArrayList<>();
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void adicionarProduto(Produto p) {
        produtos.add(p);
        p.adicionarObserver(this);
    }

    public void removerProduto(Produto p) {
        produtos.remove(p);
    }

    public void atualizar(String mensagem) {
        //tamanho da tela
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        int larguraTela = screenSize.width;
        int larguraNotificacao = 450;

        int posXVerde = larguraTela - larguraNotificacao;
        int posY = 0;

        new Notificacao(mensagem, new Color(60, 179, 113), posXVerde, posY);  //canto superior direito (verde suave)

        if (produtos.isEmpty()) {
            new Notificacao("O estoque do vendedor " + getNome() + " acabou.", Color.RED, 0, posY); //canto superior esquerdo (vermelho)
        }
    }

    @Override
    public String imprimeDescricao() {
        return super.imprimeDescricao() + ", CNPJ: " + cnpj;
    }
}