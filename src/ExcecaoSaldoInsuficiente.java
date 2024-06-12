class ExcecaoSaldoInsuficiente extends Exception {
    public ExcecaoSaldoInsuficiente(String mensagem) {
        super(mensagem);
    }
    public ExcecaoSaldoInsuficiente() {
        super("Saldo insuficiente para finalizar a compra.");
    }
}