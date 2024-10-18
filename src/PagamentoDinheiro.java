public class PagamentoDinheiro implements MetodoPagamento {
    @Override
    public String pagar(double valor) {
        return "Pagamento de " + valor + " realizado em dinheiro.";
    }
}