import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class Produto {
    private int idProduto;
    private double preco;

    public Produto(int idProduto, double preco) {
        this.idProduto = idProduto;
        this.preco = new BigDecimal(preco).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public int getIdProduto() {
        return idProduto;
    }

    public double getPreco() {
        return preco;
    }

    @Override
    public abstract String toString();
}