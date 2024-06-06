import java.util.ArrayList;
import java.util.List;

public class Vendedor extends Usuario {
    private String cnpj;
    private String enderecoEmpresa;
    private String telefoneContato;
    private String email;
    private String nomeEmpresa;
    private List<Produto> itensAnunciados;

    public Vendedor(String nome, String cpf, String login, String senha, String cnpj, String enderecoEmpresa, String telefoneContato, String email, String nomeEmpresa) { 
        this.cnpj = cnpj;
        this.enderecoEmpresa = enderecoEmpresa;
        this.telefoneContato = telefoneContato;
        this.email = email;
        this.nomeEmpresa = nomeEmpresa;
        this.itensAnunciados = new ArrayList<>();
    }

    public void anunciarItemVenda(ItemVenda item) {
        itensAnunciados.add(item);
        System.out.println("Item anunciado pelo vendedor " + getNome() + ":");
        item.toString();
    }

    public void imprimeDescricao() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return super.toString() + ", CNPJ: " + cnpj +
                ", Endereço: " + enderecoEmpresa +
                ", Telefone: " + telefoneContato +
                ", Email: " + email +
                ", Nome da Empresa: " + nomeEmpresa;
    }
}