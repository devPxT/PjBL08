public class Celular extends Eletronico {
    private float bateria;

    public Celular(int idProduto, double preco, String sistemaOperacional, int fonte, String conectividade, int garantia, float bateria) {
        super(idProduto, preco, sistemaOperacional, fonte, conectividade, garantia);
        this.bateria = bateria;
    }

    public void imprimeDescricao() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return super.toString() + ", Bateria: " + bateria + " mAh";
    }
}