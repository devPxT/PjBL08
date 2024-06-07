public class Carro extends Automovel {
    private int portas;
    private float tamanhoPortaMalas;

    public Carro(int idProduto, double preco, String marca, String modelo, int ano, int portas, float tamanhoPortaMalas) {
        super(idProduto, preco, marca, modelo, ano);
        this.portas = portas;
        this.tamanhoPortaMalas = tamanhoPortaMalas;
    }

    @Override
    public String imprimeDescricao() {
        return "ID: " +getIdProduto()+ ", Preço: " +getPreco()+ ", Portas: " + portas + ", Tamanho Porta Malas: " + tamanhoPortaMalas + " litros";
    }
}