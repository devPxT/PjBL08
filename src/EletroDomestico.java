public class EletroDomestico extends Produto{
    private String marca;
    private String modelo;
    private float volume;
    private String eficienciaEnergetica;

    public EletroDomestico(int idProduto, double preco, String marca, String modelo, float volume, String eficienciaEnergetica) {
        this.marca = marca;
        this.modelo = modelo;
        this.volume = volume;
        this.eficienciaEnergetica = eficienciaEnergetica;
    }

    public void imprimeDescricao() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return super.toString() + ", Marca: " + marca + ", Modelo: " + modelo + ", Volume em litros: " + volume + ", Eficiência Energética: " + eficienciaEnergetica;
    }
}
