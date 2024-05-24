import java.io.IOException;

public class ArquivoInvalido extends IOException {

    public ArquivoInvalido(String mensagem) {
        super(mensagem);
    }
    public ArquivoInvalido() {
        super();
    }
}