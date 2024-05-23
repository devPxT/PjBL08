public class Cliente extends Usuario {
    private String cep;

    public Cliente(String nome, String cpf, String login, String senha, String cep) {
        this.cep = cep;
    }

    public void imprimeDescricao() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return super.toString() + ", CEP: " + cep;
    }
}