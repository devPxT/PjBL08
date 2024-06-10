public class Roupa extends Vestimenta {
    private String tamanho;

    public Roupa(int idProduto, double preco, String genero, String material, String cor, String marca, String tamanho) {
        super(idProduto, preco, genero, material, cor, marca);
        this.tamanho = tamanho;
    }

    @Override
    public String imprimeDescricao() {
        return "ID: " +getIdProduto()+ ", Preço: " +getPreco()+ ", Gênero: "  +getGenero()+ ", Material: " +getMaterial()+ ", Cor: " +getCor()+ ", Marca: " +getMarca()+ ", Tamanho: " + tamanho;
    }
}