public abstract class Produto {
    private int idProduto;
    private double preco;

    public Produto(int idProduto, double preco) {
        this.idProduto = idProduto;
        this.preco = preco;
        //Main.incrementNextId();
    }

    public int getIdProduto() {
        return idProduto;
    }

    public double getPreco() {
        return preco;
    }

    public void imprimeDescricao() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "ID: " + idProduto + ", Preço: R$ " + preco;
    }
}   