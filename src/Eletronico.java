public class Eletronico extends Produto {
    private String sistemaOperacional;
    private int fonte;
    private String conectividade;
    private int garantia;

    public Eletronico(int idProduto, double preco, String sistemaOperacional, int fonte, String conectividade, int garantia) {
        super(idProduto, preco);
        this.sistemaOperacional = sistemaOperacional;
        this.fonte = fonte;
        this.conectividade = conectividade;
        this.garantia = garantia;
    }

    @Override
    public String imprimeDescricao() {
        return "ID: " +getIdProduto()+ ", Preço: " +getPreco()+ 
        ", Sistema Operacional: " + sistemaOperacional + ", Fonte: " + fonte + ", Conectividade: " + conectividade + ", Garantia: " + garantia + " meses";
    }
}