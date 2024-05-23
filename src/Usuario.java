public abstract class Usuario {
    private String nome;
    private String cpf;
    private String login;
    private String senha;

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }
    
    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public void imprimeDescricao() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", CPF: " + cpf + ", Login: " + login + ", Senha: " + senha;
    }
}