import java.util.ArrayList;
import java.util.List;

public class Vendedor extends Usuario {
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
    }


    @Override
    public String imprimeDescricao() {
        return super.imprimeDescricao() + ", CNPJ: " + cnpj;
    }
}