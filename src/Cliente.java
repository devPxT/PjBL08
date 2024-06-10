public class Cliente extends Usuario {
    private String cep;

    public Cliente(String nome, String cpf, String login, String senha, String cep) {
        super(nome, cpf, login, senha);
        this.cep = cep;
    }

    @Override
    public String imprimeDescricao() {
        return super.imprimeDescricao() + ", CEP: " + cep;
    }
}