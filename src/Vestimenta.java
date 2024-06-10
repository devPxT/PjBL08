public abstract class Vestimenta extends Produto {
    private String genero;
    private String material;
    private String cor;
    private String marca;

    public Vestimenta(int idProduto, double preco, String genero, String material, String cor, String marca) {
        super(idProduto, preco);
        this.genero = genero;
        this.material = material;
        this.cor = cor;
        this.marca = marca;
    }

    public String getGenero() {
        return genero;
    }

    public String getMaterial() {
        return material;
    }

    public String getCor() {
        return cor;
    }

    public String getMarca() {
        return marca;
    }

    @Override
    public String imprimeDescricao() {
        return "ID: " +getIdProduto()+ ", Preço: " +getPreco()+ ", Gênero: " + genero + ", Material: " + material + ", Cor: " + cor + ", Marca: " + marca;
    }
}