public abstract class Eletronico extends Produto {
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

    public String getSistemaOperacional() {
        return sistemaOperacional;
    }
    public int getFonte() {
        return fonte;
    }
    public String getConectividade() {
        return conectividade;
    }
    public int getGarantia() {
        return garantia;
    }

    @Override
    public String imprimeDescricao() {
        return getClass().getSimpleName() + " ➔ ID: " +getIdProduto()+ ", Preço: " +getPreco()+ 
        ", Sistema Operacional: " + sistemaOperacional + ", Fonte: " + fonte + ", Conectividade: " + conectividade + ", Garantia: " + garantia + " meses";
    }
}