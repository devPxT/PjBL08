import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {
    private String cep;
    private List<Produto> carrinho;

    public Cliente(String nome, String cpf, String login, String senha, String cep) {
        super(nome, cpf, login, senha);
        this.cep = cep;

        carrinho = new ArrayList<>();
    }

    public List<Produto> getCarrinho() {
        return carrinho;
    }

    public void adicionarAoCarrinho(Produto p) {
        carrinho.add(p);
    }

    @Override
    public String imprimeDescricao() {
        return super.imprimeDescricao() + ", CEP: " + cep;
    }
}