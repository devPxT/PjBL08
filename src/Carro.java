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
        return getClass().getSimpleName() + " ➔ ID: " +getIdProduto()+ ", Preço: " +getPreco()+ ", Marca: " +getMarca()+ ", Modelo: " +getModelo()+ ", Ano: " +getAno()+ ", Portas: " + portas + ", Tamanho Porta Malas: " + tamanhoPortaMalas + " litros";
    }
}