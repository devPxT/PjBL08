public abstract class Produto {
    private int idProduto;
    private double preco;


    public int getIdProduto() {
        return idProduto;
    }

    public double getPreco() {
        return preco;
    }

    public abstract void imprimeDescricao();

    @Override
    public String toString() {
        return "ID: " + idProduto + ", Preço: R$ " + preco;
    }
}   