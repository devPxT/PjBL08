public class ItemVenda extends Produto {
    private String nomeItem;
    private String descricao;

    public ItemVenda(int idProduto, double preco, String nomeItem, String descricao) {
        super(idProduto, preco);
        this.nomeItem = nomeItem;
        this.descricao = descricao;
    }

    @Override
    public String imprimeDescricao() {
        return "ID: " +getIdProduto()+ ", Preço: " +getPreco()+ ", Nome Item: " + nomeItem + ", Descrição: " + descricao;
    }
}