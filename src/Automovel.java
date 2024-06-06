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

    @Override
    public String toString() {
        return "ID: " +getIdProduto()+ ", Preço: " +getPreco()+ ", Marca: " + marca + ", Modelo: " + modelo + ", Ano: " + ano;
    }
}