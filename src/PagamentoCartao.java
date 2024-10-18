public class PagamentoCartao implements MetodoPagamento {
    @Override
    public String pagar(double valor) {
        return "Pagamento de " + valor + " realizado com cartão.";
    }
}