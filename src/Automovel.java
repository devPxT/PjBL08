public class Automovel extends Produto {
    private String marca;
    private String modelo;
    private int ano;

    public Automovel(int idProduto, double preco, String marca, String modelo, int ano) {
        super(idProduto, preco);
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
    }

    public void imprimeDescricao() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return super.toString() + ", Marca: " + marca + ", Modelo: " + modelo + ", Ano: " + ano;
    }
}