public class Computador extends Eletronico {
    private boolean mouse;

    public Computador(int idProduto, double preco, String sistemaOperacional, int fonte, String conectividade, int garantia, boolean mouse) {
        super(idProduto, preco, sistemaOperacional, fonte, conectividade, garantia);
        this.mouse = mouse;
    }

    public void imprimeDescricao() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return super.toString() + ", Mouse: " + (mouse ? "Sim" : "Não");
    }
}