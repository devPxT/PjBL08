public class Moto extends Automovel {
    private String tipo;

    public Moto(int idProduto, double preco, String marca, String modelo, int ano, String tipo) {
        super(idProduto, preco, marca, modelo, ano);
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "ID: " +getIdProduto()+ ", Preço: " +getPreco()+ ", Tipo: " + tipo;
    }

}