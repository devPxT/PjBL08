public abstract class EletroDomestico extends Produto{
    private String marca;
    private String modelo;
    private float volume;
    private String eficienciaEnergetica;

    public EletroDomestico(int idProduto, double preco, String marca, String modelo, float volume, String eficienciaEnergetica) {
        super(idProduto, preco);
        this.marca = marca;
        this.modelo = modelo;
        this.volume = volume;
        this.eficienciaEnergetica = eficienciaEnergetica;
    }

    public String getMarca() {
        return marca;
    }
    public String getModelo() {
        return modelo;
    }
    public float getVolume() {
        return volume;
    }
    public String getEficienciaEnergetica() {
        return eficienciaEnergetica;
    }

    @Override
    public String imprimeDescricao() {
        return "ID: " +getIdProduto()+ ", Preço: " +getPreco()+ ", Marca: " + marca + ", Modelo: " + modelo + ", Volume em litros: " + volume + ", Eficiência Energética: " + eficienciaEnergetica;
    }
} 