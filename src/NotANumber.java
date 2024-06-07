public class NotANumber extends NumberFormatException {

    public NotANumber(String mensagem) {
        super(mensagem);
    }
    public NotANumber() {
        super();
    }
}