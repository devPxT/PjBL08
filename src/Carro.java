public class Carro extends Automovel {
    private int portas;
    private float tamanhoPortaMalas;

    public Carro(int idProduto, double preco, String marca, String modelo, int ano, int portas, float tamanhoPortaMalas) {
        super(idProduto, preco, marca, modelo, ano);
        this.portas = portas;
        this.tamanhoPortaMalas = tamanhoPortaMalas;
    }

    public void imprimeDescricao() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return super.toString() + ", Portas: " + portas + ", Tamanho Porta Malas: " + tamanhoPortaMalas + " litros";
    }
}