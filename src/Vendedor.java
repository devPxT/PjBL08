public class Vendedor extends Usuario {
    private String cnpj;

    public Vendedor(String nome, String cpf, String login, String senha, String cnpj) {
        super(nome, cpf, login, senha);
        this.cnpj = cnpj;
    }

    @Override
    public String imprimeDescricao() {
        return super.imprimeDescricao() + ", CNPJ: " + cnpj;
    }
}