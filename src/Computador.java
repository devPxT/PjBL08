public class Computador extends Eletronico {
    private boolean mouse;

    public Computador(int idProduto, double preco, String sistemaOperacional, int fonte, String conectividade, int garantia, boolean mouse) {
        super(idProduto, preco, sistemaOperacional, fonte, conectividade, garantia);
        this.mouse = mouse;
    }

    @Override
    public String toString() {
        return "ID: " +getIdProduto()+ ", Preço: " +getPreco()+ ", Mouse: " + (mouse ? "Sim" : "Não");
    }
}