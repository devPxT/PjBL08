public class Celular extends Eletronico {
    private float bateria;

    public Celular(int idProduto, double preco, String sistemaOperacional, int fonte, String conectividade, int garantia, float bateria) {
        super(idProduto, preco, sistemaOperacional, fonte, conectividade, garantia);
        this.bateria = bateria;
    }

    @Override
    public String imprimeDescricao() {
        return getClass().getSimpleName() + " ➔ ID: " +getIdProduto()+ ", Preço: " +getPreco()+ ", SO: " +getSistemaOperacional()+ ", Fonte: " +getFonte()+ " W" + ", Conectividade: " +getConectividade()+ ", Garantia: " +getGarantia()+ " Meses" + ", Bateria: " + bateria + " mAh";
    }
}