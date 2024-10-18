import java.io.IOException;

public class LoginFacade {
    private DadosSingleton dados;

    public LoginFacade() {
        dados = DadosSingleton.getInstance();
    }

    public Usuario autenticar(String login, String senha, String userType) throws IOException {
        dados.loadUsers();

        for (Usuario usuario : dados.getUsuarios()) {
            if (usuario.getLogin().equals(login) && usuario.getSenha().equals(senha)) {
                if ((usuario instanceof Vendedor && "Vendedor".equals(userType)) ||
                    (usuario instanceof Cliente && "Cliente".equals(userType))) {
                    return usuario;
                }
            }
        }
        return null;
    }
}