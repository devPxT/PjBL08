public class Fogao extends EletroDomestico {
    private int bocas;

    public Fogao(int idProduto, double preco, String marca, String modelo, float volume, String eficienciaEnergetica, int bocas) {
        super(idProduto, preco, marca, modelo, volume, eficienciaEnergetica);
        this.bocas = bocas;
    }

    @Override
    public String imprimeDescricao() {
        return getClass().getSimpleName() + " ➔ ID: " +getIdProduto()+ ", Preço: " +getPreco()+ ", Marca: " +getMarca()+ ", Modelo: " +getModelo()+ ", Volume em litros: " +getVolume()+ ", Eficiência Energética: " +getEficienciaEnergetica()+ ", Nº de bocas: " + bocas;
    }
}