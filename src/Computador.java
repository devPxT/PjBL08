public class Computador extends Eletronico {
    private boolean mouse;

    public Computador(int idProduto, double preco, String sistemaOperacional, int fonte, String conectividade, int garantia, boolean mouse) {
        super(idProduto, preco, sistemaOperacional, fonte, conectividade, garantia);
        this.mouse = mouse;
    }

    @Override
    public String imprimeDescricao() {
        return getClass().getSimpleName() + " ➔ ID: " +getIdProduto()+ ", Preço: " +getPreco()+ ", SO: " +getSistemaOperacional()+ ", Fonte: " +getFonte()+ " W" + ", Conectividade: " +getConectividade()+ ", Garantia: " +getGarantia()+ " Meses" + ", Mouse: " + (mouse ? "Sim" : "Não");
    }
}