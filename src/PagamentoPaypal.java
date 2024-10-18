public class PagamentoPaypal implements MetodoPagamento {
    @Override
    public String pagar(double valor) {
        return "Pagamento de " + valor + " realizado via PayPal.";
    }
}